/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * administrador.java
 *
 * Created on Oct 16, 2009, 11:39:13 AM
 */

package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author alirio
 */
public class PAdministrador extends javax.swing.JFrame {


    private PLogin parent;


    /** Creates new form administrador */
    public PAdministrador(PLogin parent) {

        initComponents();
        this.setSize(655, 452);
        this.setLocationRelativeTo(null);
        PFondoadmin fondo=new PFondoadmin();
        this.add(fondo,BorderLayout.CENTER);
        this.pack();
        this.getContentPane().setBackground(Color.WHITE);
        this.parent=parent;
        usuario.setText(parent.getTextoUsuario());
        

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adminEvento = new javax.swing.JButton();
        adminReportes = new javax.swing.JButton();
        adminPuntoVenta = new javax.swing.JButton();
        adminVendedor = new javax.swing.JButton();
        adminEscenario = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rol = new javax.swing.JLabel();
        usuario = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Administrador");
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        adminEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/event.gif"))); // NOI18N
        adminEvento.setText("Evento");
        adminEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminEventoActionPerformed(evt);
            }
        });

        adminReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/reports.gif"))); // NOI18N
        adminReportes.setText("Reportes");
        adminReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminReportesActionPerformed(evt);
            }
        });

        adminPuntoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/pvta.gif"))); // NOI18N
        adminPuntoVenta.setText("Punto de Venta");
        adminPuntoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminPuntoVentaActionPerformed(evt);
            }
        });

        adminVendedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/seller.gif"))); // NOI18N
        adminVendedor.setText("Vendedor");
        adminVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminVendedorActionPerformed(evt);
            }
        });

        adminEscenario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/esce.gif"))); // NOI18N
        adminEscenario.setText("Escenario");
        adminEscenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminEscenarioActionPerformed(evt);
            }
        });

        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/salir.gif"))); // NOI18N
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 3, 16));
        jLabel1.setForeground(new java.awt.Color(1, 1, 1));
        jLabel1.setText("Opciones de administración");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/sibo.jpg"))); // NOI18N

        jLabel3.setForeground(new java.awt.Color(153, 204, 255));
        jLabel3.setText("Usuario:");

        rol.setForeground(new java.awt.Color(153, 204, 255));
        rol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/imagenes/admin.PNG"))); // NOI18N
        rol.setText("Rol:");

        usuario.setText("nombre");

        jLabel6.setText("Administrador");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(rol))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(usuario)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(adminVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(adminPuntoVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(adminEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(adminEscenario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(adminReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                            .addComponent(salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adminVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adminEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adminPuntoVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(usuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rol)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminEscenario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminReportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(salir)
                        .addGap(33, 33, 33))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adminVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminVendedorActionPerformed
        // TODO add your handling code here:
        PAdminvendedor adve = new PAdminvendedor(this);
        this.setVisible(false);
        adve.setLocationRelativeTo(null);
        adve.setVisible(true);
    }//GEN-LAST:event_adminVendedorActionPerformed

    private void adminPuntoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminPuntoVentaActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        PAdminpuntoventa puntoventa = new PAdminpuntoventa(this);
        puntoventa.setLocationRelativeTo(null);
        puntoventa.setVisible(true);
    }//GEN-LAST:event_adminPuntoVentaActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        parent.setTextoUsuario("");
        parent.setTextoContrasena("");
        parent.setVisible(true);
    }//GEN-LAST:event_salirActionPerformed

    private void adminEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminEventoActionPerformed
        // TODO add your handling code here:
        PAdminevento adev = new  PAdminevento(this);
        adev.setLocationRelativeTo(null);
        this.setVisible(false);
        adev.setVisible(true);
    }//GEN-LAST:event_adminEventoActionPerformed

    private void adminEscenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminEscenarioActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        PAdminescenario admescenario = new PAdminescenario(this);
        admescenario.setLocationRelativeTo(null);
        admescenario.setVisible(true);
    }//GEN-LAST:event_adminEscenarioActionPerformed

    private void adminReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminReportesActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        PAdminreportes admreportes = new PAdminreportes(this);
        admreportes.setLocationRelativeTo(null);
        admreportes.setVisible(true);
    }//GEN-LAST:event_adminReportesActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int confirmado=JOptionPane.showConfirmDialog(this, "Esta acción cerrará la sesión activa\n¿Seguro que desea continuar?\n\n", "¿Cerrar Sesión?", JOptionPane.YES_NO_OPTION);
        if(confirmado==JOptionPane.YES_OPTION){
            parent.setTextoUsuario("");
            parent.setTextoContrasena("");
            parent.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminEscenario;
    private javax.swing.JButton adminEvento;
    private javax.swing.JButton adminPuntoVenta;
    private javax.swing.JButton adminReportes;
    private javax.swing.JButton adminVendedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel rol;
    private javax.swing.JButton salir;
    private javax.swing.JLabel usuario;
    // End of variables declaration//GEN-END:variables

}