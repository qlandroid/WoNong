package com.shqtn.wonong.info;

/**
 * Created by android on 2017/9/25.
 */

public class ApiUrl {

    public static String BASE_URL;

    public static String LOGIN = BASE_URL + "/api/Login";
    public static String FILE_UPDATE = BASE_URL + "/api/UploadFile";

    public static String MANIFEST_LIST = BASE_URL + "/api/GetCode";
    public static String MANIFEST_DEATILS = BASE_URL + "/api/Tests";

    public static void changeBase(String ip, String port) {
        BASE_URL = "http://" + ip + ":" + port;
        LOGIN = BASE_URL + "/api/Login";
        FILE_UPDATE = BASE_URL + "/api/UploadFile";

        MANIFEST_LIST = BASE_URL + "/api/GetCode";
        MANIFEST_DEATILS = BASE_URL + "/api/Tests";
    }
}
