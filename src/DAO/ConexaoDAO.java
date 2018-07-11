/*
 * Classe ConexaoDAO
 *
 * Classe utilizada unicamente para realizar a conexao com
 * o banco de dados da aplicacao..
 *
 * Versao: 1.0.0
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Couth
 */
public class ConexaoDAO {
    private final String URL = "jdbc:mysql://localhost/GLADIOSCEV";
    private final String USUARIO = "root";
    private final String SENHA = "JOAOH123"; 
    
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    } // fim conectar()
}