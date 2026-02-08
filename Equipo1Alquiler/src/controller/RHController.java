/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.util.Properties;
import model.JDBC;
import model.Sql;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class RHController {

    private int alquilados;
    private int personas;
    private JDBC conector;
    private Properties propiedades;

    public void buscar(String provincia, int tipo,
            String ubicacion, int capacidad) {
        this.conector.setSentenciaSQL(Sql.generarSQL(provincia, tipo, ubicacion, capacidad));
        this.conector.ejecutarConsultaActualizable();

        ResultSet cursor = this.conector.getCursor();

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
    }

    public boolean finalizar() {
        return this.conector.cerrarConexion();
    }
}
