public class CO2Sensor extends Sensor {
    public CO2Sensor(String sensorId, double reading) {
        super(sensorId, reading);
    }

    @Override
    public String getInfo() {
        return "[CO2 Sensor] " + super.getInfo() + " ppm";
    }
}
