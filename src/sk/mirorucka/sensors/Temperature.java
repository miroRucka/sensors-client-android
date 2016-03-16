package sk.mirorucka.sensors;

/**
 * @author rucka
 */
public class Temperature {
    private String key;
    private double value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
