package net.runelite.client.rsb.walker.dax_api.api_lib.models;


import com.allatori.annotations.DoNotRename;

@DoNotRename
public enum PathStatus {
    UNMAPPED_REGION,
    SUCCESS,
    BLOCKED,
    EXCEEDED_SEARCH_LIMIT,
    UNREACHABLE,
    NO_WEB_PATH,

    INVALID_CREDENTIALS,
    RATE_LIMIT_EXCEEDED,


    //OFFLINE
    NO_RESPONSE_FROM_SERVER,

    //WHAT IS GOING ON
    UNKNOWN

}