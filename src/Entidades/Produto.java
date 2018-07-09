package Entidades;

import DAO.ConexaoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Gustavo
 */
public class Produto {
    private int ID_Produto;
    private String NomeProduto;
    private Date DataAquisicao;
    private String Descricao;
    private double Quantidade;
    private String Unidade;
    private double Custo;
    private double Preco;
    
    public void BuscarProduto(int ID){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlBusca = con.prepareStatement("SELECT * FROM Produtos WHERE ID_Produto = ?"); 
            sqlBusca.setInt(1, ID);
            try (ResultSet rsSelect = sqlBusca.executeQuery()) {
                setID_Produto(rsSelect.getInt("ID_Produto"));
                setNomeProduto(rsSelect.getString("NomeProduto"));
                setDataAquisicao(rsSelect.getDate("DataAquisicao"));
                setDescricao(rsSelect.getString("Descricao"));
                setQuantidade(rsSelect.getDouble("Quantidade"));
                setUnidade(rsSelect.getString("Unidade"));
                setCusto(rsSelect.getDouble("Custo"));
                setPreco(rsSelect.getDouble("Preco"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage()
                        ,"Erro inesperado - Comunique a Gládio - " + ex.getMessage() 
                        ,JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro inesperado - Comunique a Gládio"
                              , JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public void InserirProduto(){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlInsert = con.prepareStatement(" insert into Produtos  ("
                    + " NomeProduto, DataAquisicao, Descricao, Quantidade, Unidade, Custo, Preco)"
                    + " values (?, ?, ?, ?, ?, ?, ?) ");
            //-Produtos (ID_Produto, NomeProduto, DataAquisição, Descrição, Quantidade, Unidade, Custo, Preco)
            sqlInsert.setString(1, this.getNomeProduto());
            sqlInsert.setDate(2, (java.sql.Date) this.getDataAquisicao());
            sqlInsert.setString(3, this.getDescricao());
            sqlInsert.setDouble(4, this.getQuantidade());
            sqlInsert.setString(5, this.getUnidade());
            sqlInsert.setDouble(6, this.getCusto());
            sqlInsert.setDouble(7, this.getPreco());
            
            int executeUpdate = sqlInsert.executeUpdate();
            JOptionPane.showMessageDialog( null , "Produto inserido." , "Inserir" , JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()
                        ,"Erro inesperado ao tentar inserir produto - Comunique a Gládio - " + ex.getMessage() 
                        ,JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void EditarProduto(){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlEdit = con.prepareStatement(" update Produtos  ( "
                    + " set NomeProduto = ? "
                    + "    ,DataAquisicao = ? "
                    + "    ,Descricao = ? "
                    + "    ,Quantidade = ? "
                    + "    ,Unidade = ? "
                    + "    ,Custo = ? "
                    + "    ,Preco = ? )");
            //-Produtos (ID_Produto, NomeProduto, DataAquisição, Descrição, Quantidade, Unidade, Custo, Preco)
            sqlEdit.setString(1, this.getNomeProduto());
            sqlEdit.setDate(2, (java.sql.Date) this.getDataAquisicao());
            sqlEdit.setString(3, this.getDescricao());
            sqlEdit.setDouble(4, this.getQuantidade());
            sqlEdit.setString(5, this.getUnidade());
            sqlEdit.setDouble(6, this.getCusto());
            sqlEdit.setDouble(7, this.getPreco());
            
            int iConfirm = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja editar esse produto? "
                    ,"Editar",JOptionPane.YES_NO_CANCEL_OPTION);
            if (iConfirm == JOptionPane.YES_OPTION) {
                int executeUpdate = sqlEdit.executeUpdate();
                JOptionPane.showMessageDialog( null , "Edição concluida." , "Edição" , JOptionPane.INFORMATION_MESSAGE);                 
            }else {
                sqlEdit.close();
                JOptionPane.showMessageDialog( null , "Edição cancelada." , "Cancelar" , JOptionPane.WARNING_MESSAGE);                 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()
                        ,"Erro inesperado ao tentar editar produto - Comunique a Gládio - " + ex.getMessage() 
                        ,JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public int getID_Produto() {
        return ID_Produto;
    }

    public void setID_Produto(int ID_Produto) {
        this.ID_Produto = ID_Produto;
    }

    public String getNomeProduto() {
        return NomeProduto;
    }

    public void setNomeProduto(String NomeProduto) {
        this.NomeProduto = NomeProduto;
    }

    public Date getDataAquisicao() {
        return DataAquisicao;
    }

    public void setDataAquisicao(Date DataAquisicao) {
        this.DataAquisicao = DataAquisicao;
    }

    public double getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(double Quantidade) {
        this.Quantidade = Quantidade;
    }

    public String getUnidade() {
        return Unidade;
    }

    public void setUnidade(String Unidade) {
        this.Unidade = Unidade;
    }

    public double getCusto() {
        return Custo;
    }

    public void setCusto(double Custo) {
        this.Custo = Custo;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double Preco) {
        this.Preco = Preco;
    }
    
    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
}
