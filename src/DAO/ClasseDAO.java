/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author ACER
 */
public interface ClasseDAO {
    public boolean Inserir();
    public boolean Editar();
    public boolean Deletar();
    public boolean Pesquisar();
    public void Limpar();
}
