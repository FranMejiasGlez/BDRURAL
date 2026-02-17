/*
 * Utilidad para resetear el estado de alquileres en la base de datos
 * Permite poner Alquilado = 0 en todos los registros para pruebas
 */
package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.FileInputStream;
import model.JDBC;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class ResetAlquileres {

    public static void main(String[] args) {
        System.out.println("=== RESET DE ALQUILERES ===");
        System.out.println("Esta utilidad pondrá Alquilado = 0 en todos los alojamientos");
        System.out.println();

        JDBC conector = new JDBC();
        Properties propiedades = new Properties();

        try {
            // Cargar propiedades de conexión
            // Intentar varias rutas posibles
            String[] posiblesRutas = {
                "Equipo1Alquiler/src/BDRURAL.properties",
                "src/BDRURAL.properties",
                "BDRURAL.properties",
                "../src/BDRURAL.properties",
                "./src/BDRURAL.properties"
            };

            FileInputStream fis = null;
            for (String ruta : posiblesRutas) {
                try {
                    System.out.println("DEBUG: Intentando cargar: " + ruta);
                    fis = new FileInputStream(ruta);
                    System.out.println("DEBUG: Archivo encontrado en: " + ruta);
                    break;
                } catch (Exception e) {
                    // Intentar siguiente ruta
                }
            }

            if (fis == null) {
                System.out.println("ERROR: No se pudo encontrar BDRURAL.properties en ninguna ubicación");
                System.out.println("DEBUG: Directorio actual: " + System.getProperty("user.dir"));
                return;
            }

            propiedades.load(fis);
            fis.close();

            // Conectar a la base de datos
            if (!conector.setConexion(propiedades)) {
                System.out.println("ERROR: No se pudo conectar a la base de datos");
                return;
            }

            Connection conn = conector.getConexion();

            // Ejecutar UPDATE para resetear todos los alquilados
            String sql = "UPDATE ALOJAMIENTOS SET Alquilado = 0";
            Statement stmt = conn.createStatement();
            int filasAfectadas = stmt.executeUpdate(sql);

            System.out.println("✓ Reset completado exitosamente");
            System.out.println("  Filas actualizadas: " + filasAfectadas);
            System.out.println();
            System.out.println("Todos los alojamientos están ahora disponibles (Alquilado = 0)");

            stmt.close();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Solo cerrar si la conexión fue exitosa
            if (conector != null && conector.getConexion() != null) {
                conector.cerrarConexion();
            }
        }

        System.out.println();
        System.out.println("=== FIN DEL RESET ===");
    }
}
