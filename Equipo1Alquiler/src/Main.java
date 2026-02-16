/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mejias Gonzalez Francisco
 */
import java.io.IOException;
import java.util.Properties;
import controller.RHController;
import java.io.InputStream;
import view.AlquileresView;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INICIO APLICACION ===");

        // 1. Cargar propiedades (antes de Swing)
        Properties propiedades = cargarPropiedades();
        if (propiedades == null) {
            mostrarError("No se pudo cargar BDRURAL.properties");
            return;
        }

        // 2. Crear controller (antes de Swing)
        final RHController controller = new RHController();
        controller.setPropiedades(propiedades);

        // 3. Intentar conectar BD (antes de Swing)
        if (!controller.conectarBD()) {
            System.out.println("ADVERTENCIA: No se pudo conectar a BD. La app funcionará sin datos.");
        }

        // 4. INICIALIZAR SWING EN EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                System.out.println("DEBUG: Creando interfaz en EDT...");
                try {
                    // Crear la vista
                    AlquileresView view = new AlquileresView(controller);

                    // Hacer visible PRIMERO
                    view.setVisible(true);
                    System.out.println("DEBUG: Ventana visible");

                    // Configurar controller DESPUES de visible
                    controller.setFramePrincipal(view);
                    controller.setAlquileresView(view);

                    // Cargar datos AHORA que la ventana es visible
                    view.cargarDatosIniciales();

                    System.out.println("DEBUG: Interfaz lista");
                } catch (Exception e) {
                    System.err.println("ERROR al crear interfaz: " + e.getMessage());
                    e.printStackTrace();
                    mostrarError("Error al iniciar la aplicación: " + e.getMessage());
                }
            }
        });

        System.out.println("=== MAIN COMPLETADO ===");
    }

    private static Properties cargarPropiedades() {
        Properties propiedades = new Properties();
        InputStream input = Main.class.getClassLoader().getResourceAsStream("BDRURAL.properties");

        if (input == null) {
            System.err.println("No se encuentra BDRURAL.properties");
            return null;
        }

        try {
            propiedades.load(input);
            input.close();
            return propiedades;
        } catch (IOException ex) {
            System.err.println("Error leyendo propiedades: " + ex.getMessage());
            return null;
        }
    }

    private static void mostrarError(final String mensaje) {
        System.err.println("ERROR: " + mensaje);
        // Intentar mostrar diálogo si Swing está disponible
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            // Si Swing no está listo, al menos imprimimos en consola
        }
    }
}
