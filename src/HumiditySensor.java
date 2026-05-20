public class HumiditySensor extends Sensor {
    public HumiditySensor(String sensorId, double reading) {
        super(sensorId, reading);
    }

    @Override
    public String getInfo() {
        return "[Humidity Sensor] " + super.getInfo() + " %";
    }
}
