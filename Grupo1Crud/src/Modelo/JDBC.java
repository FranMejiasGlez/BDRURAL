package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Alvaro Pérez Salvador
 */
public class JDBC {

    private Connection conexion;
    private String sentenciasSQL;
    private ResultSet cursor;

    public JDBC() {
    }

    public boolean setConexion(File propertiesFile) {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(propertiesFile);
            props.load(fis);

            String driver = props.getProperty("driver");
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            Class.forName(driver);
            this.conexion = DriverManager.getConnection(url, user, password);
            return true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Connection getConexion() {
        return this.conexion;
    }

    public void setSentenciaSQL(String strSQL) {
        this.sentenciasSQL = strSQL;
    }

    public boolean ejecutarConsulta() {
        try {
            Statement stmt = this.conexion.createStatement();
            this.cursor = stmt.executeQuery(this.sentenciasSQL);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ejecutarConsultaActualizable() {
        Statement stmt = null;
        try {
            stmt = this.conexion.createStatement();
            int filasAfectadas = stmt.executeUpdate(this.sentenciasSQL);
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ResultSet getCursor() {
        return this.cursor;
    }

    public boolean cerrarCursor() {
        try {
            if (this.cursor != null) {
                Statement stmt = this.cursor.getStatement();
                this.cursor.close();
                if (stmt != null) {
                    stmt.close();
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cerrarConexion() {
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                this.conexion.close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}