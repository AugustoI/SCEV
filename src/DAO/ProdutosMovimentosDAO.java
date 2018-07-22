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
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class ProdutosMovimentosDAO implements ClasseDAO{
    private int ID_ProdutoMovimento;
    private int ID_Movimento;
    private int ID_Produto;
    private double Quantidade;

    public ResultSet rsDados;
    public String MensagemErro;
    
    @Override
    public boolean Inserir() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("insert into produtosmovimentos (ID_Movimento,ID_Produto,Quantidade)\n" +
                                                         "values(?,?,?)");
            SQL.setInt(1, this.getID_Movimento());
            SQL.setInt(2, this.getID_Produto());
            SQL.setDouble(3, this.getQuantidade());
                     
            try {
                SQL.executeUpdate();
                getUltimoID_ProdutoMovimento();
                SQL = con.prepareStatement("select * from produtosmovimentos where ID_ProdutoMovimento = ?");
                SQL.setInt(1, this.getID_ProdutoMovimento());
                this.rsDados = SQL.executeQuery();
                SetProdutoMovimento();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = e.getMessage();
            }
            
            if (!Executou){
                MensagemErro = "Não foi possível inserir: " + MensagemErro;
            }
        } catch (SQLException ex) {
            MensagemErro = "Não foi possível inserir: " + ex.getMessage();
        }
        return Executou;
    }
    
    public boolean Inserir(ResultSet rsInserir) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        String sqlInsert;
        try {
            con = new ConexaoDAO().conectar();
            sqlInsert = "insert into produtosmovimentos (ID_Movimento,ID_Produto,Quantidade) values "; 
            
            while (!rsDados.isAfterLast()) {
                rsDados.next();
                sqlInsert = sqlInsert + "("+rsInserir.getString(1)+","+rsInserir.getString(2)+","+rsInserir.getString(3)+") ";                
            }
            
            PreparedStatement SQL = con.prepareStatement(sqlInsert);
                     
            try {
                SQL.executeUpdate();
                Limpar();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = e.getMessage();
            }
            
            if (!Executou){
                MensagemErro = "Não foi possível inserir: " + MensagemErro;
            }
        } catch (SQLException ex) {
            MensagemErro = "Não foi possível inserir: " + ex.getMessage();
        }
        return Executou;
    }

    @Override
    public boolean Editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean Deletar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean Pesquisar() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from produtosmovimentos where ID_Movimento = ?");   
            SQL.setInt(1, this.getID_Movimento());
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
                SetProdutoMovimento();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }

    
    public boolean Pesquisar(String Produto) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from produtosmovimentos where ID_Produto = ?");   
            SQL.setInt(1, this.getID_Produto());
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
                SetProdutoMovimento();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
    private void SetProdutoMovimento () {
        try {
            this.setID_Movimento(rsDados.getInt("ID_Movimento"));
            this.setID_Produto(rsDados.getInt("ID_Produto"));
            this.setID_ProdutoMovimento(rsDados.getInt("ID_ProdutoMovimento"));
            this.setQuantidade(rsDados.getDouble("Quantidade"));
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Erro inesperdo", "Comunique a Gládio Softwares.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void getUltimoID_ProdutoMovimento(){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select ID_ProdutoMovimento from produtosmovimentos \n" +
                                    " where ID_ProdutoMovimento in (select max(ID_ProdutoMovimento) from produtosmovimentos) ");
            ResultSet rsBusca = SQL.executeQuery();
            this.setID_ProdutoMovimento(rsBusca.getInt(1));
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Erro inesperdo", "Comunique a Gládio Softwares.", JOptionPane.ERROR_MESSAGE);
        }            
    }
    
    @Override
    public void Limpar() {
        this.setID_Movimento(0);
        this.setID_Produto(0);
        this.setID_ProdutoMovimento(0);
        this.setQuantidade(0);
    }

    public int getID_ProdutoMovimento() {
        return ID_ProdutoMovimento;
    }

    public void setID_ProdutoMovimento(int ID_ProdutoMovimento) {
        this.ID_ProdutoMovimento = ID_ProdutoMovimento;
    }

    public int getID_Movimento() {
        return ID_Movimento;
    }

    public void setID_Movimento(int ID_Movimento) {
        this.ID_Movimento = ID_Movimento;
    }

    public int getID_Produto() {
        return ID_Produto;
    }

    public void setID_Produto(int ID_Produto) {
        this.ID_Produto = ID_Produto;
    }

    public double getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(double Quantidade) {
        this.Quantidade = Quantidade;
    }
}
