/*
 * Classe Vendas
 *
 * Classe utilizada para controle das vendas, contendo
 * metodos utilitarios como set|get, alem de tratamento de erros.
 *
 * Versao: 1.0.0
 */
package Entidades;

import Interface.GladioError;

/**
 *
 * @author Couth
 */
public class Venda implements GladioError {
    
    // ------------------------------------------------------------------------------------------------- INICIO INTERFACE GLADIOERROR
    /*
     * CODIGOS DE ERRO
     * 0 -> NAO HA ERRO.
     * 1 ->
     */
    private int codError = 0;
    
    public void setCodError(int codError) {
        this.codError = codError;
    }
    
    public int getCodError() {
        return codError;
    }
    
    public boolean hasError() {
        boolean r = true;
        if (getCodError() == 0) {
            r = false;
        }
        return r;
    }
    
    public String msgError() {
        String msg = msgErrorByCod(getCodError());
        return msg;
    }
    
    public String msgErrorByCod(int codError) {        
        String msg;
        switch (codError) {
            case 0:
                msg = "";
                break;
            default:
                msg = "";
                break;
        }
        return msg;
    }
    // ------------------------------------------------------------------------------------------------- FIM INTERFACE GLADIOERROR
    
    // ------------------------------------------------------------------------------------------------- INICIO CLASSE VENDA
    private String Nome_Produto, Nome_Movimento, Data_Aquisicao, Data_Venda, Descricao, Unidade;
    private char DebitoCredito;
    private int ID_Produto, ID_Movimento, ID_TipoMovimento, Quantidade;
    private float Preco_Venda;

    /*
     * Metodo para venda de um novo produto atraves de seu codigo.
     */
    public void novaVenda(int idProduto, String nomeMovimento, String dataVenda, 
                          String unidade, int quantidade, char debCred, float preco) {
        // Testar se os parametros sao validos.
        if (idProduto < 0 || nomeMovimento == null || dataVenda == null ||
            unidade == null || quantidade <= 0 || eDebCred(debCred) || preco < 0.0) {
            // Decidir se vou testar um por um e setar seu erro ou se vou apenas dizer que
            // ha erro na venda.
        } else {
            // caso os parametros estejam corretos:
            // cod here
        }
    }
    
    /*
     * Metodo para venda de um novo produto atraves de seu nome.
     */
    public void novaVenda(String nomeProduto, String nomeMovimento, String dataVenda, 
                          String unidade, int quantidade, char debCred, float preco) {
        // Testar se os parametros sao validos.
        if (nomeProduto == null || nomeMovimento == null || dataVenda == null ||
            unidade == null || quantidade <= 0 || eDebCred(debCred) || preco < 0.0) {
            // Decidir se vou testar um por um e setar seu erro ou se vou apenas dizer que
            // ha erro na venda.
        } else {
            // caso os parametros estejam corretos:
            // cod here
        }
    }
    
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA
    /*
     * Metodo para conferir se o debito|credito e valido.
     */
    public boolean eDebCred(char debCred) {
        boolean r = false;
        if (debCred == 'C' || debCred == 'c' ||
            debCred == 'D' || debCred == 'd') {
            r = true;
        }
        return r;
    }
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA
    
    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE VENDA
    public String getNome_Produto() {
        return Nome_Produto;
    }

    public void setNome_Produto(String Nome_Produto) {
        this.Nome_Produto = Nome_Produto;
    }

    public String getNome_Movimento() {
        return Nome_Movimento;
    }

    public void setNome_Movimento(String Nome_Movimento) {
        this.Nome_Movimento = Nome_Movimento;
    }

    public String getData_Aquisicao() {
        return Data_Aquisicao;
    }

    public void setData_Aquisicao(String Data_Aquisicao) {
        this.Data_Aquisicao = Data_Aquisicao;
    }

    public String getData_Venda() {
        return Data_Venda;
    }

    public void setData_Venda(String Data_Venda) {
        this.Data_Venda = Data_Venda;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public String getUnidade() {
        return Unidade;
    }

    public void setUnidade(String Unidade) {
        this.Unidade = Unidade;
    }

    public char getDebitoCredito() {
        return DebitoCredito;
    }

    public void setDebitoCredito(char DebitoCredito) {
        this.DebitoCredito = DebitoCredito;
    }

    public int getID_Produto() {
        return ID_Produto;
    }

    public void setID_Produto(int ID_Produto) {
        this.ID_Produto = ID_Produto;
    }

    public int getID_Movimento() {
        return ID_Movimento;
    }

    public void setID_Movimento(int ID_Movimento) {
        this.ID_Movimento = ID_Movimento;
    }

    public int getID_TipoMovimento() {
        return ID_TipoMovimento;
    }

    public void setID_TipoMovimento(int ID_TipoMovimento) {
        this.ID_TipoMovimento = ID_TipoMovimento;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int Quantidade) {
        this.Quantidade = Quantidade;
    }

    public float getPreco_Venda() {
        return Preco_Venda;
    }

    public void setPreco_Venda(float Preco_Venda) {
        this.Preco_Venda = Preco_Venda;
    }    
    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE VENDA
    // ------------------------------------------------------------------------------------------------- FIM CLASSE VENDA    
}
