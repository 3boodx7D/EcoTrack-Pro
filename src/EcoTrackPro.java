import java.util.ArrayList;

public class EcoTrackPro {
    private ArrayList<Sensor> sensorList = new ArrayList<>();

    public void addSensor(Sensor sensor) {
        sensorList.add(sensor);
    }

    public void clearSensors() {
        sensorList.clear();
    }

    public ArrayList<Sensor> getSensorList() {
        return sensorList;
    }

    public double getAverageCO2() {
        double total = 0;
        int count = 0;
        for (Sensor s : sensorList) {
            if (s instanceof CO2Sensor) {
                total += s.getReading();
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }

    public double getAverageTemp() {
        double total = 0;
        int count = 0;
        for (Sensor s : sensorList) {
            if (s instanceof TemperatureSensor) {
                total += s.getReading();
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }

    public double getAverageHumid() {
        double total = 0;
        int count = 0;
        for (Sensor s : sensorList) {
            if (s instanceof HumiditySensor) {
                total += s.getReading();
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }

    public String runAnalysis() {
        double avgCO2 = getAverageCO2();
        double avgTemp = getAverageTemp();

        if (avgCO2 > 1000) {
            return "CRITICAL ALERT: CO2 Levels high!";
        } else if (avgTemp > 25) {
            return "WARNING: Temperature above limit!";
        } else if (sensorList.isEmpty()) {
            return "Status: No Data";
        } else {
            return "Status: Optimal";
        }
    }
}
