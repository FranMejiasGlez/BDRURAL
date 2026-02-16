/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mejias Gonzalez Francisco
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.JDBC;
import controller.RHController;
import java.io.InputStream;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        JDBC conector;
        Properties propiedades;
        propiedades = new Properties();
        Statement stmt;


        InputStream input = Main.class.getClassLoader().getResourceAsStream("BDRURAL.properties");

        if (input == null) {
            System.out.println("No se encuentra BDRURAL.properties en src/");
            return;
        }
        try {
            propiedades.load(input);
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        conector = new JDBC();
        conector.setConexion(propiedades);

        System.out.println(conector.getConexion().toString());
    }
}
