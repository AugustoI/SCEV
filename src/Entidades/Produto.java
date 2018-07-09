package Entidades;

import java.util.Date;
/**
 *
 * @author Gustavo
 */
public class Produto {
    //-Produtos (ID_Produto, NomeProduto, DataAquisição, Descrição, Quantidade, Unidade, Custo, Preco)
    public int ID_Produto;
    public String NomeProduto;
    public Date DataAquisicao;
    public double Quantidade;
    public char Unidade;
    public double Custo;
    public double Preco;

    public int getID_Produto() {
        return ID_Produto;
    }

    public void setID_Produto(int ID_Produto) {
        this.ID_Produto = ID_Produto;
    }

    public String getNomeProduto() {
        return NomeProduto;
    }

    public void setNomeProduto(String NomeProduto) {
        this.NomeProduto = NomeProduto;
    }

    public Date getDataAquisicao() {
        return DataAquisicao;
    }

    public void setDataAquisicao(Date DataAquisicao) {
        this.DataAquisicao = DataAquisicao;
    }

    public double getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(double Quantidade) {
        this.Quantidade = Quantidade;
    }

    public char getUnidade() {
        return Unidade;
    }

    public void setUnidade(char Unidade) {
        this.Unidade = Unidade;
    }

    public double getCusto() {
        return Custo;
    }

    public void setCusto(double Custo) {
        this.Custo = Custo;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double Preco) {
        this.Preco = Preco;
    }
    
}
