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
    public void inserirTipoMovimento(String nomeMovimento, String debCred) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into tipoMovimentos values(0,?,?)"); 
        SQL.setString(1, nomeMovimento);
        SQL.setString(2, debCred);
        SQL.executeUpdate();
    }
    
    public void inserirMovimentoEstoque(int idTipoMovimento, String dataMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into movimentosEstoque values(0,?,?)");
        SQL.setInt(1, idTipoMovimento);
        SQL.setString(2, dataMovimento);
        SQL.executeUpdate();
    }
    
    public void inserirProdutoMovimento(int idMovimento, int idProduto, int quantidade) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into produtosMovimento values(0,?,?,?)");        
        SQL.setInt(1, idMovimento);
        SQL.setInt(2, idProduto);
        SQL.setInt(3, quantidade);
        SQL.executeUpdate();
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO
    
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE PESQUISA
    public ResultSet obterIdTipoMovimento(int idProduto) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select idTipoMovimento from movimentosEstoque where idProduto=?");        
        SQL.setInt(1, idProduto);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet obterIdMovimento(int idProduto) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select idMovimento from movimentosEstoque where idProduto=?");     
        SQL.setInt(1, idProduto);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet obterQuantidadeProdutosDisponiveis(int idProduto) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select quantidade from produtos where idProduto=?");        
        SQL.setInt(1, idProduto);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE PESQUISA
    // -------------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO|PESQUISA PELO ID_PRODUTO
}
