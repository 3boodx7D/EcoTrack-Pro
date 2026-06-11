import java.util.ArrayList;

/**
 * -----------------------------------------------------------
 * PARADIGM: OBJECT-ORIENTED PROGRAMMING (OOP)
 * -----------------------------------------------------------
 * Encapsulating system state, managing sensor collections polymorphically,
 * and decoupling business logic from the graphical user interface.
 */
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

    /**
     * -----------------------------------------------------------
     * PARADIGM: PROCEDURAL PROGRAMMING
     * -----------------------------------------------------------
     * Executing step-by-step algorithms, iterating through data
     * collections, and calculating mathematical averages.
     */
    public double getAverageCO2() {
        double total = 0;
        int count = 0;
        for (Sensor s : sensorList) {
            // OOP: Using 'instanceof' to identify type – Polymorphism in action
            if (s instanceof CO2Sensor) {
                total += s.getReading(); // Accumulate CO2 readings
                count++;                 // Track valid sensor count for average
            }
        }
        // Ternary: Prevent division by zero if no sensors of this type exist
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

        if (sensorList.isEmpty()) {
            return "Status: No Data";
        } else if (avgCO2 > 1000) {
            return "CRITICAL ALERT: CO2 Levels high!";
        } else if (avgTemp > 25) {
            return "WARNING: Temperature above limit!";
        } else {
            return "Status: Optimal";
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // V2 ENHANCEMENT: Cross-Sensor Consistency Check
    // ═══════════════════════════════════════════════════════════════
    /**
     * Checks whether sensors of the same type have readings that are
     * too far apart, which would indicate a faulty sensor or a
     * localized environmental anomaly inside the building zone.
     *
     * Three sensors in the same building cannot realistically read
     * wildly different values at the same time — a big gap means
     * something is wrong and the averaged result would be misleading.
     *
     * Thresholds used:
     *   Temperature : max allowed difference = 5.0 °C
     *   Humidity    : max allowed difference = 20.0 %
     *   CO2         : max allowed difference = 300.0 ppm
     *
     * @param threshold  the maximum acceptable difference between
     *                   any two sensors of the same type
     * @param sensorType the Class to filter (e.g. TemperatureSensor.class)
     * @param unit       display unit appended to values in the warning
     * @return a multi-line warning string if any pair exceeds the
     *         threshold, or null if all readings are consistent
     */
    public String checkSensorConsistency(
            double threshold,
            Class<? extends Sensor> sensorType,
            String unit) {

        // Step 1: Collect readings for this sensor type only
        ArrayList<Double> readings = new ArrayList<>();
        ArrayList<String> ids      = new ArrayList<>();

        for (Sensor s : sensorList) {
            if (sensorType.isInstance(s)) {
                readings.add(s.getReading());
                ids.add(s.getSensorId());
            }
        }

        // Step 2: Need at least 2 sensors to compare
        if (readings.size() < 2) return null;

        // Step 3: Compare every unique pair (i, j) where i < j
        StringBuilder warnings = new StringBuilder();
        for (int i = 0; i < readings.size(); i++) {
            for (int j = i + 1; j < readings.size(); j++) {
                double diff = Math.abs(readings.get(i) - readings.get(j));
                if (diff > threshold) {
                    warnings.append(
                        String.format(
                            "⚠ Inconsistency: %s=%.1f%s vs %s=%.1f%s  (diff=%.1f%s)%n",
                            ids.get(i), readings.get(i), unit,
                            ids.get(j), readings.get(j), unit,
                            diff, unit
                        )
                    );
                }
            }
        }

        return warnings.length() > 0 ? warnings.toString().trim() : null;
    }
}
