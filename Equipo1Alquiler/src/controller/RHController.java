/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.util.Properties;
import model.JDBC;
import view.AlquileresView;
import view.FinalView;
import javax.swing.JFrame;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class RHController {

    private JDBC conector;
    private Properties propiedades;
    private AlquileresView alquileresView;
    private JFrame framePrincipal;

    public ResultSet buscar(String provincia, int tipo,
            String ubicacion, int capacidad) {
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

        // Filtro por ubicación
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
        this.conector.setConexion(propiedades);
        return conector.getConexion() != null;
    }

    public ResultSet buscarTipos() {
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
}
