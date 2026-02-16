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
import view.ResultadosView;
import javax.swing.JFrame;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class RHController {

    private int alquilados;
    private int personas;
    private JDBC conector;
    private Properties propiedades;
    private AlquileresView alquileresView;
    private JFrame framePrincipal;

    public void buscar(String provincia, int tipo,
            String ubicacion, int capacidad) {
        this.conector.setSentenciaSQL(generarSQL(provincia, tipo, ubicacion, capacidad));
        this.conector.ejecutarConsultaActualizable();

        ResultSet cursor = this.conector.getCursor();

    }
// Setter para asignar la vista principal

    public void setAlquileresView(AlquileresView view) {
        this.alquileresView = view;
    }
// Setter para el frame principal

    public void setFramePrincipal(JFrame frame) {
        this.framePrincipal = frame;
    }

    private String generarSQL(String provincia, int tipo,
            String ubicacion, int capacidad) {
        String sqlBase = "SELECT * FROM ALOJAMIENTOS WHERE DISPONIBLE=TRUE";
        String sqlFinal = sqlBase;
        return sqlFinal;
    }

    public boolean conectarBD() {
        this.conector = new JDBC();
        this.conector.setConexion(propiedades);
        return conector.getConexion() != null;
    }

    public ResultSet buscarTipos() {
        ResultSet cursor = null;
        return cursor;
    }

    public boolean alquilar(int referencia) {
        boolean alquilado = false;
        return alquilado;
    }

    public void volver() {
        if (alquileresView != null) {
            alquileresView.panelPrincipal.setVisible(true);
            // Necesitamos acceder al panel de resultados
            // Lo haremos mediante un método getter en AlquileresView
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
