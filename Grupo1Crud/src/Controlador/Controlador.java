package Controlador;

/**
 *
 * @author Pablo Jimenez Fuentes
 */
import Modelo.JDBC;
import Vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controlador {

    private Vista view;
    private JDBC modelo;

    public Controlador(Vista view, JDBC modelo) {
        this.view = view;
        this.modelo = modelo;
        this.inicializarEventos();
        this.getTipos();
    }

    private void inicializarEventos() {
        view.addListenerNuevo(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.limpiarCampos();
                view.setMensajeEstado("");
                view.cambiarModoFormulario(true);
            }
        });

        view.addListenerCancelar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.cambiarModoFormulario(false);
                view.setMensajeEstado("");
                view.limpiarCampos();
            }
        });

        view.addListenerSalir(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        view.addListenerAceptar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (insertar()) {
                    view.cambiarModoFormulario(false);
                }
            }
        });
    }

    private void getTipos() {
        List<String> listaTipos = new ArrayList<>();
        String sql = "SELECT Descripcion FROM Tipos";
        modelo.setSentenciaSQL(sql);

        if (modelo.ejecutarConsulta()) {
            ResultSet rs = modelo.getCursor();
            try {
                while (rs.next()) {
                    listaTipos.add(rs.getString(1));
                }
                modelo.cerrarCursor();
                view.rellenarComboTipos(listaTipos);
            } catch (SQLException e) {
                view.mostrarError(e.getMessage());
            }
        }
    }

    private boolean camposLlenos() {
        if (view.getNombre().isEmpty()
                || view.getPoblacion().isEmpty()
                || view.getProvincia().isEmpty()) {
            view.mostrarError("Todos los campos de texto son obligatorios.");
            return false;
        }

        if (view.getTipoAlojamientoSeleccionado() == null) {
            view.mostrarError("Debe seleccionar un tipo de alojamiento.");
            return false;
        }

        try {
            int cap = Integer.parseInt(view.getCapacidad());
            if (cap <= 0) {
                view.mostrarError("La capacidad debe ser mayor que 0.");
                return false;
            }
        } catch (NumberFormatException ex) {
            view.mostrarError("La capacidad debe ser un número entero válido.");
            return false;
        }

        return true;
    }

    public boolean insertar() {
        if (!camposLlenos()) {
            return false;
        }

        String nombre = view.getNombre();
        String poblacion = view.getPoblacion();
        String provincia = view.getProvincia();
        int capacidad = Integer.parseInt(view.getCapacidad());
        String ubicacion = view.esAislado() ? "Aislada" : "En población";

        Object tipoObj = view.getTipoAlojamientoSeleccionado();
        String descripcionTipo = (tipoObj != null) ? tipoObj.toString() : "";

        String sql = "INSERT INTO Alojamientos (Nombre, Poblacion, Provincia, Capacidad, Ubicacion, Tipo) VALUES ('"
                + nombre + "', '" + poblacion + "', '" + provincia + "', " + capacidad + ", '" + ubicacion + "', "
                + "(SELECT Codigo FROM Tipos WHERE Descripcion = '" + descripcionTipo + "'))";

        modelo.setSentenciaSQL(sql);

        if (modelo.ejecutarConsultaActualizable()) {
            view.setMensajeEstado("Registro añadido correctamente.");
            view.limpiarCampos();
            return true;
        } else {
            view.mostrarError("Error al insertar en la base de datos.");
            return false;
        }
    }
}