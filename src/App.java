import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EcoTrackPro system = new EcoTrackPro();
            EcoTrackUI ui = new EcoTrackUI(system);
            ui.setVisible(true);
        });
    }
}
