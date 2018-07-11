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

/**
 *
 * @author Couth
 */
public class Venda implements GladioError {
    
    // ------------------------------------------------------------------------------------------------- INICIO INTERFACE GLADIOERROR
    /*
     * CODIGOS DE ERRO
     * 00 -> NAO HA ERRO.
     * 01 -> ERRO NO ID DO PRODUTO.
     * 02 -> ERRO NO ID DO MOVIMENTO.
     * 03 -> ERRO NO NOME DO MOVIMENTO.
     * 04 -> ERRO NA DATA DA VENDA.
     * 05 -> ERRO NA HORA DA VENDA.
     * 06 -> ERRO NA QUANTIDADE DE PRODUTOS.
     * 07 -> QUANTIDADE DE COMPRA MAIOR QUE A QUANTIDADE DISPONIVEL NO ESTOQUE.
     * 08 -> O ESTOQUE DESTE PRODUTO ESTA VAZIO.
     * 09 -> ERRO SQL.
     * 10 -> ERRO NA PESQUISA DO PRODUTOS MOVIMENTO.
     * 11 -> ERRO NA PESQUISA DO PRODUTOS MOVIMENTO PELO ID DO PRODUTOMOVIMENTO.
     * 12 -> ERRO NA PESQUISA DO PRODUTOS MOVIMENTO PELO ID DO MOVIMENTO.
     * 13 -> ERRO NA PESQUISA DO PRODUTOS MOVIMENTO PELO ID DO PRODUTO.
     * 14 -> ERRO NA PESQUISA DO PRODUTOS MOVIMENTO PELA QUANTIDADE.
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
        String msg, prefix = "[ERRO] Vendas: ";
        switch (codError) {
            case 0:
                msg = prefix+"Não há erros.";
                break;
            case 1:
                msg = prefix+"Erro no id do produto.";
                break;
            case 2:
                msg = prefix+"Erro no id do movimento.";
                break;
            case 3:
                msg = prefix+"Erro no nome do movimento.";
                break;
            case 4:
                msg = prefix+"Erro na data da venda.";
                break;
            case 5:
                msg = prefix+"Erro na hora da venda.";
                break;
            case 6:
                msg = prefix+"Erro na quantidade de produtos.";
                break;
            case 7:
                msg = prefix+"Quantidade de produtos disponiveis é inferior a de compra.";
                break;
            case 8:
                msg = prefix+"O estoque deste produto está vazio.";
                break;
            case 9:
                msg = prefix+"Erro SQL: ";
                break;
            default:
                msg = prefix+"Código de erro inválido ou não cadastrado.";
                break;
        }
        return msg;
    }
    // ------------------------------------------------------------------------------------------------- FIM INTERFACE GLADIOERROR
    
    // ------------------------------------------------------------------------------------------------- INICIO CLASSE VENDA
    private String nomeMovimento;
    private int idProduto, idMovimento, quantidade;
    
    /*
     * Metodo para venda de um novo produto atraves de seu codigo.
     */
    public void novoProdutoMovimento(int idProduto, int idMovimento, int quantidade) throws Exception {
        
        setIdProduto(idProduto);
        setIdMovimento(idMovimento);
        setQuantidade(quantidade);
        
        confereParametrosNovoProdutoMovimento();
        
        if (hasError()) {
            throw new Exception(msgError());
        } else {
            try {
                VendasDAO vDAO = new VendasDAO();
                int quantidadeEstoque = vDAO.obterQuantidadeProdutosDisponiveis(getIdProduto());
                if (quantidadeEstoque >= 0) {
                    if (quantidade <= quantidadeEstoque) {
                        vDAO.inserirProdutoMovimento(getIdProduto(), getIdMovimento(), getQuantidade());
                    } else {
                        setCodError(6);
                        throw new Exception(msgError());
                    }
                } else {
                    setCodError(7);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(8);
                throw new Exception(msgError()+" "+sqlE);
            }
        }
    }
    
    /*
     * Metodo para pesquisar todos os produtos movimentos.
     */
    public ResultSet pesquisaProdutoMovimento() throws Exception {
        ResultSet rs;
        try {
            VendasDAO vDAO = new VendasDAO();
            rs = vDAO.pesquisarProdutoMovimento();
            if (rs == null) {
                setCodError(10);
                throw new Exception(msgError());
            }
        } catch (SQLException sqlE) {
            setCodError(8);
            throw new Exception(msgError()+" "+sqlE);
        }
        return rs;
    }
    
    /*
     * Metodo para pesquisar todos os produtos movimentos pelo id do produto movimento.
     */
    public ResultSet pesquisaProdutoMovimento(int idProdutoMovimento) throws Exception {
        if (idProdutoMovimento >= 0) {
            ResultSet rs;
            try {
                VendasDAO vDAO = new VendasDAO();
                rs = vDAO.pesquisarProdutoMovimento(idProdutoMovimento);
                if (rs == null) {
                    setCodError(11);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(8);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(11);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar todos os produtos movimentos pelo id do movimento.
     */
    public ResultSet pesquisaProdutoMovimentoPeloIdMovimento(int idMovimento) throws Exception {
        if (idMovimento >= 0) {
            ResultSet rs;
            try {
                VendasDAO vDAO = new VendasDAO();
                rs = vDAO.pesquisarProdutoMovimento(idMovimento);
                if (rs == null) {
                    setCodError(12);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(8);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(12);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar todos os produtos movimentos pelo id do produto.
     */
    public ResultSet pesquisaProdutoMovimentoPeloIdProduto(int idProduto) throws Exception {
        if (idProduto  >= 0) {
            ResultSet rs;
            try {
                VendasDAO vDAO = new VendasDAO();
                rs = vDAO.pesquisarProdutoMovimento(idProduto);
                if (rs == null) {
                    setCodError(13);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(8);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(13);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar todos os produtos movimentos pela quantidade.
     */
    public ResultSet pesquisaProdutoMovimentoPelaQuantidade(int quantidade) throws Exception {
        if (quantidade > 0) {
            ResultSet rs;
            try {
                VendasDAO vDAO = new VendasDAO();
                rs = vDAO.pesquisarProdutoMovimento(quantidade);
                if (rs == null) {
                    setCodError(14);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(8);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(14);
            throw new Exception(msgError());
        }
    }
    
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA    
    /**
     * Metodo para conferir se os parametros da venda sao validos.
     */
    public void confereParametrosNovoProdutoMovimento() {
        if (getIdProduto() < 0) {
            setCodError(1);
        } else {
            if (getIdMovimento() < 0) {
                setCodError(2);
            } else {
                if (getNomeMovimento() == null || 
                    !(getNomeMovimento().equalsIgnoreCase("VENDA") || 
                      getNomeMovimento().equalsIgnoreCase("COMPRA"))) {
                    setCodError(3);
                } else {
                    if (getQuantidade() < 0) {
                        setCodError(6);
                    }
                }
            }
        }
    }
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA
    
    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE VENDA
    public String getNomeMovimento() {
        return nomeMovimento;
    }

    public void setNomeMovimento(String nomeMovimento) {
        this.nomeMovimento = nomeMovimento;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE VENDA
    // ------------------------------------------------------------------------------------------------- FIM CLASSE VENDA   
}
