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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
     * 12 -> PARAMETRO 'confereParametrosNovaVenda' INVALIDO.
     * 14 -> ERRO NA PESQUISA DE QUANTIDADE DISPONIVEL NO ESTOQUE.
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
        String msg, prefix = "[ERRO] Vendas: ";
        switch (codError) {
            case 0:
                msg = prefix+"Não há erros.";
                break;
            case 1:
                msg = prefix+"Erro no código(id) do produto.";
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
//    private String nomeProduto, nomeMovimento, dataVenda, descricao, unidade;
//    private char debitoCredito;
//    private int idProduto, idMovimento, idTipoMovimento, quantidade;
//    private float precoVenda;
    private String nomeMovimento, dataVenda, horaVenda;
    private int idProduto, idMovimento, quantidade;

    
    /*
     * Metodo para venda de um novo produto atraves de seu codigo.
     */
    public void novaVenda(int idProduto, int idMovimento, String nomeMovimento, int quantidade) 
        throws Exception {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String dataFormatada = formatoData.format(date);
        String horaFormatada = formatoHora.format(date);
        novaVenda(idProduto, idMovimento, nomeMovimento, dataFormatada, horaFormatada, quantidade);
    }
    
    
    /*
     * Metodo para venda de um novo produto atraves de seu codigo.
     */
    public void novaVenda(int idProduto, int idMovimento, String nomeMovimento, 
        String dataVenda, String horaVenda, int quantidade) throws Exception {
        
        setIdProduto(idProduto);
        setIdMovimento(idMovimento);
        setNomeMovimento(nomeMovimento);
        setDataVenda(dataVenda);
        setHoraVenda(horaVenda);
        setQuantidade(quantidade);
        
        confereParametrosNovaVenda();
        
        if (hasError()) {
            throw new Exception(msgError());
        } else {
            try {
                VendasDAO vDAO = new VendasDAO();
                int quantidadeEstoque = vDAO.obterQuantidadeProdutosDisponiveis(getIdProduto());
                if (quantidadeEstoque >= 0) {
                    if (quantidade <= quantidadeEstoque) {
                        //ResultSet rs = vDAO.inserirMovimentoEstoqueEObterId(getNomeMovimento(), getDataVenda());
                        //if (rs == null) {
                            //setCodError(2);
                            //throw new Exception(msgError());
                        //} else {
                        vDAO.inserirProdutoMovimento(getIdProduto(), getIdMovimento(), getQuantidade());
                        //}
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
    
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA    
    /**
     * Metodo para conferir se os parametros da venda sao validos.
     */
    public void confereParametrosNovaVenda() {
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
                    if (!eDataValida(getDataVenda())) {
                        setCodError(4);
                    } else {
                        if (!eHoraValida(getHoraVenda())) {
                            setCodError(5);
                        } else {
                            if (getQuantidade() < 0) {
                                setCodError(6);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Metodo para conferir se uma data e valida.
     * 
     * @param data - data a ser verificada.
     * 
     * @return true  -> data valida.
     *         false -> data invalida.
     */
    public boolean eDataValida(String data) {
        boolean r = true;
        int tamanho = data.length();
        if (tamanho == 10) {
            int i = 0;
            // testar se o formato da data e valido
            while (i < tamanho && r) {
                if (i == 2 || i == 5) {
                    if (data.charAt(i) != '/') {
                        r = false;
                    }
                } else {
                    if (data.charAt(i) < '0' ||
                        data.charAt(i) > '9') {
                        r = false;
                    }
                }
                i = i + 1;
            }
            
            if (r) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    sdf.parse(data);
                } catch (ParseException pe) {
                    r = false;
                }
            }
        } else {
            r = false;
        }
        return r;
    }
    
    /**
     * Metodo para conferir se uma hora e valida.
     * 
     * @param hora - hora a ser verificada.
     * 
     * @return true  -> hora valida.
     *         false -> hora invalida.
     */
    public boolean eHoraValida(String hora) {
        boolean r = true;
        int tamanho = hora.length();
        if (tamanho == 8) {
            int i = 0;
            // testar se o formato da data e valido
            while (i < tamanho && r) {
                if (i == 2 || i == 5) {
                    if (hora.charAt(i) != ':') {
                        r = false;
                    }
                } else {
                    if (hora.charAt(i) < '0' ||
                        hora.charAt(i) > '9') {
                        r = false;
                    }
                }
                i = i + 1;
            }
            
            if (r) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    sdf.setLenient(false);
                    sdf.parse(hora);
                } catch (ParseException pe) {
                    r = false;
                }
            }
        } else {
            r = false;
        }
        return r;
    }
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA
    
    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE VENDA
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

    public String getHoraVenda() {
        return horaVenda;
    }

    public void setHoraVenda(String horaVenda) {
        this.horaVenda = horaVenda;
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
