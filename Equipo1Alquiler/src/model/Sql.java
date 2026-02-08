/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class Sql {

    public Sql() {
    }

    public static String generarSQL(String provincia, int tipo,
            String ubicacion, int capacidad) {
        String sqlBase = "SELECT * FROM ALOJAMIENTOS WHERE DISPONIBLE=TRUE";
        String sqlFinal = sqlBase;
        return sqlFinal;
    }
}
