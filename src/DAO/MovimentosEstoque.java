/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Interface.ClasseDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class MovimentosEstoque implements ClasseDAO{

    private int ID_Movimento;
    private Date DataMovimento;
    private int ID_TipoMovimento;
    private int ID_Variante;
    
    public String MensagemErro;
    public ResultSet rsDados;
    
    private int obterProximoIdMovimentoEstoque() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select max(ID_Movimento) + 1 as ID from MovimentosEstoque");     
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return -1;
        }
    }
    
    @Override
    public boolean Inserir() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("insert into MovimentosEstoque"
                + "(ID_Movimento, DataMovimento, ID_TipoMovimento, ID_Variante) "
                + "values(?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?,?)");
            this.setID_Movimento(obterProximoIdMovimentoEstoque());
            SQL.setInt(1, this.getID_Movimento());
            SQL.setDate(2, (java.sql.Date) this.getDataMovimento());
            SQL.setInt(3, this.getID_TipoMovimento());
            SQL.setInt(4, this.getID_Variante());
                     
            try {
                SQL.executeUpdate();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = e.getMessage();
            }
            
            if (!Executou){
                MensagemErro = "Não foi possível inserir: " + MensagemErro;
            }

            SQL = con.prepareStatement("select * from MovimentosEstoque where ID_Movimento = ? ");
            SQL.setInt(1, this.getID_Movimento());
            this.rsDados = SQL.executeQuery();
            SetMovimento();
        } catch (SQLException ex) {
            MensagemErro = "Não foi possível inserir: " + ex.getMessage();
        }            
        return Executou;
    }

    @Override
    public boolean Editar() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("	UPDATE movimentosestoque\n" +
                                                         "		SET DataMovimento = ? \n" +
                                                         "		   ,ID_Variante = ? \n" +
                                                         "	WHERE ID_Movimento = ? ");

            SQL.setDate(1, (java.sql.Date) this.getDataMovimento());
            SQL.setInt(2, this.getID_TipoMovimento());
            SQL.setInt(3, this.getID_Movimento());
            
            try {
                SQL.executeUpdate();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = e.getMessage();
            }
            
            if (!Executou){
                MensagemErro = "Não foi possível editar: " + MensagemErro;
            }

            SQL = con.prepareStatement("select * from movimentosestoque where ID_Movimento = ?");
            SQL.setInt(1, this.getID_Movimento());
            this.rsDados = SQL.executeQuery();
            SetMovimento();            
        } catch (SQLException ex) {
            MensagemErro = "Não foi possível editar: " + ex.getMessage();
        }            
        return Executou;
    }

    @Override
    public boolean Deletar() {
        JOptionPane.showMessageDialog(null, "Atenção", "Permitir a exclusão do movimento pode resultar em erros!", JOptionPane.INFORMATION_MESSAGE);
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("delete from movimentosestoque where ID_Movimento = ?");   
            SQL.setInt(1, this.getID_Movimento());
            
            try {
                SQL.executeUpdate();
                rsDados = null;
                Executou = true;
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a exclusão dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a exclusão dos dados: " + ex.getMessage();
        }
        
        Limpar();
        return Executou;
    }

    @Override
    public boolean Pesquisar() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from movimentosestoque where ID_Movimento = ?");
            SQL.setInt(1, this.getID_Variante());
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
                SetMovimento();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
    /**
     *
     * @param Tipo
     * @return
     */
    
    public boolean Pesquisar(int Variante) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from movimentosestoque where ID_Variante = ?");
            SQL.setInt(1, this.getID_Variante());
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
    public boolean Pesquisar(Date DataInicial, Date DataFinal) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from movimentosestoque where DataMovimento between ? and ? ");   
            SQL.setDate(1, (java.sql.Date) DataInicial);
            SQL.setDate(2, (java.sql.Date) DataFinal);
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }

    private void SetMovimento () {
        try {
            this.setID_Variante(rsDados.getInt("ID_Variante"));
            this.setDataMovimento(rsDados.getDate("DataMovimento"));
            this.setID_Movimento(rsDados.getInt("ID_Movimento"));
            this.setID_TipoMovimento(rsDados.getInt("ID_TipoMovimento"));
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Erro inesperdo", "Comunique a Gládio Softwares.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     *
     */
    @Override
    public void Limpar () {
        this.setID_Variante(0);
        this.setDataMovimento(null);
        this.setID_Movimento(0);
        this.setID_TipoMovimento(0);
    }

    public int getID_Movimento() {
        return ID_Movimento;
    }

    public void setID_Movimento(int ID_Movimento) {
        this.ID_Movimento = ID_Movimento;
    }

    public Date getDataMovimento() {
        return DataMovimento;
    }

    public void setDataMovimento(Date DataMovimento) {
        this.DataMovimento = DataMovimento;
    }

    public int getID_TipoMovimento() {
        return ID_TipoMovimento;
    }

    public void setID_TipoMovimento(int ID_TipoMovimento) {
        this.ID_TipoMovimento = ID_TipoMovimento;
    }

    public int getID_Variante() {
        return ID_Variante;
    }

    public void setID_Variante(int ID_Variante) {
        this.ID_Variante = ID_Variante;
    }
    
}
