/*
 * Classe Movimentos
 *
 * Classe utilizada para controle dos movimentos, contendo
 * metodos utilitarios como set|get, alem de tratamento de erros.
 *
 * Versao: 1.0.0
 */
package Entidades;

import DAO.MovimentosDAO;
import Interface.GladioError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Couth
 */
public class Movimentos implements GladioError {
    
    // ------------------------------------------------------------------------------------------------- INICIO INTERFACE GLADIOERROR
    /*
     * CODIGOS DE ERRO
     * 00 -> NAO HA ERRO.
     * 01 -> ERRO NO ID DO MOVIMENTO.
     * 02 -> ERRO NO ID DO TIPO DE MOVIMENTO.
     * 03 -> ERRO NO ID DO VARIANTE.
     * 04 -> ERRO NA DATA DO MOVIMENTO.
     * 05 -> ERRO NA HORA DO MOVIMENTO.
     * 06 -> ERRO NO NOME DO MOVIMENTO.
     * 07 -> ERRO SQL.
     * 08 -> ERRO NA INSERCAO DO MOVIMENTOS ESTOQUE.
     * 09 -> ERRO NA PESQUISA DOS MOVIMENTOS ESTOQUE.
     * 10 -> ERRO NA PESQUISA DO MOVIMENTOS ESTOQUE PELO ID DO MOVIMENTO.
     * 11 -> ERRO NA PESQUISA DO MOVIMENTOS ESTOQUE PELO ID DO TIPO DE MOVIMENTO.
     * 12 -> ERRO NA PESQUISA DO MOVIMENTOS ESTOQUE PELA DATA DO MOVIMENTO.
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
                msg = prefix+"Erro no id do movimento.";
                break;
            case 2:
                msg = prefix+"Erro no id do tipo de movimento.";
                break;
            case 3:
                msg = prefix+"Erro no id do variante.";
                break;
            case 4:
                msg = prefix+"Erro na data do movimento.";
                break;
            case 5:
                msg = prefix+"Erro na hora do movimento.";
                break;
            case 6:
                msg = prefix+"Erro no nome do movimento.";
                break;
            case 7:
                msg = prefix+"Erro SQL: ";
                break;
            case 8:
                msg = prefix+"Erro na inserção do movimentos estoque.";
                break;
            case 9:
                msg = prefix+"Erro na pesquisa dos movimentos estoque.";
                break;
            case 10:
                msg = prefix+"Erro na pesquisa dos movimentos estoque pelo id do movimento.";
                break;
            case 11:
                msg = prefix+"Erro na pesquisa dos movimentos estoque pelo id do tipo de movimento.";
                break;
            case 12:
                msg = prefix+"Erro na pesquisa dos movimentos estoque pela data do movimento.";
                break;
            default:
                msg = prefix+"Código de erro inválido ou não cadastrado.";
                break;
        }
        return msg;
    }
    // ------------------------------------------------------------------------------------------------- FIM INTERFACE GLADIOERROR
    
    // ------------------------------------------------------------------------------------------------- INICIO CLASSE VENDA
    private int idMovimento, idTipoMovimento, idVariante;
    private String dataMovimento, horaMovimento, nomeMovimento;
    
    /*
     * Metodo para movimentar um novo estoque sem passar a data atual.
     * Obs.: a data é gerada pela data atual do computador.
     */
    public void novoMovimentoEstoqueEObterId(int idMovimento, int idTipoMovimento, int idVariante,
            String nomeMovimento) throws Exception {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String dataFormatada = formatoData.format(date);
        String horaFormatada = formatoHora.format(date);
        novoMovimentoEstoqueEObterId(idMovimento, idTipoMovimento, idVariante,
                                     dataFormatada, horaFormatada, nomeMovimento);
    }
    
    /*
     * Metodo para movimentar um novo estoque.
     */
    public int novoMovimentoEstoqueEObterId(int idMovimento, int idTipoMovimento, int idVariante,
            String dataMovimento, String horaMovimento, String nomeMovimento) throws Exception {
        
        setIdMovimento(idMovimento);
        setIdTipoMovimento(idMovimento);
        setIdVariante(idVariante);
        setDataMovimento(dataMovimento);
        setNomeMovimento(nomeMovimento);
        
        confereParametrosNovoMovimentoEstoque();
        
        if (hasError()) {
            throw new Exception(msgError());
        } else {
            try {
                MovimentosDAO mDAO = new MovimentosDAO();
                String dataHora = getDataMovimento()+" "+getHoraMovimento();
                ResultSet rs = mDAO.inserirMovimentoEstoqueEObterId(getNomeMovimento(), dataHora, idVariante);
                if (rs == null) {
                    setCodError(8);
                    throw new Exception(msgError());
                } else {
                    return rs.getInt(1);
                } 
            } catch (SQLException sqlE) {
                setCodError(7);
                throw new Exception(msgError()+" "+sqlE);
            }
        }
    }
    
    /*
     * Metodo para pesquisar todos os movimentos do estoque.
     */
    public ResultSet pesquisarMovimentoEstoque() throws Exception {
        ResultSet rs;
        try {
            MovimentosDAO mDAO = new MovimentosDAO();
            rs = mDAO.pesquisarMovimentoEstoque();
            if (rs == null) {
                setCodError(9);
                throw new Exception(msgError());
            }
        } catch (SQLException sqlE) {
            setCodError(7);
            throw new Exception(msgError()+" "+sqlE);
        }
        return rs;
    }
    
    /*
     * Metodo para pesquisar todos os movimentos do estoque pelo id do movimento.
     */
    public ResultSet pesquisarMovimentoEstoque(int idMovimento) throws Exception {
        if (idMovimento >= 0) {
            ResultSet rs;
            try {
                MovimentosDAO mDAO = new MovimentosDAO();
                rs = mDAO.pesquisarMovimentoEstoque(idMovimento);
                if (rs == null) {
                    setCodError(10);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(7);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(1);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar todos os movimentos do estoque pelo id do tipo do movimento.
     */
    public ResultSet pesquisarMovimentoEstoquePeloTipoMovimento(int idTipoMovimento) throws Exception {
        if (idTipoMovimento >= 0) {
            ResultSet rs;
            try {
                MovimentosDAO mDAO = new MovimentosDAO();
                rs = mDAO.pesquisarMovimentoEstoquePeloTipoMovimento(idTipoMovimento);
                if (rs == null) {
                    setCodError(11);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(7);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(2);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar todos os movimentos do estoque pelo id do variante.
     */
    public ResultSet pesquisarMovimentoEstoquePeloIdVariante(int idVariante) throws Exception {
        if (idVariante >= 0) {
            ResultSet rs;
            try {
                MovimentosDAO mDAO = new MovimentosDAO();
                rs = mDAO.pesquisarMovimentoEstoquePeloIdVariante(idVariante);
                if (rs == null) {
                    setCodError(11);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(7);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(3);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar todos os movimentos do estoque pela data do movimento.
     */
    public ResultSet pesquisarMovimentoEstoque(String dataMovimento) throws Exception {
        String[] dataSQL = dataMovimento.split(" ");
        if (eDataValida(dataSQL[0]) && eHoraValida(dataSQL[1])) {
            ResultSet rs;
            try {
                MovimentosDAO mDAO = new MovimentosDAO();
                rs = mDAO.pesquisarMovimentoEstoque(dataMovimento);
                if (rs == null) {
                    setCodError(12);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(7);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            if (eDataValida(dataSQL[0])) {
                setCodError(4);
                throw new Exception(msgError());
            } else {
                setCodError(5);
                throw new Exception(msgError());
            }
        }
    }
    
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA    
    /**
     * Metodo para conferir se os parametros da venda sao validos.
     */
    public void confereParametrosNovoMovimentoEstoque() {
        if (getIdMovimento() < 0) {
            setCodError(1);
        } else {
            if (getIdTipoMovimento() < 0) {
                setCodError(2);
            } else {
                if (getIdVariante() < 0) {
                    setCodError(3);
                } else {
                    if (getNomeMovimento() == null || 
                        !(getNomeMovimento().equalsIgnoreCase("VENDA") || 
                          getNomeMovimento().equalsIgnoreCase("COMPRA"))) {
                        setCodError(6);
                    } else {
                        if (!eDataValida(getDataMovimento())) {
                            setCodError(4);
                        } else {
                            if (!eHoraValida(getHoraMovimento())) {
                                setCodError(5);
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
    // ------------------------------------------------------------------------------------------------- FIM AUXILIARES CLASSE VENDA

    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE MOVIMENTOS
    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public int getIdTipoMovimento() {
        return idTipoMovimento;
    }

    public void setIdTipoMovimento(int idTipoMovimento) {
        this.idTipoMovimento = idTipoMovimento;
    }

    public int getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(int idVariante) {
        this.idVariante = idVariante;
    }

    public String getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(String dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public String getHoraMovimento() {
        return horaMovimento;
    }

    public void setHoraMovimento(String horaMovimento) {
        this.horaMovimento = horaMovimento;
    }

    public String getNomeMovimento() {
        return nomeMovimento;
    }

    public void setNomeMovimento(String nomeMovimento) {
        this.nomeMovimento = nomeMovimento;
    }
    // ------------------------------------------------------------------------------------------------- FIM SET|GET CLASSE MOVIMENTOS
    // ------------------------------------------------------------------------------------------------- FIM CLASSE VENDA
}
