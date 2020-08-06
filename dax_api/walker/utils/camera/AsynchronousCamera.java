package net.runelite.client.rsb.walker.dax_api.walker.utils.camera;


import net.runelite.client.rsb.methods.Web;
import net.runelite.client.rsb.util.StdRandom;
import net.runelite.client.rsb.wrappers.common.Positionable;
import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static net.runelite.client.rsb.walker.dax_api.walker.utils.camera.CameraCalculations.getRotationToTile;
import static scripts.dax_api.walker.utils.camera.CameraCalculations.getRotationToTile;

public class AsynchronousCamera {

    private static AsynchronousCamera instance = null;
    private ExecutorService executorService;
    private Future angleTask, rotationTask;

    private static AsynchronousCamera getInstance(){
        return instance != null ? instance : (instance = new AsynchronousCamera());
    }

    private AsynchronousCamera(){
        executorService = Executors.newFixedThreadPool(2);
    }

    public static Future focus(Positionable positionable){
        Future rotation = setCameraRotation(getRotationToTile(positionable), 0);
        Future angle = setCameraAngle(CameraCalculations.getAngleToTile(positionable), 0);
        return rotation;
    }

    public static synchronized Future setCameraAngle(int angle, int tolerance){
        if (getInstance().angleTask != null && !getInstance().angleTask.isDone()){
            return null;
        }
        Camera.setRotationMethod(Camera.ROTATION_METHOD.ONLY_KEYS);
            return getInstance().angleTask = getInstance().executorService.submit(() -> Web.methods.camera.setPitch(
		            CameraCalculations.normalizeAngle(angle + StdRandom.uniform(-tolerance, tolerance))));
    }

    public static synchronized Future setCameraRotation(int degrees, int tolerance){
        if (getInstance().rotationTask != null && !getInstance().rotationTask.isDone()){
            return null;
        }
        Camera.setRotationMethod(Camera.ROTATION_METHOD.ONLY_KEYS);
        return getInstance().rotationTask = getInstance().executorService.submit(() -> Web.methods.camera.setAngle(
		        CameraCalculations.normalizeRotation(degrees + StdRandom.uniform(-tolerance, tolerance))));
    }

}
