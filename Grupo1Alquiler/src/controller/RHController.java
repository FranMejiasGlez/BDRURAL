/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import model.JDBC;
import view.AlquileresView;
import view.FinalView;
import javax.swing.JFrame;

/**
 *
 * @author Andy Jan
 * @revision Francisco Mejias
 */
public class RHController {

    private JDBC conector;
    private Properties propiedades;
    private AlquileresView alquileresView;
    private JFrame framePrincipal;
    private boolean conexionActiva = false;

    public ResultSet buscar(String provincia, int tipo,
            String ubicacion, int capacidad) {
        if (!verificarConexion()) {
            System.out.println("ERROR: No hay conexion activa para realizar la búsqueda");
            return null;
        }
        this.conector.setSentenciaSQL(generarSQL(provincia, tipo, ubicacion, capacidad));
        this.conector.ejecutarConsultaActualizable();

        ResultSet cursor = this.conector.getCursor();
        return cursor;
    }
// Setter para asignar la vista principal

    public void setPropiedades(Properties propiedades) {
        this.propiedades = propiedades;
    }

    public void setAlquileresView(AlquileresView view) {
        this.alquileresView = view;
    }
// Setter para el frame principal

    public void setFramePrincipal(JFrame frame) {
        this.framePrincipal = frame;
    }

    private String generarSQL(String provincia, int tipo, String ubicacion, int capacidad) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ALOJAMIENTOS WHERE Alquilado = 0");

        // Filtro por provincia
        if (provincia != null && !provincia.isEmpty()) {
            sql.append(" AND Provincia LIKE '%").append(provincia).append("%'");
        }

        // Filtro por tipo (0 = "Sin especificar")
        if (tipo > 0) {
            sql.append(" AND Tipo = ").append(tipo);
        }

        // Filtro por ubicacion
        if (ubicacion != null && !ubicacion.isEmpty()) {
            sql.append(" AND Ubicacion = '").append(ubicacion).append("'");
        }

        // Filtro por capacidad
        if (capacidad > 0) {
            sql.append(" AND Capacidad >= ").append(capacidad);
        }

        return sql.toString();
    }

    public boolean conectarBD() {
        if (this.conector == null) {
            this.conector = new JDBC();
        }
        boolean exito = this.conector.setConexion(propiedades);
        this.conexionActiva = exito && (conector.getConexion() != null);
        return this.conexionActiva;
    }

    public boolean isConexionActiva() {
        return conexionActiva;
    }

    public boolean verificarConexion() {
        if (!conexionActiva || conector == null || conector.getConexion() == null) {
            conexionActiva = false;
            return false;
        }
        try {
            // Hacer una consulta simple para verificar que la conexion sigue activa
            conector.setSentenciaSQL("SELECT 1");
            boolean resultado = conector.ejecutarConsulta();
            if (resultado && conector.getCursor() != null) {
                conector.getCursor().close();
            }
            conexionActiva = resultado;
            return resultado;
        } catch (Exception e) {
            conexionActiva = false;
            return false;
        }
    }

    public ResultSet buscarTipos() {
        if (!verificarConexion()) {
            System.out.println("ERROR: No hay conexion activa para cargar tipos");
            return null;
        }
        String sentencia = "SELECT * FROM TIPOS";
        conector.setSentenciaSQL(sentencia);
        conector.ejecutarConsulta();
        ResultSet cursor = conector.getCursor();
        return cursor;
    }

    public void volver() {
        if (alquileresView != null) {
            alquileresView.panelPrincipal.setVisible(true);
            if (alquileresView.getPanelResultados() != null) {
                alquileresView.getPanelResultados().setVisible(false);
            }
        }
    }

    public void finalizarBD() {
        if (conector != null) {
            conector.cerrarConexion();
        }
    }

    public void finalizar(int totalAlojamientos, int totalPersonas) {
        if (framePrincipal != null) {
            FinalView vistaFinal = new FinalView();
            vistaFinal.setMensajeFinal(totalAlojamientos, totalPersonas);

            framePrincipal.getContentPane().removeAll();
            framePrincipal.getContentPane().add(vistaFinal, java.awt.BorderLayout.CENTER);
            framePrincipal.revalidate();
            framePrincipal.repaint();
        }
    }

    /**
     * Alquila un alojamiento actualizando el campo Alquilado a 1 en la BD.
     * Utiliza manejo explícito de transacciones (RA2.j).
     *
     * @param referencia La referencia del alojamiento a alquilar
     * @return true si el alquiler fue exitoso, false en caso contrario
     */
    public boolean alquilarAlojamiento(String referencia) {
        if (!verificarConexion()) {
            System.out.println("ERROR: No hay conexion activa para realizar el alquiler");
            return false;
        }
        Connection conn = null;
        ResultSet rs = null;

        try {
            // 1. Obtener conexion y desactivar auto-commit (iniciar transaccion)
            conn = conector.getConexion();
            conn.setAutoCommit(false);

            // 2. Ejecutar consulta actualizable para obtener el registro
            // Seleccionamos la PK (Referencia) y la columna a actualizar (Alquilado)
            // La PK es obligatoria para que el ResultSet sea actualizable en JDBC
            String sql = "SELECT Referencia, Alquilado FROM ALOJAMIENTOS WHERE Referencia = '" + referencia + "' AND Alquilado = 0";
            conector.setSentenciaSQL(sql);
            conector.ejecutarConsultaActualizable();
            rs = conector.getCursor();

            // 3. Verificar que existe y modificar
            if (rs != null && rs.next()) {
                // Actualizar el campo Alquilado a 1
                rs.updateInt("Alquilado", 1);
                rs.updateRow();  // UPDATE ejecutado en la BD

                // 4. Confirmar transaccion
                conn.commit();
                System.out.println("DEBUG: Alquiler confirmado para referencia " + referencia);
                return true;
            } else {
                // No se encontro el alojamiento o ya está alquilado
                conn.rollback();
                System.out.println("DEBUG: No se encontro alojamiento disponible con referencia " + referencia);
                return false;
            }

        } catch (SQLException e) {
            // 5. En caso de error, revertir transaccion
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("DEBUG: Transaccion revertida por error");
                }
            } catch (SQLException ex) {
                System.out.println("ERROR: No se pudo hacer rollback: " + ex.getMessage());
            }
            System.out.println("ERROR al alquilar: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // 6. Limpiar recursos y restaurar auto-commit
            try {
                conector.cerrarCursor();
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.out.println("ERROR al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
