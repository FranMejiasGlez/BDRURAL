/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Mejias Gonzalez Francisco
 * @revision Andy Jan
 */
public class JDBC {

    private Connection conexion;
    private String sentenciaSQL;
    private ResultSet cursor;

    public boolean setConexion(Properties properties) {
        String nombreDriver = "";
        String urlConexion = "";
        String usuario = "";
        String contra = "";

        nombreDriver = properties.getProperty("driver");
        urlConexion = properties.getProperty("url");
        usuario = properties.getProperty("usuario");
        contra = properties.getProperty("password");

        try {
            try {
                Class.forName(nombreDriver);
            } catch (ClassNotFoundException ex) {
                System.out.println("Clase no encontrada");
                return false;
            }
            // Establecer timeout de login (en segundos)
            DriverManager.setLoginTimeout(5);
            this.conexion = DriverManager.getConnection(urlConexion, usuario, contra);
            System.out.println("Conexion exitosa");
            return true;
        } catch (SQLException ex) {
            if (ex.getMessage().contains("timeout") || ex.getMessage().contains("timed out") || ex.getMessage().contains("Connection refused")) {
                System.out.println("TIMEOUT: El servidor MySQL no responde o ha rechazado la conexion");
            }
            System.out.println("No se pudo realizar la conexion: " + ex.getMessage());
        }
        return false;
    }

    public Connection getConexion() {
        return this.conexion;
    }

    public void setSentenciaSQL(String strSQL) {
        this.sentenciaSQL = strSQL;
    }

    public boolean ejecutarConsulta() {
        try {
            Statement stmt = conexion.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            cursor = stmt.executeQuery(sentenciaSQL);

            return true;
        } catch (SQLException e) {
            System.out.println("Error ejecutando consulta: " + e.getMessage());
            return false;
        }
    }

    public boolean ejecutarConsultaActualizable() {
        try {
            Statement stmt = conexion.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            cursor = stmt.executeQuery(sentenciaSQL);

            return true;
        } catch (SQLException e) {
            System.out.println("Error ejecutando consulta actualizable: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getCursor() {
        return this.cursor;
    }

    public boolean cerrarCursor() {
        try {
            if (this.cursor != null) {
                this.cursor.close();
                this.cursor = null;
                return true;
            }
            return false;
        } catch (SQLException ex) {
            System.out.println("Error cerrando el cursor");
            return false;
        }

    }

    public boolean cerrarConexion() {
        try {
            if (this.conexion != null) {
                this.conexion.close();
                this.conexion = null;
                return true;
            }
            return false;
        } catch (SQLException ex) {
            System.out.println("Error cerrando conexion con BBDD");
            return false;
        }

    }
}
