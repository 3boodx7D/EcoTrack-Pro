public class Sensor {
    private String sensorId;
    private double reading;

    public Sensor(String sensorId, double reading) {
        this.sensorId = sensorId;
        this.reading = reading;
    }

    public String getSensorId() {
        return sensorId;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public String getInfo() {
        return "Sensor ID: " + sensorId + " | Reading: " + reading;
    }
}
