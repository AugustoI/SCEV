/*
 * Classe MovimentosDAO
 *
 * Classe utilizada para controle dos movimentos, contendo
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
public class MovimentosDAO {
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE INSERCAO 
    public ResultSet inserirMovimentoEstoqueEObterId(String nomeMovimento, String dataMovimento, int idVariante) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into MovimentosEstoque"
                + "(ID_Movimento, DataMovimento, ID_TipoMovimento, ID_Variante) "
                + "values(?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?,?)");
        SQL.setInt(1, obterIdMovimentoEstoque());
        SQL.setString(2, dataMovimento);
        SQL.setInt(3, obterIdTipoMovimento(nomeMovimento));
        SQL.setInt(4, idVariante);
        SQL.executeUpdate();
        
        SQL = con.prepareStatement("select max(ID_Movimento) as ID from MovimentosEstoque");
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public void inserirMovimentoEstoque(String nomeMovimento, String dataMovimento, int idVariante) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into MovimentosEstoque "
                + "(ID_Movimento, DataMovimento, ID_TipoMovimento, ID_Variante) "
                + "values(?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?,?)");
        SQL.setInt(1, obterIdMovimentoEstoque());
        SQL.setString(2, dataMovimento);
        SQL.setInt(3, obterIdTipoMovimento(nomeMovimento));
        SQL.setInt(4, idVariante);
        SQL.executeUpdate();
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO 
    
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE PESQUISA
    public ResultSet pesquisarMovimentoEstoque() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from MovimentosEstoque");     
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarMovimentoEstoque(int idMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from MovimentosEstoque where ID_Movimento=?");     
        SQL.setInt(1, idMovimento);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarMovimentoEstoquePeloTipoMovimento(int idTipoMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from MovimentosEstoque where ID_TipoMovimento=?");     
        SQL.setInt(1, idTipoMovimento);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarMovimentoEstoquePeloIdVariante(int idVariante) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from MovimentosEstoque where ID_Variante=?");     
        SQL.setInt(1, idVariante);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarMovimentoEstoque(String dataMovimento) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from MovimentosEstoque where ID_TipoMovimento='?'");     
        SQL.setString(1, dataMovimento);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
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
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE PESQUISA
}
