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
    //-Produtos (ID_Produto, NomeProduto, DataAquisição, Descrição, Quantidade, Unidade, Custo, Preco)
    public int ID_Produto;
    public String NomeProduto;
    public Date DataAquisicao;
    public double Quantidade;
    public char Unidade;
    public double Custo;
    public double Preco;
    
    public void BuscarProduto(int ID){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlBusca = con.prepareStatement("SELECT * FROM Produtos WHERE ID_Produto = ?"); 
            sqlBusca.setInt(1, ID);
            ResultSet rsSelect = sqlBusca.executeQuery();
            //-Produtos (ID_Produto, NomeProduto, DataAquisição, Descrição, Quantidade, Unidade, Custo, Preco)
            setID_Produto(rsSelect.getInt("ID_Produto"));
            setNomeProduto(rsSelect.getString("NomeProduto"));
            //setDataAquisicao(rsSelect.sss);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro inesperado - Comunique a Gládio"
                              , JOptionPane.ERROR_MESSAGE);
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

    public char getUnidade() {
        return Unidade;
    }

    public void setUnidade(char Unidade) {
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
    
}
