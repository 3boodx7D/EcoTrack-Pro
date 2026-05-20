import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcoTrackUI extends JFrame {
    private EcoTrackPro system;

    private JComboBox<Integer> tempQtyBox;
    private JComboBox<Integer> humidQtyBox;
    private JComboBox<Integer> co2QtyBox;

    private JLabel[] tempLabels = new JLabel[3];
    private JTextField[] tempInputs = new JTextField[3];

    private JLabel[] humidLabels = new JLabel[3];
    private JTextField[] humidInputs = new JTextField[3];

    private JLabel[] co2Labels = new JLabel[3];
    private JTextField[] co2Inputs = new JTextField[3];

    private JPanel sensorCardsContainer;
    private JLabel avgTempLabel;
    private JLabel avgHumidLabel;
    private JLabel avgCO2Label;
    
    private JPanel avgTempCard;
    private JPanel avgHumidCard;
    private JPanel avgCO2Card;
    
    private JPanel statusCard;
    private JLabel systemStatusLabel;

    public EcoTrackUI(EcoTrackPro system) {
        this.system = system;
        initUI();
    }

    private void initUI() {
        setTitle("EcoTrack Pro - Environmental Dashboard");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(241, 245, 249));

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(241, 245, 249));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(360, 0));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel configTitle = new JLabel("Sensor Control Panel");
        configTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        configTitle.setForeground(new Color(15, 23, 42));
        configTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(configTitle);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel inputsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        inputsPanel.setBackground(Color.WHITE);
        inputsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel tempRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        tempRow.setBackground(Color.WHITE);
        tempRow.add(new JLabel("Temp Qty:"));
        tempQtyBox = new JComboBox<>(new Integer[]{1, 2, 3});
        tempRow.add(tempQtyBox);
        for (int i = 0; i < 3; i++) {
            tempLabels[i] = new JLabel("T" + (i + 1) + ":");
            tempInputs[i] = new JTextField(4);
            tempRow.add(tempLabels[i]);
            tempRow.add(tempInputs[i]);
        }
        inputsPanel.add(tempRow);

        JPanel humidRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        humidRow.setBackground(Color.WHITE);
        humidRow.add(new JLabel("Humid Qty:"));
        humidQtyBox = new JComboBox<>(new Integer[]{1, 2, 3});
        humidRow.add(humidQtyBox);
        for (int i = 0; i < 3; i++) {
            humidLabels[i] = new JLabel("H" + (i + 1) + ":");
            humidInputs[i] = new JTextField(4);
            humidRow.add(humidLabels[i]);
            humidRow.add(humidInputs[i]);
        }
        inputsPanel.add(humidRow);

        JPanel co2Row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        co2Row.setBackground(Color.WHITE);
        co2Row.add(new JLabel("CO2 Qty:"));
        co2QtyBox = new JComboBox<>(new Integer[]{1, 2, 3});
        co2Row.add(co2QtyBox);
        for (int i = 0; i < 3; i++) {
            co2Labels[i] = new JLabel("C" + (i + 1) + ":");
            co2Inputs[i] = new JTextField(4);
            co2Row.add(co2Labels[i]);
            co2Row.add(co2Inputs[i]);
        }
        inputsPanel.add(co2Row);

        leftPanel.add(inputsPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton runButton = new JButton("Run Environmental Analysis");
        runButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        runButton.setBackground(new Color(15, 23, 42));
        runButton.setForeground(Color.WHITE);
        runButton.setFocusPainted(false);
        runButton.setPreferredSize(new Dimension(200, 40));
        runButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        runButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runProceduralAnalysis();
            }
        });
        leftPanel.add(runButton);

        tempQtyBox.addActionListener(e -> updateFieldsState());
        humidQtyBox.addActionListener(e -> updateFieldsState());
        co2QtyBox.addActionListener(e -> updateFieldsState());

        updateFieldsState();

        JPanel rightPanel = new JPanel(new BorderLayout(15, 15));
        rightPanel.setBackground(new Color(241, 245, 249));

        JPanel averagesPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        averagesPanel.setBackground(new Color(241, 245, 249));

        avgTempCard = createMetricCard("Average Temp", "0.0 °C", new Color(239, 246, 255), new Color(59, 130, 246));
        avgTempLabel = (JLabel) avgTempCard.getClientProperty("valueLabel");
        
        avgHumidCard = createMetricCard("Average Humid", "0.0 %", new Color(236, 253, 245), new Color(16, 185, 129));
        avgHumidLabel = (JLabel) avgHumidCard.getClientProperty("valueLabel");

        avgCO2Card = createMetricCard("Average CO2", "0.0 ppm", new Color(254, 242, 242), new Color(239, 68, 68));
        avgCO2Label = (JLabel) avgCO2Card.getClientProperty("valueLabel");

        averagesPanel.add(avgTempCard);
        averagesPanel.add(avgHumidCard);
        averagesPanel.add(avgCO2Card);
        rightPanel.add(averagesPanel, BorderLayout.NORTH);

        JPanel activeSensorsPanel = new JPanel(new BorderLayout(5, 5));
        activeSensorsPanel.setBackground(Color.WHITE);
        activeSensorsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel activeSensorsTitle = new JLabel("Active Sensors Dashboard");
        activeSensorsTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        activeSensorsTitle.setForeground(new Color(51, 65, 85));
        activeSensorsPanel.add(activeSensorsTitle, BorderLayout.NORTH);

        sensorCardsContainer = new JPanel();
        sensorCardsContainer.setLayout(new BoxLayout(sensorCardsContainer, BoxLayout.Y_AXIS));
        sensorCardsContainer.setBackground(new Color(248, 250, 252));

        JScrollPane scrollPane = new JScrollPane(sensorCardsContainer);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249), 1));
        scrollPane.getViewport().setBackground(new Color(248, 250, 252));
        activeSensorsPanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(activeSensorsPanel, BorderLayout.CENTER);

        statusCard = new JPanel(new BorderLayout());
        statusCard.setBackground(Color.WHITE);
        statusCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(12, 20, 12, 20)
        ));

        systemStatusLabel = new JLabel("SYSTEM STATUS: NO DATA", SwingConstants.CENTER);
        systemStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        systemStatusLabel.setForeground(new Color(100, 116, 139));
        statusCard.add(systemStatusLabel, BorderLayout.CENTER);

        rightPanel.add(statusCard, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createMetricCard(String title, String defaultValue, Color bg, Color accentColor) {
        JPanel card = new JPanel(new GridLayout(2, 1, 2, 2));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor.brighter(), 1),
                new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(71, 85, 105));
        
        JLabel valueLabel = new JLabel(defaultValue);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(accentColor);
        
        card.add(titleLabel);
        card.add(valueLabel);
        card.putClientProperty("valueLabel", valueLabel);
        
        return card;
    }

    private void updateFieldsState() {
        int tempQty = (Integer) tempQtyBox.getSelectedItem();
        for (int i = 0; i < 3; i++) {
            tempLabels[i].setVisible(i < tempQty);
            tempInputs[i].setVisible(i < tempQty);
            if (i >= tempQty) {
                tempInputs[i].setText("");
            }
        }

        int humidQty = (Integer) humidQtyBox.getSelectedItem();
        for (int i = 0; i < 3; i++) {
            humidLabels[i].setVisible(i < humidQty);
            humidInputs[i].setVisible(i < humidQty);
            if (i >= humidQty) {
                humidInputs[i].setText("");
            }
        }

        int co2Qty = (Integer) co2QtyBox.getSelectedItem();
        for (int i = 0; i < 3; i++) {
            co2Labels[i].setVisible(i < co2Qty);
            co2Inputs[i].setVisible(i < co2Qty);
            if (i >= co2Qty) {
                co2Inputs[i].setText("");
            }
        }

        revalidate();
        repaint();
    }

    private JPanel createSensorCard(Sensor sensor) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setMaximumSize(new Dimension(Short.MAX_VALUE, 52));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel leftInfo = new JPanel(new GridLayout(2, 1, 2, 2));
        leftInfo.setBackground(Color.WHITE);
        
        JLabel idLabel = new JLabel(sensor.getSensorId());
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        idLabel.setForeground(new Color(51, 65, 85));
        
        JLabel typeLabel = new JLabel(sensor instanceof TemperatureSensor ? "Temperature Sensor" : sensor instanceof HumiditySensor ? "Humidity Sensor" : "CO2 Sensor");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        typeLabel.setForeground(new Color(148, 163, 184));
        
        leftInfo.add(idLabel);
        leftInfo.add(typeLabel);
        card.add(leftInfo, BorderLayout.WEST);

        String valueText = sensor.getReading() + " ";
        if (sensor instanceof TemperatureSensor) valueText += "°C";
        else if (sensor instanceof HumiditySensor) valueText += "%";
        else valueText += "ppm";
        
        JLabel readingLabel = new JLabel(valueText, SwingConstants.CENTER);
        readingLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        readingLabel.setForeground(new Color(15, 23, 42));
        card.add(readingLabel, BorderLayout.CENTER);

        String status = "Optimal";
        if (sensor instanceof TemperatureSensor && sensor.getReading() > 25.0) status = "Warning";
        else if (sensor instanceof HumiditySensor && (sensor.getReading() < 30.0 || sensor.getReading() > 60.0)) status = "Warning";
        else if (sensor instanceof CO2Sensor && sensor.getReading() > 1000.0) status = "Critical";

        JLabel statusBadge = new JLabel(status, SwingConstants.CENTER);
        statusBadge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusBadge.setOpaque(true);
        statusBadge.setPreferredSize(new Dimension(85, 25));

        if (status.equalsIgnoreCase("Optimal")) {
            statusBadge.setBackground(new Color(240, 253, 250));
            statusBadge.setForeground(new Color(13, 148, 136));
        } else if (status.equalsIgnoreCase("Warning")) {
            statusBadge.setBackground(new Color(254, 243, 199));
            statusBadge.setForeground(new Color(217, 119, 6));
        } else {
            statusBadge.setBackground(new Color(254, 242, 242));
            statusBadge.setForeground(new Color(220, 38, 38));
        }
        card.add(statusBadge, BorderLayout.EAST);

        return card;
    }

    private void runProceduralAnalysis() {
        try {
            system.clearSensors();
            sensorCardsContainer.removeAll();

            int sensorCount = 1;

            int tempQty = (Integer) tempQtyBox.getSelectedItem();
            for (int i = 0; i < tempQty; i++) {
                String text = tempInputs[i].getText().trim();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter Temperature T" + (i + 1) + " reading.", "Input Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double val = Double.parseDouble(text);
                if (val < -20.0 || val > 60.0) {
                    JOptionPane.showMessageDialog(this, "Illogical Input: Temperature reading " + val + " °C is unrealistic for indoor office environment.\nMust be between -20°C and 60°C.", "Invalid Reading", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Sensor sensor = new TemperatureSensor("S-" + sensorCount++, val);
                system.addSensor(sensor);
            }

            int humidQty = (Integer) humidQtyBox.getSelectedItem();
            for (int i = 0; i < humidQty; i++) {
                String text = humidInputs[i].getText().trim();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter Humidity H" + (i + 1) + " reading.", "Input Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double val = Double.parseDouble(text);
                if (val < 0.0 || val > 100.0) {
                    JOptionPane.showMessageDialog(this, "Illogical Input: Humidity reading " + val + " % is unrealistic.\nMust be between 0% and 100%.", "Invalid Reading", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Sensor sensor = new HumiditySensor("S-" + sensorCount++, val);
                system.addSensor(sensor);
            }

            int co2Qty = (Integer) co2QtyBox.getSelectedItem();
            for (int i = 0; i < co2Qty; i++) {
                String text = co2Inputs[i].getText().trim();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter CO2 C" + (i + 1) + " reading.", "Input Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double val = Double.parseDouble(text);
                if (val < 0.0 || val > 5000.0) {
                    JOptionPane.showMessageDialog(this, "Illogical Input: CO2 reading " + val + " ppm is unrealistic for office environment.\nMust be between 0 ppm and 5000 ppm.", "Invalid Reading", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Sensor sensor = new CO2Sensor("S-" + sensorCount++, val);
                system.addSensor(sensor);
            }

            for (Sensor s : system.getSensorList()) {
                JPanel card = createSensorCard(s);
                sensorCardsContainer.add(card);
                sensorCardsContainer.add(Box.createRigidArea(new Dimension(0, 8)));
            }

            double avgTemp = system.getAverageTemp();
            double avgHumid = system.getAverageHumid();
            double avgCO2 = system.getAverageCO2();

            avgTempLabel.setText(String.format("%.1f °C", avgTemp));
            avgHumidLabel.setText(String.format("%.1f %%", avgHumid));
            avgCO2Label.setText(String.format("%.1f ppm", avgCO2));

            String result = system.runAnalysis();
            systemStatusLabel.setText("SYSTEM STATUS: " + result.toUpperCase());

            if (result.contains("CRITICAL")) {
                statusCard.setBackground(new Color(254, 242, 242));
                systemStatusLabel.setForeground(new Color(220, 38, 38));
            } else if (result.contains("WARNING")) {
                statusCard.setBackground(new Color(254, 243, 199));
                systemStatusLabel.setForeground(new Color(217, 119, 6));
            } else {
                statusCard.setBackground(new Color(240, 253, 250));
                systemStatusLabel.setForeground(new Color(13, 148, 136));
            }

            sensorCardsContainer.revalidate();
            sensorCardsContainer.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers in all active reading fields.", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
