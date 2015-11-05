package es.upm.miw.persistenciaservicios.models;

public class APIutils {

    private static String API_BASE_URL;

    private static APIutils singletonInstance = new APIutils();

    private APIutils() {
        API_BASE_URL = "http://jsonplaceholder.typicode.com";
    }

    public static APIutils getInstance() {
        return singletonInstance;
    }

    public static String getAPI_BASE_URL() {
        return API_BASE_URL;
    }
}
