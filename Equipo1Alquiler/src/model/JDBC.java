/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class JDBC {

    private Connection conexion;
    private String sentenciaSQL;
    private ResultSet cursor;

    public boolean setConexion(File properties) {
        return false;
    }

    public Connection getConexion() {
        return null;
    }

    public void setSentenciaSQL(String strSQL) {
    }

    public boolean ejecutarConsulta() {
        return false;
    }

    public boolean ejecutarConsultaActualizable() {
        return false;
    }

    public ResultSet getCursor() {
        return null;
    }

    public boolean cerrarCursor() {
        return false;
    }

    public boolean cerrarConexion() {
        return false;
    }
}
