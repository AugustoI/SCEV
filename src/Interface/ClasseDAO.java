/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

/**
 *
 * @author ACER
 */
public interface ClasseDAO {
    
    /*
     * Esses metodos sao requeridos em todas as classes de conexao com o banco de dados.
     */
    public boolean inserir() throws Exception;
    public boolean editar() throws Exception;
    public boolean deletar() throws Exception;
    public boolean pesquisar() throws Exception;
    
    /*
     * Esse metodo limpa os atributos da classe
     */
    public void limpar();
    
}
