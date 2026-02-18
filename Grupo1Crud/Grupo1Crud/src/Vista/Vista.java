package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class Vista extends JFrame {

    private JPanel panelFormulario;
    private JPanel panelBotones;
    private JTextField txtNombre, txtPoblacion, txtProvincia, txtCapacidad;
    private JComboBox<String> cmbTipoAlojamiento;
    private JRadioButton rbtnPoblacion, rbtnAislado;
    private JButton btnNuevo, btnSalir, btnAceptar, btnCancelar;
    private JLabel lblMensajeEstado;

    public Vista() {
        setTitle("Mantenimiento de alojamientos");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- TÍTULO ---
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Alquiler de alojamientos rurales (RuralHome)");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // --- FORMULARIO (CENTRO) ---
        panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Población:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPoblacion = new JTextField(20);
        panelFormulario.add(txtPoblacion, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Provincia:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtProvincia = new JTextField(20);
        panelFormulario.add(txtProvincia, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Tipo Alojamiento:"), gbc);
        gbc.gridx = 1;
        cmbTipoAlojamiento = new JComboBox<>();
        cmbTipoAlojamiento.setPreferredSize(new Dimension(150, 25));
        panelFormulario.add(cmbTipoAlojamiento, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        rbtnPoblacion = new JRadioButton("En población");
        rbtnPoblacion.setSelected(true);
        panelFormulario.add(rbtnPoblacion, gbc);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Capacidad:"), gbc);
        gbc.gridx = 1;
        txtCapacidad = new JTextField(10);
        panelFormulario.add(txtCapacidad, gbc);

        JPanel panelRadio2 = new JPanel(new GridLayout(2, 1));
        JLabel lblUbi = new JLabel("Ubicación:");
        lblUbi.setHorizontalAlignment(SwingConstants.RIGHT);
        rbtnAislado = new JRadioButton("Aislado");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbtnPoblacion);
        grupo.add(rbtnAislado);

        panelRadio2.add(lblUbi);
        panelRadio2.add(rbtnAislado);
        gbc.gridx = 2;
        panelFormulario.add(panelRadio2, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // --- BOTONES (SUR) ---
        JPanel panelSur = new JPanel(new BorderLayout());
        panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Etiqueta de mensaje (Delante del botón nuevo)
        lblMensajeEstado = new JLabel(" ");
        lblMensajeEstado.setForeground(Color.BLUE);
        lblMensajeEstado.setFont(new Font("Arial", Font.BOLD, 12));

        btnNuevo = new JButton("Nuevo");
        btnSalir = new JButton("Salir");
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(lblMensajeEstado);
        panelBotones.add(btnNuevo);
        panelBotones.add(btnSalir);
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        panelSur.add(panelBotones, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        // Estado inicial según especificaciones
        cambiarModoFormulario(false);
    }

    // --- GETTERS ---
    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getPoblacion() {
        return txtPoblacion.getText().trim();
    }

    public String getProvincia() {
        return txtProvincia.getText().trim();
    }

    public String getCapacidad() {
        return txtCapacidad.getText().trim();
    }

    public Object getTipoAlojamientoSeleccionado() {
        return cmbTipoAlojamiento.getSelectedItem();
    }

    public boolean esAislado() {
        return rbtnAislado.isSelected();
    }

    // --- LISTENERS ---
    public void addListenerNuevo(ActionListener listener) {
        btnNuevo.addActionListener(listener);
    }

    public void addListenerAceptar(ActionListener listener) {
        btnAceptar.addActionListener(listener);
    }

    public void addListenerCancelar(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    public void addListenerSalir(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }

    // --- MÉTODOS VISUALES ---
    public void cambiarModoFormulario(boolean mostrarFormulario) {
        // "Inicialmente no se podrá introducir ningún dato" -> ocultamos panel
        panelFormulario.setVisible(mostrarFormulario);

        // Gestión de visibilidad de botones según especificaciones
        btnAceptar.setVisible(mostrarFormulario);
        btnCancelar.setVisible(mostrarFormulario);

        btnNuevo.setVisible(!mostrarFormulario);
        btnSalir.setVisible(!mostrarFormulario);

        if (mostrarFormulario) {
            txtNombre.requestFocus();
        }
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtPoblacion.setText("");
        txtProvincia.setText("");
        txtCapacidad.setText("");
        if (cmbTipoAlojamiento.getItemCount() > 0) {
            cmbTipoAlojamiento.setSelectedIndex(0);
        }
        rbtnPoblacion.setSelected(true);
        // NO limpiamos el lblMensajeEstado aquí, lo controla el controlador
    }

    public void rellenarComboTipos(List<String> tipos) {
        cmbTipoAlojamiento.removeAllItems();
        for (String tipo : tipos) {
            cmbTipoAlojamiento.addItem(tipo);
        }
    }

    public void setMensajeEstado(String mensaje) {
        lblMensajeEstado.setText(mensaje);
    }

    // Mantenemos este SOLO para errores de validación (campos vacíos)
    public void mostrarError(String mensajeError) {
        JOptionPane.showMessageDialog(this, mensajeError, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}