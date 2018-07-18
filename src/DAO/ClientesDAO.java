/*
 * Classe ClientesDAO
 *
 * Classe utilizada para controle dos clientes, contendo
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
public class ClientesDAO {
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE INSERCAO 
    public ResultSet inserirClienteEObterId(String nome, String cpf, String telCelular, String telFixo, String cep,
                               String rua, String complemento, String bairro, String dataCadastro,
                               char tipo) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into variantesestoque  "
                + "(ID_Variante, Nome, CPF, TelefoneCelular, TelefoneFixo, CEP, Rua, Complemento, Bairro, "
                + "DataCadastro, Tipo) "
                + "values(?,?,?,?,?,?,?,?,?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?)");
        SQL.setInt(1, 0);
        SQL.setString(2, nome);
        SQL.setString(3, cpf);
        SQL.setString(4, telCelular);
        SQL.setString(5, telFixo);
        SQL.setString(6, cep);
        SQL.setString(7, rua);
        SQL.setString(8, complemento);
        SQL.setString(9, bairro);
        SQL.setString(10, dataCadastro);
        SQL.setString(11, ""+tipo);
        SQL.executeUpdate();
        
        SQL = con.prepareStatement("select max(ID_Variante) as ID from variantesestoque");
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public void inserirCliente(String nome, String cpf, String telCelular, String telFixo, String cep,
                               String rua, String complemento, String bairro, String dataCadastro, char tipo) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("insert into variantesestoque"
                + "(ID_Variante, Nome, CPF, TelefoneCelular, TelefoneFixo, CEP, Rua, Complemento, Bairro,"
                + " DataCadastro, Tipo) "
                + " values(?,?,?,?,?,?,?,?,?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?)");
        SQL.setInt(1, 0);
        SQL.setString(2, nome);
        SQL.setString(3, cpf);
        SQL.setString(4, telCelular);
        SQL.setString(5, telFixo);
        SQL.setString(6, cep);
        SQL.setString(7, rua);
        SQL.setString(8, complemento);
        SQL.setString(9, bairro);
        SQL.setString(10, dataCadastro);
        SQL.setString(11, ""+tipo);
        SQL.executeUpdate();
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE INSERCAO 
    
    // ------------------------------------------------------------------------------------------------- INICIO METODOS DE PESQUISA
    public ResultSet pesquisarClientes() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where tipo like '?%'");   
        SQL.setString(1, "C");
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarCliente(int idVariante) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where ID_Variante=?");     
        SQL.setInt(1, idVariante);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarCliente(String nome) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where Nome like '?%'");     
        SQL.setString(1, nome);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarClienteCPF(String cpf) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where CPF=?");     
        SQL.setString(1, cpf);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    public ResultSet pesquisarClienteDataCadastro(String dataCadastro) throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where DataCadastro='?'");     
        SQL.setString(1, dataCadastro);
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    // ------------------------------------------------------------------------------------------------- FIM METODOS DE PESQUISA
}
