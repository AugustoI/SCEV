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
    *Esses sãos os métodos báscios que toda classe de conexão com o banco deveria ter
    */
    public boolean Inserir();
    public boolean Editar();
    public boolean Deletar();
    public boolean Pesquisar();
    /*
    *Esse método limpa os atributos da classe
    */
    public void Limpar();
    
}
