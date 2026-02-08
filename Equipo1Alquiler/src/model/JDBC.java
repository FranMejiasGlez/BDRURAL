/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mejias Gonzalez Francisco
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
        try {
            properties.load(new FileInputStream("src/MySql.properties"));
            nombreDriver = properties.getProperty("driver");
            urlConexion = properties.getProperty("url");
            usuario = properties.getProperty("usuario");
            contra = properties.getProperty("password");
        } catch (IOException ex) {
            System.out.println("Error de E/S con fichero properties");
        }
        try {
            try {
                Class.forName(nombreDriver);
            } catch (ClassNotFoundException ex) {
                System.out.println("Clase no encontrada");
            }
            this.conexion = DriverManager.getConnection(urlConexion, usuario, contra);
            System.out.println("Conexion exitosa");
        } catch (SQLException ex) {
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
            Statement stmt = conexion.createStatement();
            cursor = stmt.executeQuery(sentenciaSQL);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean ejecutarConsultaActualizable() {
        return false;
    }

    public ResultSet getCursor() {
        return this.cursor;
    }

    public boolean cerrarCursor() {
        try {
            this.cursor.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error cerrando el cursor");
            return false;
        }

    }

    public boolean cerrarConexion() {
        try {
            this.conexion.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error cerrando conexion con BBDD");
            return false;
        }

    }
}
