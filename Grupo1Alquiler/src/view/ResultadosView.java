/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Francisco Mejias
 * @revision Andy Jan
 */
public class ResultadosView extends javax.swing.JPanel {

    private controller.RHController controller;
    private int totalAlojamientos = 0;
    private int totalPersonas = 0;
    private java.util.ArrayList<Integer> capacidades = new java.util.ArrayList<>();
    private java.util.ArrayList<Integer> referencias = new java.util.ArrayList<>();
    // Filtros actuales para poder recargar después de alquilar
    private String filtroProvincia = "";
    private int filtroTipo = 0;
    private String filtroUbicacion = "";
    private int filtroCapacidad = 0;

    /**
     * Creates new form ResultadosView
     */
    public ResultadosView() {
        initComponents();

        // 1. Creamos el gestor pasando el JCheckBox que necesita el constructor
        ButtonEditor gestorBoton = new ButtonEditor(new javax.swing.JCheckBox());

        // 2. Usamos el MISMO objeto para las dos cosas (dibujar y clickar)
        // Usamos el índice 3 que es tu cuarta columna
        tablaResultados.getColumnModel().getColumn(3).setCellRenderer(gestorBoton);
        tablaResultados.getColumnModel().getColumn(3).setCellEditor(gestorBoton);
    }

    public void setController(controller.RHController controller) {
        this.controller = controller;
    }

    public int getTotalAlojamientos() {
        return totalAlojamientos;
    }

    public int getTotalPersonas() {
        return totalPersonas;
    }

    public void setCapacidadInicial(int capacidadBuscador) {
        this.totalPersonas = capacidadBuscador;
    }

    public void cargarResultados(java.sql.ResultSet rs, String provincia, int tipo, String ubicacion, int capacidad) {
        // Guardar los filtros para poder recargar después
        this.filtroProvincia = provincia;
        this.filtroTipo = tipo;
        this.filtroUbicacion = ubicacion;
        this.filtroCapacidad = capacidad;

        // Obtener el modelo de la tabla
        javax.swing.table.DefaultTableModel modelo =
                (javax.swing.table.DefaultTableModel) tablaResultados.getModel();

        // Limpiar la tabla actual (eliminar todas las filas)
        modelo.setRowCount(0);

        // Limpiar las listas
        capacidades.clear();
        referencias.clear();

        try {
            if (rs != null) {
                // Recorrer el ResultSet y añadir filas
                while (rs.next()) {
                    // Obtener los valores de cada columna
                    int referencia = rs.getInt("Referencia");
                    String nombre = rs.getString("Nombre");
                    String provinciaVal = rs.getString("Provincia");
                    int capacidadVal = rs.getInt("Capacidad");

                    // Guardar referencia y capacidad en las listas
                    referencias.add(referencia);
                    capacidades.add(capacidadVal);

                    // Crear fila con los datos (la columna 4 es el botón)
                    Object[] fila = new Object[]{
                        referencia, // Columna 0: Referencia
                        nombre, // Columna 1: Nombre
                        provinciaVal, // Columna 2: Provincia
                        "Alquilar" // Columna 3: Botón
                    };

                    // Añadir fila al modelo
                    modelo.addRow(fila);
                }

                System.out.println("DEBUG: Cargados " + modelo.getRowCount() + " resultados en la tabla");
                System.out.println("DEBUG: Capacidades guardadas: " + capacidades.size());
                System.out.println("DEBUG: Referencias guardadas: " + referencias.size());
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error cargando resultados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para recargar resultados después de alquilar
    public void recargarResultados() {
        if (controller != null) {
            try {
                System.out.println("DEBUG: Recargando resultados con filtros guardados...");
                java.sql.ResultSet rs = controller.buscar(filtroProvincia, filtroTipo, filtroUbicacion, filtroCapacidad);
                cargarResultados(rs, filtroProvincia, filtroTipo, filtroUbicacion, filtroCapacidad);
                if (rs != null) {
                    rs.close();
                }

                // Forzar redibujado de la tabla
                tablaResultados.revalidate();
                tablaResultados.repaint();
                System.out.println("DEBUG: Tabla recargada y redibujada");
            } catch (Exception e) {
                System.out.println("Error al recargar resultados: " + e.getMessage());
                e.printStackTrace();
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "REFERENCIA", "NOMBRE", "PROVINCIA", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaResultadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaResultados);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Volver principal");
        jButton1.setToolTipText("Volver a pantalla principal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.getAccessibleContext().setAccessibleDescription("");

        jButton2.setText("Finalizar alquiler");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (controller != null) {
            controller.volver();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablaResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMouseClicked
        // El manejo del clic en el botón Alquilar está en ButtonEditor.getCellEditorValue()
    }//GEN-LAST:event_tablaResultadosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (controller != null) {
            controller.finalizar(totalAlojamientos, totalPersonas);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaResultados;
    // End of variables declaration//GEN-END:variables

    class ButtonEditor extends javax.swing.DefaultCellEditor implements javax.swing.table.TableCellRenderer {

        protected javax.swing.JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow = -1;

        public ButtonEditor(javax.swing.JCheckBox checkBox) {
            super(checkBox);
            button = new javax.swing.JButton();
            button.setOpaque(true);
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // Ejecutar el alquiler directamente aquí, sin fireEditingStopped()
                    procesarAlquiler();
                }
            });
        }

        private void procesarAlquiler() {
            final int fila = currentRow;
            System.out.println("DEBUG: Procesando alquiler para fila: " + fila);

            if (fila < 0 || fila >= ResultadosView.this.capacidades.size()) {
                System.out.println("DEBUG: Fila inválida");
                return;
            }

            System.out.println("DEBUG: Tamaño de lista capacidades: " + ResultadosView.this.capacidades.size());

            try {
                // Obtener la capacidad y referencia del alojamiento
                final int capacidad = ResultadosView.this.capacidades.get(fila);
                final int referenciaInt = ResultadosView.this.referencias.get(fila);
                final String referencia = String.valueOf(referenciaInt);
                System.out.println("DEBUG: Fila " + fila + ", Referencia: " + referencia + ", Capacidad: " + capacidad);

                // Verificar que el controller esté disponible
                if (controller == null) {
                    javax.swing.JOptionPane.showMessageDialog(button,
                            "ERROR: Controller no inicializado", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    cancelCellEditing();
                    return;
                }

                // Llamar al método del controller para alquilar en la BD (RA2.f y RA2.j)
                boolean exito = controller.alquilarAlojamiento(referencia);

                if (exito) {
                    // Actualizar contadores solo si el alquiler fue exitoso
                    totalAlojamientos++;
                    totalPersonas += capacidad;

                    // Eliminar la fila inmediatamente
                    try {
                        javax.swing.table.DefaultTableModel modelo =
                                (javax.swing.table.DefaultTableModel) tablaResultados.getModel();
                        modelo.removeRow(fila);
                        ResultadosView.this.capacidades.remove(fila);
                        ResultadosView.this.referencias.remove(fila);
                        System.out.println("DEBUG: Fila eliminada de la tabla");
                    } catch (Exception e) {
                        System.out.println("DEBUG: Error al eliminar fila: " + e.getMessage());
                    }

                    javax.swing.JOptionPane.showMessageDialog(button,
                            "Alquiler realizado exitosamente.\nTotal personas acumuladas: " + totalPersonas);
                } else {
                    // El alquiler falló (ya estaba alquilado o error en BD)
                    javax.swing.JOptionPane.showMessageDialog(button,
                            "ERROR: No se pudo realizar el alquiler.\nEl alojamiento ya no está disponible o ha ocurrido un error.",
                            "Error de alquiler", javax.swing.JOptionPane.ERROR_MESSAGE);

                    // Recargar resultados para mostrar el estado actual
                    recargarResultados();
                }

            } catch (Exception e) {
                System.out.println("DEBUG: Error al procesar alquiler: " + e.getMessage());
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(button,
                        "ERROR inesperado: " + e.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

            // Cancelar la edición para evitar que la tabla intente actualizar la celda
            cancelCellEditing();
        }

        // ESTO ES LO QUE TE FALTABA (El Renderer)
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            button.setText((value == null) ? "Alquilar" : value.toString());
            return button;
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "Alquilo" : value.toString();
            button.setText(label);
            isPushed = true;
            currentRow = row;  // Guardamos la fila actual para usarla en el ActionListener
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            // La lógica de alquiler ahora está en procesarAlquiler() llamado desde el ActionListener
            // Este método solo retorna el valor para que la tabla complete la edición
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            currentRow = -1;
            return super.stopCellEditing();
        }
    }
}
