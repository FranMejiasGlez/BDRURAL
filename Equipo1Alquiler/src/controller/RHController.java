/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class RHController {

    private int alquilados;
    private int personas;

    public ResultSet buscar(String provincia, int tipo,
            String ubicacion, int capacidad) {
        ResultSet cursor = null;
        return cursor;
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

    public void finalizar() {
    }
}
