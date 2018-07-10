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
//    public void inserirTipoMovimento(String nomeMovimento, String debCred) throws SQLException {;
//        Connection con = new ConexaoDAO().conectar();
//        PreparedStatement SQL = con.prepareStatement("insert into TiposMovimentos values(?,?,?)");
//        SQL.setInt(1, 0);
//        SQL.setString(2, nomeMovimento);
//        SQL.setString(3, debCred);
//        SQL.executeUpdate();
//    }
    // Teste
    public ResultSet inserirMovimentoEstoqueEObterId(String nomeMovimento, String dataMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into MovimentosEstoque (ID_Movimento, DataMovimento, ID_TipoMovimento)"
                + " values(?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?)");
        SQL.setInt(1, obterIdMovimentoEstoque());
        SQL.setString(2, dataMovimento);
        SQL.setInt(3, obterIdTipoMovimento(nomeMovimento));
        SQL.executeUpdate();
        
        SQL = con.prepareStatement("select max(ID_Movimento) as ID from MovimentosEstoque");
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    // Teste
    
    public void inserirMovimentoEstoque(String nomeMovimento, String dataMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into MovimentosEstoque (ID_Movimento, DataMovimento, ID_TipoMovimento)"
                + " values(?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?)");
        SQL.setInt(1, obterIdMovimentoEstoque());
        SQL.setString(2, dataMovimento);
        SQL.setInt(3, obterIdTipoMovimento(nomeMovimento));
        SQL.executeUpdate();
    }
    
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
    private int obterIdMovimentoEstoque() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select max(ID_Movimento) + 1 as ID from MovimentosEstoque");     
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return -1;
        }
    }
    
    private int obterIdTipoMovimento(String nomeMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select ID_TipoMovimento as ID from tiposmovimentos where NomeMovimento = ?");  
        SQL.setString(1, nomeMovimento);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return -1;
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
