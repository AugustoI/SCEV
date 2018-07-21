/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import DAO.MovimentosDAO;

/**
 *
 * @author ACER
 */
public class Entradas {
   private int ID_Movimento;
   private int ID_Produto;
   private double Quantidade;

    public int getID_Movimento() {
        return ID_Movimento;
    }

    public void setID_Movimento(int ID_Movimento) {
        this.ID_Movimento = ID_Movimento;
    }

    public int getID_Produto() {
        return ID_Produto;
    }

    public void setID_Produto(int ID_Produto) {
        this.ID_Produto = ID_Produto;
    }

    public double getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(double Quantidade) {
        this.Quantidade = Quantidade;
    }
    
    public void inserirEntrada() {
        MovimentosDAO mov = new MovimentosDAO();
        Movimentos mov2 = new Movimentos();
        
    }
}
