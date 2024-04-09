
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

import java.sql.*;
import dal.Conexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Classe Responsável por Realizar toda a actividade relativa a Ordem de
 * Serviço.
 *
 * @author Alves Hatewa
 */
public class OrdemServico extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String tipo;

    /**
     * Creates new form OrdemServico
     */
    public OrdemServico() {
        initComponents();
        conexao = Conexao.conector();
    }

    private void pesquisarCliente() {

        String sql = "select idcliente as id, nomecliente as nome, email as email from tbclientes where nomecliente like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisarCli.getText() + "%");
            rs = pst.executeQuery();
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void PesquisarOrdemServico() {

        String num_OS = JOptionPane.showInputDialog("Insira o Número da Ordem de Serviço");
        String sql = "select * from tbordemservico where os = " + num_OS;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));

                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("Ordem de Serviço")) {
                    rbtOs.setSelected(true);
                    tipo = "Ordem de Serviço";

                } else {
                    rbtOrc.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOs.setSelectedItem(rs.getString(4));
                txtEquip.setText(rs.getString(5));
                txtProblema.setText(rs.getString(6));
                txtServ.setText(rs.getString(7));
                txtTec.setText(rs.getString(8));
                txtValor.setText(rs.getString(9));
                txtCliid.setText(rs.getString(10));

                btnAdd.setEnabled(false);
                txtPesquisarCli.setEnabled(false);
                tblClientes.setVisible(false);
                btnSearch.setEnabled(false);

                btnEdit.setEnabled(true);
                btnDel.setEnabled(true);
                btnPrint.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Ordem de Servíço não Adicionada");
            }

        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Ordem de Serviço Inválida");

        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, e1);
        }
    }

    private void alterarOrdemServico() {
        String sql = "update tbordemservico set tipo = ?, situacao = ?, mercadoria = ?, problema = ?, servico = ?, tecnico = ?, valor = ? where os = ? ";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOs.getSelectedItem().toString());
            pst.setString(3, txtEquip.getText());
            pst.setString(4, txtProblema.getText());
            pst.setString(5, txtServ.getText());
            pst.setString(6, txtTec.getText());

            pst.setString(7, txtValor.getText().replace(",", "."));
            pst.setString(8, txtOs.getText());

            if ((txtCliid.getText().isEmpty()) || (txtEquip.getText().isEmpty()) || (txtProblema.getText().isEmpty() || cboOs.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço Alterada com Sucesso!");

                    txtOs.setText(" ");
                    txtData.setText(" ");
                    limpar();

                    btnAdd.setEnabled(true);
                    txtPesquisarCli.setEnabled(true);
                    tblClientes.setVisible(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void ExcluirOrdemServico() {
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem Certeza que deseja apagar está Ordem de Serviço?", "Aviso", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            String sql = "delete from tbordemservico where os = ? ";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOs.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço excluída com sucesso!");
                    txtOs.setText(" ");
                    txtData.setText(" ");
                    limpar();

                    btnAdd.setEnabled(true);
                    txtPesquisarCli.setEnabled(true);
                    tblClientes.setVisible(true);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    private void colocarCampos() {
        int colocar = tblClientes.getSelectedRow();
        txtCliid.setText(tblClientes.getModel().getValueAt(colocar, 0).toString());
    }

    private void EmitirOrdemServico() {
        String sql = "insert into tbordemservico (tipo, situacao, mercadoria,problema, servico, tecnico, valor, idcliente) values (?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, tipo);
            pst.setString(2, cboOs.getSelectedItem().toString());
            pst.setString(3, txtEquip.getText());
            pst.setString(4, txtProblema.getText());
            pst.setString(5, txtServ.getText());
            pst.setString(6, txtTec.getText());
            pst.setString(7, txtValor.getText().replace(",", "."));
            pst.setString(8, txtCliid.getText());

            if ((txtCliid.getText().isEmpty()) || (txtEquip.getText().isEmpty()) || (txtProblema.getText().isEmpty() || cboOs.getSelectedItem().equals(" "))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço Criada com Sucesso!");
                    recuperarOS();
                    btnAdd.setEnabled(false);
                    btnSearch.setEnabled(false);
                    btnPrint.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void imprimir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a Impressão desta Ordem de servíço?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                HashMap filtroImpressao = new HashMap();
                filtroImpressao.put("os", Integer.parseInt(txtOs.getText()));
                JasperPrint imprimirRelSer = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/Imprimir.jasper"), filtroImpressao, conexao);


                JasperViewer.viewReport(imprimirRelSer, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao Imprimir a Ordem de Servíço");
            }

        }
    }

    private void recuperarOS() {
        String sql = "select max(os) from tbordemservico";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOs.setText(rs.getString(1));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Relatório de Ordem de Servíço não encontrado");
        }

    }

    /**
     * Método Responsável por limpar os campos na tela
     */
    private void limpar() {
        txtEquip.setText("");
        txtProblema.setText("");
        txtServ.setText("");
        txtTec.setText("");
        txtValor.setText("");
        txtCliid.setText("");
        txtPesquisarCli.setText("");
        ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
        cboOs.setSelectedItem(" ");

        btnEdit.setEnabled(false);
        btnDel.setEnabled(false);
        btnPrint.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtOrc = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOs = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtPesquisarCli = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliid = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEquip = new javax.swing.JTextField();
        txtProblema = new javax.swing.JTextField();
        txtServ = new javax.swing.JTextField();
        txtTec = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(640, 480));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtOs.setEditable(false);

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        buttonGroup1.add(rbtOrc);
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtOrc))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rbtOs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(txtData))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrc)
                    .addComponent(rbtOs))
                .addGap(17, 17, 17))
        );

        jLabel3.setText("Estado/ Situação ");

        cboOs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Orçamento Negado", "Esperando Por Peças", "Orçamento Aprovado", "Abandonado", "Esperando por Reparação", "..." }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        txtPesquisarCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarCliKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Search_1.png"))); // NOI18N

        jLabel5.setText("* Id");

        txtCliid.setEditable(false);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Email"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtPesquisarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliid, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 110, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txtCliid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("* Equipamento");

        jLabel7.setText("* Problema");

        jLabel8.setText("Serviço");

        jLabel9.setText("Técnico");

        jLabel10.setText("Valor Total");

        txtValor.setText("0");

        btnAdd.setText("Adicionar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSearch.setText("Consultar");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnEdit.setText("Editar");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDel.setText("Eliminar");
        btnDel.setEnabled(false);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnPrint.setText("Imprimir");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboOs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(38, 38, 38)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnDel)
                                .addGap(18, 18, 18)
                                .addComponent(btnPrint))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEquip)
                                .addComponent(txtProblema)
                                .addComponent(txtServ)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtTec, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtValor, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtProblema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnSearch)
                    .addComponent(btnEdit)
                    .addComponent(btnDel)
                    .addComponent(btnPrint))
                .addGap(51, 51, 51))
        );

        setBounds(0, 0, 781, 531);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarCliKeyReleased

        pesquisarCliente();
    }//GEN-LAST:event_txtPesquisarCliKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked

        colocarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed

        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed

        tipo = "Ordem de Servíço";

    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

        rbtOrc.setSelected(true);
        tipo = "Orçamento";

    }//GEN-LAST:event_formInternalFrameOpened

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        EmitirOrdemServico();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        PesquisarOrdemServico();

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

        alterarOrdemServico();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed

        ExcluirOrdemServico();

    }//GEN-LAST:event_btnDelActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        imprimir();
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliid;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtEquip;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtPesquisarCli;
    private javax.swing.JTextField txtProblema;
    private javax.swing.JTextField txtServ;
    private javax.swing.JTextField txtTec;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
