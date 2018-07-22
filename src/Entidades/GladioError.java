/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Couth
 */
public class GladioError {
    /*
     * CODIGOS DE ERRO
     * 00 -> NAO HA ERRO.
     */
    private int codError = 0;
    
    public void setCodError(int codError) {
        this.codError = codError;
    }
    
    public int getCodError() {
        return codError;
    }
    
    /**
     * Metodo para conferir se ha erro no programa ou nao.
     * 
     * @return retorna true para caso haja erro e
     *                 false para caso contrario.
     */
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
    public String msgErrorByCod(int codError) {       
        String msg, prefix = "[ERRO] Ocorreu um erro: ",
                    sufix = "Código de erro: "+codError;
        switch (codError) {
            case 0:
                msg = prefix+"Não há erros."+sufix;
                break;
            default:
                msg = prefix+"Código de erro inválido ou não cadastrado."+sufix;
                break;
        }
        return msg;
    }
}
