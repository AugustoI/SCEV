/*
 * Classe VendasDAO
 *
 * Classe utilizada para controle das vendas, contendo
 * metodos utilitarios referentes ao banco de dados.
 *
 * Versao: 1.0.0
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Couth
 */
public class VendasDAO {
    // -------------------------------------------------------------------------------------------------------- INICIO METODOS DE INSERCAO|PESQUISA PELO ID_PRODUTO
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE INSERCAO    
    public void inserirProdutoMovimento(int idProduto, int idMovimento, int quantidade) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into ProdutosMovimentos (ID_ProdutoMovimento, ID_Movimento, ID_Produto, Quantidade)"
                + " values(?,?,?,?)");       
        SQL.setInt(1, 0);   
        SQL.setInt(2, idMovimento);
        SQL.setInt(3, idProduto);
        SQL.setInt(4, quantidade);
        SQL.executeUpdate();
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO
    
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE PESQUISA
    public ResultSet pesquisarProdutoMovimento() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from ProdutosMovimentos");     
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarProdutoMovimento(int idProdutoMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from ProdutosMovimentos where ID_ProdutoMovimento=?");     
        SQL.setInt(1, idProdutoMovimento);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarProdutoMovimentoPeloIdMovimento(int idMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from ProdutosMovimentos where ID_Movimento=?");     
        SQL.setInt(1, idMovimento);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarProdutoMovimentoPeloIdProduto(int idProduto) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from ProdutosMovimentos where ID_Produto=?");     
        SQL.setInt(1, idProduto);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarProdutoMovimentoPelaQuantidade(int quantidade) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from ProdutosMovimentos where Quantidade=?");     
        SQL.setInt(1, quantidade);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public int obterQuantidadeProdutosDisponiveis(int idProduto) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select Quantidade from produtos where ID_PRODUTO=?");        
        SQL.setInt(1, idProduto);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return -1;
        }
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE PESQUISA
    // -------------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO|PESQUISA PELO ID_PRODUTO
}
