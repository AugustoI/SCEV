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
        PreparedStatement SQL = con.prepareStatement("insert into TiposMovimentos values(?,?,?)");
        SQL.setInt(1, 0);
        SQL.setString(2, nomeMovimento);
        SQL.setString(3, debCred);
        SQL.executeUpdate();
    }
    
    public void inserirMovimentoEstoque(String dataMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into MovimentosEstoque (ID_Movimento, DataMovimento, ID_TipoMovimento)"
                + " values(?,?,?)");
        SQL.setInt(1, 0);
        SQL.setInt(2, obterIdMovimento());
        SQL.setString(3, dataMovimento);
        SQL.executeUpdate();
    }
    
    public void inserirProdutoMovimento(int idProduto, int quantidade) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into ProdutosMovimento (ID_Movimento, ID_Produto, Quantidade)"
                + " values(?,?,?)");        
        SQL.setInt(1, obterIdMovimento());
        SQL.setInt(2, idProduto);
        SQL.setInt(3, quantidade);
        SQL.executeUpdate();
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO
    
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE PESQUISA
    private int obterIdMovimento() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select max(ID_Movimento) + 1 as ID from MovimentosEstoque");     
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return Integer.parseInt(rs.toString());
        } else {
            return -1;
        }
    }
    
    public int obterQuantidadeProdutosDisponiveis(int idProduto) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select Quantidade from Produtos where ID_PRODUTO=?");        
        SQL.setInt(1, idProduto);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return Integer.parseInt(rs.toString());
        } else {
            return -1;
        }
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE PESQUISA
    // -------------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO|PESQUISA PELO ID_PRODUTO
}
