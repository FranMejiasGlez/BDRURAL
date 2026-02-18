/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Francisco Mejias
 * @revision Andy Jan
 */
public class AlquileresView extends javax.swing.JFrame {

    private controller.RHController controller;

    public AlquileresView() {
        System.out.println("DEBUG: AlquileresView() constructor - ANTES de initComponents");

        // Configurar JFrame antes de initComponents
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setTitle("RURALHOME - Alquiler de Alojamientos");
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar en pantalla

        initComponents();
        System.out.println("DEBUG: AlquileresView() constructor - DESPUES de initComponents");

        panelPrincipal.setVisible(true);   // Este debe estar SIEMPRE true al inicio
        panelResultados.setVisible(false); // Este oculto hasta que busquemos

        this.revalidate(); // Obliga al JFrame a organizar sus piezas
        this.repaint();    // Dibuja las piezas organizadas
        System.out.println("DEBUG: AlquileresView() constructor - FIN");
    }

    public AlquileresView(final controller.RHController controller) {
        this(); // Llama al constructor por defecto que ya existe
        System.out.println("DEBUG: Constructor con controller - INICIO");
        this.controller = controller;

        // Pasar el controller al panel de resultados
        if (panelResultados != null) {
            panelResultados.setController(controller);
        }

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.finalizarBD();
            }
        });

        // Verificar estado de conexion y actualizar interfaz
        actualizarEstadoConexion();

        System.out.println("DEBUG: Constructor con controller - FIN (datos se cargaran despues)");
    }

    /**
     * Actualiza el estado de la interfaz segun la conexion a BD
     */
    private void actualizarEstadoConexion() {
        if (controller != null && !controller.isConexionActiva()) {
            // No hay conexion: deshabilitar boton de busqueda
            botonBuscar.setEnabled(false);
            botonBuscar.setText("Sin conexion a BD");
            botonBuscar.setToolTipText("No se puede buscar: sin conexion a la base de datos");

            // Mostrar mensaje de advertencia en el titulo
            setTitle("RURALHOME - Alquiler de Alojamientos [SIN CONEXION]");

            // Agregar etiqueta de advertencia si no existe
            agregarEtiquetaSinConexion();
        } else if (controller != null) {
            // Hay conexion: asegurar que el boton esta habilitado
            botonBuscar.setEnabled(true);
            botonBuscar.setText("Buscar");
            botonBuscar.setToolTipText("Buscar alojamientos");
            setTitle("RURALHOME - Alquiler de Alojamientos");
        }
    }
    private javax.swing.JLabel labelSinConexion;

    /**
     * Agrega una etiqueta de advertencia visible cuando no hay conexion
     */
    private void agregarEtiquetaSinConexion() {
        if (labelSinConexion == null) {
            labelSinConexion = new javax.swing.JLabel("SIN CONEXION A BASE DE DATOS");
            labelSinConexion.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            labelSinConexion.setForeground(java.awt.Color.RED);
            labelSinConexion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            // Agregar al panel principal, arriba de todo
            java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            gbc.insets = new java.awt.Insets(20, 0, 0, 0);
            panelPrincipal.add(labelSinConexion, gbc);
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
    }

    // Metodo para cargar datos DESPUES de que la ventana es visible
    public void cargarDatosIniciales() {
        System.out.println("DEBUG: cargarDatosIniciales() - Iniciando carga en segundo plano");

        // Cargar tipos en un hilo separado para no bloquear la interfaz
        new Thread(new Runnable() {
            public void run() {
                try {
                    cargarTipos();
                    System.out.println("DEBUG: cargarDatosIniciales() - Tipos cargados correctamente");
                } catch (Exception e) {
                    System.err.println("DEBUG: Error cargando tipos: " + e.getMessage());

                }
            }
        }).start();
    }

    public view.ResultadosView getPanelResultados() {
        return panelResultados;
    }

    public void cargarTipos() {
        System.out.println("DEBUG: cargarTipos() llamado");
        System.out.println("DEBUG: controller = " + controller);
        if (controller != null) {
            // Verificar conexion antes de intentar cargar
            if (!controller.isConexionActiva()) {
                System.out.println("DEBUG: Sin conexion a BD, no se cargaran tipos");
                // Mantener solo "Sin especificar" en el combo
                selectorTipo.removeAllItems();
                selectorTipo.addItem("Sin especificar");
                return;
            }

            selectorTipo.removeAllItems();
            selectorTipo.addItem("Sin especificar");
            try {
                java.sql.ResultSet rs = controller.buscarTipos();
                System.out.println("DEBUG: ResultSet obtenido = " + rs);
                if (rs != null) {
                    int count = 0;
                    while (rs.next()) {
                        count++;
                        String descripcion = rs.getString("Descripcion");
                        System.out.println("DEBUG: Tipo #" + count + " = " + descripcion);
                        selectorTipo.addItem(descripcion);
                    }
                    System.out.println("DEBUG: Total tipos cargados = " + count);
                }
            } catch (Exception e) {
                System.out.println("DEBUG: ERROR = " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("DEBUG: controller es NULL");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new javax.swing.JPanel();
        labelProvincia = new javax.swing.JLabel();
        inputTextProvincia = new javax.swing.JTextField();
        labelTipoAlojam = new javax.swing.JLabel();
        selectorTipo = new javax.swing.JComboBox();
        labelUbicacion = new javax.swing.JLabel();
        checkBoxEnPoblacion = new javax.swing.JCheckBox();
        checkBoxAislada = new javax.swing.JCheckBox();
        labelCapacidad = new javax.swing.JLabel();
        inputTextCapacidad = new javax.swing.JTextField();
        labelCapacidadPersonas = new javax.swing.JLabel();
        botonBuscar = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();
        panelResultados = new view.ResultadosView();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RURALHOME");

        panelPrincipal.setMaximumSize(new java.awt.Dimension(200, 200));
        panelPrincipal.setMinimumSize(new java.awt.Dimension(200, 200));
        panelPrincipal.setName("panelPrincipal"); // NOI18N
        panelPrincipal.setLayout(new java.awt.GridBagLayout());

        labelProvincia.setText("Provincia:");
        labelProvincia.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panelPrincipal.add(labelProvincia, gridBagConstraints);

        inputTextProvincia.setColumns(20);
        inputTextProvincia.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        inputTextProvincia.setMaximumSize(new java.awt.Dimension(200, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panelPrincipal.add(inputTextProvincia, gridBagConstraints);

        labelTipoAlojam.setText("Tipo de alojamiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 40);
        panelPrincipal.add(labelTipoAlojam, gridBagConstraints);

        selectorTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sin especificar", "Item 2", "Item 3", "Item 4" }));
        selectorTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorTipoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panelPrincipal.add(selectorTipo, gridBagConstraints);

        labelUbicacion.setText("Ubicacion:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelPrincipal.add(labelUbicacion, gridBagConstraints);

        checkBoxEnPoblacion.setText("En poblacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(checkBoxEnPoblacion, gridBagConstraints);

        checkBoxAislada.setText("Aislada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(checkBoxAislada, gridBagConstraints);

        labelCapacidad.setText("Capacidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelPrincipal.add(labelCapacidad, gridBagConstraints);

        inputTextCapacidad.setColumns(5);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelPrincipal.add(inputTextCapacidad, gridBagConstraints);

        labelCapacidadPersonas.setText("Personas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, -110, 0, 0);
        panelPrincipal.add(labelCapacidadPersonas, gridBagConstraints);

        botonBuscar.setText("Buscar");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panelPrincipal.add(botonBuscar, gridBagConstraints);

        getContentPane().add(panelPrincipal, java.awt.BorderLayout.CENTER);

        labelTitulo.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        labelTitulo.setForeground(new java.awt.Color(103, 0, 255));
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("RURALHOME todo en turismo ");
        labelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 20, 1));
        getContentPane().add(labelTitulo, java.awt.BorderLayout.NORTH);
        getContentPane().add(panelResultados, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectorTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectorTipoActionPerformed

    private boolean esTextoValido(String texto) {
        if (texto == null || texto.isEmpty()) {
            return true; // Vacio es valido (sin filtro)
        }
        // Caracteres peligrosos para SQL Injection
        String caracteresPeligrosos = ";'\"--/*";
        for (char c : caracteresPeligrosos.toCharArray()) {
            if (texto.indexOf(c) >= 0) {
                return false;
            }
        }
        return true;
    }
    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        // 0. Verificar conexion antes de operar
        if (controller == null || !controller.isConexionActiva()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No hay conexion a la base de datos.\n"
                    + "Por favor, verifique que el servidor MySQL este en ejecucion y reinicie la aplicacion.",
                    "Sin conexion",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Capturar los valores de los filtros
        String provincia = inputTextProvincia.getText().trim();
        if (!esTextoValido(provincia)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "La provincia contiene caracteres no permitidos",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Obtener el indice seleccionado del combo (0 = "Sin especificar", 1 = Casa Rural, etc.)
        int tipoSeleccionado = selectorTipo.getSelectedIndex();

        // Determinar ubicacion segun los checkboxes
        String ubicacion = "";
        if (checkBoxEnPoblacion.isSelected() && !checkBoxAislada.isSelected()) {
            ubicacion = "En poblacion";
        } else if (checkBoxAislada.isSelected() && !checkBoxEnPoblacion.isSelected()) {
            ubicacion = "Aislada";
        } else {
            ubicacion = ""; // Ambos o ninguno seleccionado = sin filtro
        }

        // Capturar capacidad
        int capacidad = 0;
        try {
            String textoCapacidad = inputTextCapacidad.getText().trim();
            if (!textoCapacidad.isEmpty()) {
                capacidad = Integer.parseInt(textoCapacidad);
            }
        } catch (NumberFormatException e) {
            capacidad = 0; // Si no es numero valido, sin filtro
        }

        // 2. Pasar la capacidad inicial al panel de resultados (para el contador)
        panelResultados.setCapacidadInicial(capacidad);

        // 3. Llamar al controller para buscar
        if (controller != null) {
            try {
                java.sql.ResultSet rs = controller.buscar(provincia, tipoSeleccionado, ubicacion, capacidad);

                // 4. Llenar la tabla con los resultados (pasando los filtros para poder recargar despues)
                panelResultados.cargarResultados(rs, provincia, tipoSeleccionado, ubicacion, capacidad);

                // 5. Cerrar el ResultSet despues de usarlo
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println("Error en busqueda: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 6. Cambiar de pantalla (ocultar principal, mostrar resultados)
        panelPrincipal.setVisible(false);
        panelResultados.setVisible(true);
        this.getContentPane().validate();
        this.getContentPane().repaint();
        this.pack();
    }//GEN-LAST:event_botonBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AlquileresView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlquileresView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlquileresView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlquileresView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlquileresView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonBuscar;
    private javax.swing.JCheckBox checkBoxAislada;
    private javax.swing.JCheckBox checkBoxEnPoblacion;
    private javax.swing.JTextField inputTextCapacidad;
    private javax.swing.JTextField inputTextProvincia;
    private javax.swing.JLabel labelCapacidad;
    private javax.swing.JLabel labelCapacidadPersonas;
    private javax.swing.JLabel labelProvincia;
    private javax.swing.JLabel labelTipoAlojam;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelUbicacion;
    public javax.swing.JPanel panelPrincipal;
    private view.ResultadosView panelResultados;
    private javax.swing.JComboBox selectorTipo;
    // End of variables declaration//GEN-END:variables
}
