public class TemperatureSensor extends Sensor {
    public TemperatureSensor(String sensorId, double reading) {
        super(sensorId, reading);
    }

    @Override
    public String getInfo() {
        return "[Temp Sensor] " + super.getInfo() + " °C";
    }
}
