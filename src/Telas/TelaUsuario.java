
/*
 * The MIT License
 *
 * Copyright 2023 Alves Hatewa.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package Telas;

/**
 *
 * @author Alves Hatewa
 */
import java.sql.*;
import dal.Conexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaUsuario
     */
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() {
        initComponents();
        conexao = Conexao.conector();
    }

    private void consultar() {
        String sql = "Select * from tbusuario where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsNome.setText(rs.getString(2));
                txtUstel.setText(rs.getString(3));
                txtusLog.setText(rs.getString(4));
                txtUsSenha.setText(rs.getString(5));
                cboPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não Cadastrado!");
               
                cleanner();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void adicionar() {
        String sql = "insert into tbusuario (iduser,nomeuser,telefone,login,senha, perfil) values (?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsId.getText());
            pst.setString(2, txtUsNome.getText());
            pst.setString(3, txtUstel.getText());
            pst.setString(4, txtusLog.getText());
            pst.setString(5, txtUsSenha.getText());
            pst.setString(6, cboPerfil.getSelectedItem().toString());

            if (txtUsId.getText().isEmpty() || txtUsNome.getText().isEmpty() || txtusLog.getText().isEmpty() || txtUsSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
               
                int ad = pst.executeUpdate();

               
                if (ad > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Cadastrado com Sucesso!");
                    cleanner();

                } else {

                }
            }
            
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void actualizar() {
        String sql = "update tbusuario set  nomeuser=?, telefone=?, login=?, senha=?, perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsNome.getText());
            pst.setString(2, txtUstel.getText());
            pst.setString(3, txtusLog.getText());
            pst.setString(4, txtUsSenha.getText());
            pst.setString(5, cboPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsId.getText());
            

            if (txtUsId.getText().isEmpty() || txtUsNome.getText().isEmpty() || txtusLog.getText().isEmpty() || txtUsSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                
                int act = pst.executeUpdate();

                if (act > 0) {
                    JOptionPane.showMessageDialog(null, "Actualização efectuada com Sucesso!");
                    cleanner();

                } else {

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void remover() {

       
        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza de que Deseja Remover Este Usuário", "Aviso", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbusuario where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsId.getText());

                int apg = pst.executeUpdate();

                if (apg > 0) {
                    JOptionPane.showMessageDialog(null, "usuário Removido com Sucesso!");
                   cleanner();

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }
    

    public void cleanner() {
        txtUsId.setText("");
        txtUsNome.setText("");
        txtUstel.setText("");
        txtusLog.setText("");
        txtUsSenha.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblTel = new javax.swing.JLabel();
        lblLog = new javax.swing.JLabel();
        lblSe = new javax.swing.JLabel();
        txtUsId = new javax.swing.JTextField();
        txtUsNome = new javax.swing.JTextField();
        txtUstel = new javax.swing.JTextField();
        txtusLog = new javax.swing.JTextField();
        txtUsSenha = new javax.swing.JPasswordField();
        btnUsRem = new javax.swing.JButton();
        btnUsAdd = new javax.swing.JButton();
        btnUsCons = new javax.swing.JButton();
        btnUsAlt = new javax.swing.JButton();
        lblLog1 = new javax.swing.JLabel();
        cboPerfil = new javax.swing.JComboBox<>();
        lblLog2 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(752, 521));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblId.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblId.setText("* Id");
        getContentPane().add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 28, -1, -1));

        lblNome.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblNome.setText("* Nome de usuário");
        getContentPane().add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 79, -1, -1));

        lblTel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblTel.setText("Telefone");
        getContentPane().add(lblTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 148, -1, -1));

        lblLog.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblLog.setText("* Campos Obrigatórios");
        getContentPane().add(lblLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 150, 20));

        lblSe.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblSe.setText("Senha");
        getContentPane().add(lblSe, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 276, 42, 20));
        getContentPane().add(txtUsId, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 27, 63, -1));

        txtUsNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsNomeActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 78, 407, -1));
        getContentPane().add(txtUstel, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 147, 191, -1));

        txtusLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusLogActionPerformed(evt);
            }
        });
        getContentPane().add(txtusLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 209, 191, -1));

        txtUsSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsSenhaActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 277, 191, -1));

        btnUsRem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUsRem.setText("Remover");
        btnUsRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsRemActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, -1, -1));

        btnUsAdd.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUsAdd.setText("Adicionar");
        btnUsAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, -1, -1));

        btnUsCons.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUsCons.setText("Consultar");
        btnUsCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsConsActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsCons, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, -1, -1));

        btnUsAlt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUsAlt.setText("Alterar");
        btnUsAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsAltActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsAlt, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, -1, -1));

        lblLog1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblLog1.setText("* Login");
        getContentPane().add(lblLog1, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 210, -1, -1));

        cboPerfil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admim", "User" }));
        getContentPane().add(cboPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 144, 130, 30));

        lblLog2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblLog2.setText("* Perfil");
        getContentPane().add(lblLog2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 50, 20));

        setBounds(0, 0, 779, 527);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsSenhaActionPerformed

    private void txtusLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusLogActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusLogActionPerformed

    private void txtUsNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsNomeActionPerformed

    private void btnUsRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsRemActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btnUsRemActionPerformed

    private void btnUsConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsConsActionPerformed
        // TODO add your handling code here:
        //Chamando o método consultar
        consultar();
    }//GEN-LAST:event_btnUsConsActionPerformed

    private void btnUsAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsAddActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnUsAddActionPerformed

    private void btnUsAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsAltActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_btnUsAltActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsAdd;
    private javax.swing.JButton btnUsAlt;
    private javax.swing.JButton btnUsCons;
    private javax.swing.JButton btnUsRem;
    private javax.swing.JComboBox<String> cboPerfil;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLog;
    private javax.swing.JLabel lblLog1;
    private javax.swing.JLabel lblLog2;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSe;
    private javax.swing.JLabel lblTel;
    private javax.swing.JTextField txtUsId;
    private javax.swing.JTextField txtUsNome;
    private javax.swing.JPasswordField txtUsSenha;
    private javax.swing.JTextField txtUstel;
    private javax.swing.JTextField txtusLog;
    // End of variables declaration//GEN-END:variables
}
