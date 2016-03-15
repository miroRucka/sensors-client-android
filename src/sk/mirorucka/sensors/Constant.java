package sk.mirorucka.sensors;

/**
 * @author rucka
 */
public enum Constant {
    APP_TAG("sensors-"),
    PROPERTIES_KEY("sensors.properties"),
    HTTP_CLIENT_TIMEOUT("http.client.timeout");

    private String code;

    private Constant(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    }
