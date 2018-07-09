/*
 * Classe Vendas
 *
 * Classe utilizada para controle das vendas, contendo
 * metodos utilitarios como set|get, alem de tratamento de erros.
 *
 * Versao: 1.0.0
 */
package Entidades;

import DAO.VendasDAO;
import Interface.GladioError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Couth
 */
public class Venda implements GladioError {
    
    public static void main(String[] args) {
        Venda v = new Venda();
        String a = v.novaVenda(1, "VENDA", "23/02/2018 00:14:15", "PRODUTO", "U", 'C', 2, 10.90f);
        System.out.println(a);
    }
    
    // ------------------------------------------------------------------------------------------------- INICIO INTERFACE GLADIOERROR
    /*
     * CODIGOS DE ERRO
     * 00 -> NAO HA ERRO.
     * 01 -> ERRO NO ID DO PRODUTO.
     * 02 -> ERRO NO ID DO MOVIMENTO.
     * 03 -> ERRO NO ID DO TIPO DE MOVIMENTO. 
     * 04 -> ERRO NO NOME DO PRODUTO.
     * 05 -> ERRO NO NOME DO MOVIMENTO.
     * 06 -> ERRO NA DATA DA VENDA.
     * 07 -> ERRO NA DESCRICAO DO PRODUTO.
     * 08 -> ERRO NA UNIDADE DO PRODUTO.
     * 09 -> ERRO NO DEBITO|CREDITO.
     * 10 -> ERRO NA QUANTIDADE DE PRODUTOS.
     * 11 -> ERRO NO PRECO DO PRODUTO.
     * 12 -> PARAMETRO 'confereParametrosNovaVenda' INVALIDO.
     * 13 -> QUANTIDADE DE COMPRA MAIOR QUE A QUANTIDADE DISPONIVEL NO ESTOQUE.
     * 13 -> ERRO NA PESQUISA DE QUANTIDADE DISPONIVEL NO ESTOQUE.
     * 15 -> ERRO SQLEXCEPTION EM VENDAS.
     */
    private int codError = 0;
    
    @Override
    public void setCodError(int codError) {
        this.codError = codError;
    }
    
    @Override
    public int getCodError() {
        return codError;
    }
    
    /**
     * Metodo para conferir se ha erro no programa ou nao.
     * 
     * @return retorna true para caso haja erro e
     *                 false para caso contrario.
     */
    @Override
    public boolean hasError() {
        boolean r = true;
        if (getCodError() == 0) {
            r = false;
        }
        return r;
    }
    
    /**
     * Metodo para obter a mensagem do erro atual.
     * 
     * @return retorna a mensagem do erro atual.
     */
    @Override
    public String msgError() {
        String msg = msgErrorByCod(getCodError());
        return msg;
    }
    
    /**
     * Metodo para obter uma mensagem de erro pelo seu codigo.
     * 
     * @param codError - Codigo de erro da respectiva mensagem.
     * 
     * @return retorna a mensagem do respectivo codigo.
     */
    @Override
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
    private String nomeProduto, nomeMovimento, dataVenda, descricao, unidade;
    private char debitoCredito;
    private int idProduto, idMovimento, idTipoMovimento, quantidade;
    private float precoVenda;

    /*
     * Metodo para venda de um novo produto atraves de seu codigo.
     */
    public String novaVenda(int idProduto, String nomeMovimento, String dataVenda, String descricao, 
                          String unidade, char debCred, int quantidade, float preco) {
        // Mensagem a ser retornada
        String msgFinal;
        // setar dados
        setIDProduto(idProduto);
        setNomeMovimento(nomeMovimento);
        setDataVenda(dataVenda);
        setDescricao(descricao);
        setUnidade(unidade);
        setDebitoCredito(debCred);
        setQuantidade(quantidade);
        setPrecoVenda(preco);
        // conferir se estao corretos
        confereParametrosNovaVenda('I');
        // verificar se ha erros
        if (hasError()) {
            msgFinal = msgError();
        } else {
            try {
                VendasDAO vDAO = new VendasDAO();
                // obter quantidade disponivel e conferir se
                // quantidade contem no estoque.
                int quantidadeEstoque = vDAO.obterQuantidadeProdutosDisponiveis(idProduto);
                if (quantidadeEstoque >= 0) {
                    if (quantidade <= quantidadeEstoque) {
                        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        String dataFormatada = formatoData.format(date);
                        //vDAO.inserirTipoMovimento("VENDA", ""+debCred);
                        vDAO.inserirMovimentoEstoque("VENDA", dataFormatada);
                        vDAO.inserirProdutoMovimento(idProduto, quantidade);
                    } else {
                        setCodError(13);
                        throw new Exception(msgError());
                    }
                } else {
                    setCodError(14);
                    throw new Exception(msgError());
                }
                // se der certo
                msgFinal = "Tudo CERTO.";
            } catch (SQLException sqlE) {
                msgFinal = ""+sqlE;
                //msgFinal = msgErrorByCod(15);
            } catch (Exception e) {
                msgFinal = ""+e;
            }
        }
        return msgFinal;
    }
    
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA
    /*
     * Metodo para conferir se o debito|credito e valido.
     *
     * @return retorna true para caso haja erro e
     *                 false para caso contrario.
     */
    public boolean eDebCred(char debCred) {
        boolean r = false;
        if (debCred == 'C' || debCred == 'c' ||
            debCred == 'D' || debCred == 'd') {
            r = true;
        }
        return r;
    }
    
    /**
     * Metodo para conferir se os parametros da venda sao validos.
     *
     * @param cod - Codigo contendo 'n', 'N', 'i' ou 'I' para informar
     *              se a compra esta sendo realizada atraves do codigo
     *              ou do nome do produto
     */
    public void confereParametrosNovaVenda(char cod) {
        if (cod != 'n' && cod != 'N' &&
            cod != 'i' && cod != 'I') {
            setCodError(12);
        } else {
            if (getNomeMovimento() == null) {
                setCodError(5);
            } else {
                if (getDataVenda() == null) {
                    setCodError(6);
                } else {
                    if (getDescricao() == null) {
                        setCodError(7);
                    } else {
                        if (getUnidade() == null) {
                            setCodError(8);
                        } else {
                            if (!eDebCred(getDebitoCredito())) {
                                setCodError(9);
                            } else {
                                if (getQuantidade() <= 0) {
                                    setCodError(10);
                                } else {
                                    if (getPrecoVenda() < 0.0) {
                                        setCodError(11);
                                    } else {
                                        if (cod == 'n' || cod == 'N') {
                                            if (getNomeProduto() == null) {
                                                setCodError(4);
                                            }
                                        } else {
                                            if (getIDProduto() < 0) {
                                                setCodError(0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA
    
    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE VENDA
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNomeMovimento() {
        return nomeMovimento;
    }

    public void setNomeMovimento(String nomeMovimento) {
        this.nomeMovimento = nomeMovimento;
    }
    
    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public char getDebitoCredito() {
        return debitoCredito;
    }

    public void setDebitoCredito(char debitoCredito) {
        this.debitoCredito = debitoCredito;
    }

    public int getIDProduto() {
        return idProduto;
    }

    public void setIDProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIDMovimento() {
        return idMovimento;
    }

    public void setIDMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public int getIDTipoMovimento() {
        return idTipoMovimento;
    }

    public void setIDTipoMovimento(int idTipoMovimento) {
        this.idTipoMovimento = idTipoMovimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }    
    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE VENDA
    // ------------------------------------------------------------------------------------------------- FIM CLASSE VENDA    
}
