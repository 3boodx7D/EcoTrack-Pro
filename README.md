# EcoTrack Pro 🌿 | Smart Environmental Dashboard

**EcoTrack Pro** is a modern, professional-grade desktop application written in **Java** utilizing a sleek **Java Swing GUI**. It serves as an environmental monitoring dashboard designed for offices, smart buildings, or workspaces to measure and analyze indoor climate quality. 

This project is meticulously structured according to **BTEC OOP (Object-Oriented Programming)** software design and academic criteria, featuring strict input validation, real-time dynamic dashboard updates, and clean modular separations between core business logic and user interface layers.

---

## 🎨 Preview & UI Design Aesthetics

The user interface is custom-styled with a contemporary palette inspired by modern tailwind/web designs:
*   **Backgrounds:** Slate / Light Gray (`#F1F5F9`)
*   **Controls Panels:** Clean White (`#FFFFFF`) with sleek borders (`#E2E8F0`)
*   **Primary Accent:** Slate Dark (`#0F172A`)
*   **Metric Cards:**
    *   🔵 **Temperature:** Soft Blue (`#EFF6FF`) with a clear (`#3B82F6`) accent.
    *   🟢 **Humidity:** Emerald Green (`#ECFDF5`) with a clear (`#10B981`) accent.
    *   🔴 **CO2 Levels:** Soft Red (`#FEF2F2`) with a clear (`#EF4444`) accent.
*   **Status Badges:** Modern rounded pill badges with contextual coloring (Optimal 🟢, Warning 🟡, Critical 🔴).

---

## ✨ Features

1.  **Dynamic Input Panels:** 
    *   Use dropdown selectors to choose the active quantity of sensors (1 to 3) for each environmental metric (Temperature, Humidity, CO2).
    *   Input text fields dynamically hide or show based on selected quantity to maintain a clean workspace and avoid clutter.
2.  **Robust Input Validation:**
    *   **Empty Checks:** Guarantees all active sensor inputs are filled out before execution.
    *   **Format Verification:** Prevents application crashes from alphabetical characters or illegal formats.
    *   **Logical Boundary Validations:** Validates that readings are realistic for an office/indoor setting (e.g., Temperature: `-20°C` to `60°C`, Humidity: `0%` to `100%`, CO2: `0 ppm` to `5000 ppm`). Any outlier triggers a descriptive dialog and aborts the execution safely.
3.  **Real-Time Dashboard Analytics:**
    *   Computes and displays accurate averages for all active metrics simultaneously.
    *   Renders customized details for individual active sensors in a modern scrolling list complete with dynamic status markers.
4.  **Actionable Environmental Diagnostics:**
    *   Analyzes averages and triggers real-time statuses like **Optimal**, **Warning**, or **Critical Alert** based on safety standards (e.g., warning if temperature exceeds 25°C or critical alert if CO2 levels cross 1000 ppm).

---

## 🏗️ OOP System Architecture (BTEC Aligned)

EcoTrack Pro fully implements the four core pillars of Object-Oriented Programming (OOP):

### 1. Encapsulation
Every component hides its internal state. The base `Sensor` class exposes attributes through standard getters and setters, ensuring modifications are strictly validated and tracked.
```java
public class Sensor {
    private String sensorId;
    private double reading;

    public Sensor(String sensorId, double reading) {
        this.sensorId = sensorId;
        this.reading = reading;
    }
    // Encapsulated accessors...
}
```

### 2. Inheritance
A parent-child hierarchy connects specialized sensors to a generalized base. Specialized classes (`CO2Sensor`, `TemperatureSensor`, and `HumiditySensor`) extend the main `Sensor` class, inheriting base parameters while defining custom context.

### 3. Polymorphism
Method overriding is utilized in each sub-sensor class to display tailored output:
*   `CO2Sensor` overrides `getInfo()` to output unit `ppm`.
*   `TemperatureSensor` overrides `getInfo()` to output unit `°C`.
*   `HumiditySensor` overrides `getInfo()` to output unit `%`.
Dynamic list iterations process all active subclasses via their base type `Sensor`.

### 4. Abstraction
Separation of concerns is maintained throughout the project. The GUI launcher class (`EcoTrackUI`) deals only with UI interactions and delegates environmental telemetry calculations entirely to the core `EcoTrackPro` controller class.

---

## 📂 Project Structure

```
EcoTrack Pro project/
│
├── .vscode/               # Workspace configuration files
├── bin/                   # Compiled .class files (output)
├── lib/                   # Project libraries and external dependencies
├── src/                   # Source Java files
│   ├── App.java           # Entry point (Main application loop)
│   ├── EcoTrackPro.java   # Environmental core business logic controller
│   ├── EcoTrackUI.java    # Swing graphical dashboard & input controls
│   ├── Sensor.java        # Base parent class for environmental sensors
│   ├── TemperatureSensor.java # Subclass specialized for Temperature telemetry
│   ├── HumiditySensor.java    # Subclass specialized for Humidity telemetry
│   └── CO2Sensor.java         # Subclass specialized for CO2 telemetry
│
├── README.md              # Project documentation (This file)
└── .gitignore             # Git ignore list for binaries & build logs
```

---

## 🚀 Getting Started

### Prerequisites
*   **Java Development Kit (JDK):** Version 11 or higher is recommended.
*   **IDE (Optional):** Visual Studio Code (with Extension Pack for Java) or IntelliJ IDEA.

### Compilation and Launch

#### Via Terminal / Command Line:
1. Navigate to the project root directory:
   ```bash
   cd "EcoTrack Pro project"
   ```
2. Compile all java files inside the `src` directory into the output folder:
   ```bash
   javac -d bin src/*.java
   ```
3. Launch the application:
   ```bash
   java -cp bin App
   ```

#### Via VS Code:
1. Open the project folder in VS Code.
2. Locate `src/App.java` and click on **Run** above the `main` method (or press `F5` / `Ctrl+F5`).

---

## 🛠️ Usage Flow

1.  **Configure Sensors:** In the left sidebar panel, adjust the quantity dropdowns for Temperature, Humidity, and CO2 sensors. 
2.  **Provide Readings:** Fill in active text boxes with valid numbers (e.g., Temp: `22`, Humid: `45`, CO2: `600`).
3.  **Analyze:** Click the dark **Run Environmental Analysis** button.
4.  **Observe Results:** Watch the metric cards update their averages, check the individual sensor states in the center dashboard list, and inspect the main banner status at the bottom for any environmental warnings!
