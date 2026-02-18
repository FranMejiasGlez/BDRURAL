
import Controlador.Controlador;
import Modelo.JDBC;
import Vista.Vista;
import java.io.File;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pablo Jiménez Fuentes
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDBC modelo = new JDBC();
                File archivoConfig = new File(".\\src\\BD.properties");

                if (modelo.setConexion(archivoConfig)) {
                    Vista view = new Vista();
                    new Controlador(view, modelo);
                    view.setVisible(true);
                } else {
                    System.err.println("Error: No se ha podido conectar a la base de datos. Revise config.properties");
                }
            }
        });
    }
}
