/*
 * Classe Auxiliares
 *
 * Contem alguns metodos auxiliares de modo geral.
 *
 * Versao: 1.0.0
 */
package Entidades;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Couth
 */
public class Auxiliares {
    
    /**
     * Metodo para conferir se o formato do telefone fixo e valido.
     * 
     * @param telFixo - telefone fixo a ser verificado.
     * 
     * @return true  - formato valido.
     *         false - formato invalido.
     */    
    public boolean eTelFixoValido(String telFixo) {
        boolean retorno;
        // Teste cod
        retorno = telFixo.matches(".((10)|([1-9]1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
        return retorno;
    }
    
    /**
     * Metodo para conferir se o formato do telefone celular e valido.
     * 
     * @param telCelular - telefone celular a ser verificado.
     * 
     * @return true  - formato valido.
     *         false - formato invalido.
     */    
    public boolean eTelCelularValido(String telCelular) {
        boolean retorno;
        // Teste cod
        retorno = telCelular.matches(".((10)|([1-9]1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}");
        return retorno;
    }
    
    /**
     * Metodo para conferir se o formato do cep e valido.
     * 
     * @param cep - cep a ser verificado.
     * 
     * @return true  - formato valido.
     *         false - formato invalido.
     */    
    public boolean eCepValido(String cep) {
        boolean retorno = true;
        int tamanho = cep.length();
        if (tamanho == 8 || tamanho == 9) {
            int i = 0;
            while (i < tamanho && retorno) {
                if (cep.charAt(i) < '0' || cep.charAt(i) > '9') {
                    if (cep.charAt(i) == '-') {
                        if (tamanho != 9 || i != 5) {
                            retorno = false;
                        }
                    } else {
                        retorno = false;
                    }
                }
                i = i + 1;
            }
        } else {
            retorno = false;
        }
        return retorno;
    }
    
    /**
     * Metodo para conferir se o formato do cpf e valido.
     * 
     * @param cpf - cpf a ser verificado.
     * 
     * @return true  - formato valido.
     *         false - formato invalido.
     */    
    public boolean eCpfValido(String cpf) {
        boolean retorno = true;
        int tamanho = cpf.length();
        if (tamanho == 11 || tamanho == 12) {
            int i = 0;
            // verificar formato
            while (i < tamanho && retorno) {
                if (cpf.charAt(i) < '0' || cpf.charAt(i) > '9') {
                    if (cpf.charAt(i) == '-') {
                        if (tamanho != 12 || i != 9) {
                            retorno = false;
                        }
                    } else {
                        retorno = false;
                    }
                }
                i = i + 1;
            }
            
            // verificar se legitimidade
            if (retorno) {
                int somaCpf1 = 0,
                    somaCpf2 = 0,
                    decrescente,
                    dig1, dig2;
                decrescente = 10;
                for (i = 0; i < 10; i = i + 1) {
                    somaCpf1 = (somaCpf1 + Integer.parseInt(""+cpf.charAt(i)) * decrescente);
                    decrescente = decrescente - 1;
                }
                somaCpf1 = (somaCpf1 * 10) % 11;
                
                decrescente = 11;
                for (i = 0; i < 10; i = i + 1) {
                    somaCpf2 = (somaCpf2 + Integer.parseInt(""+cpf.charAt(i)) * decrescente);
                    decrescente = decrescente - 1;
                }
                somaCpf2 = ((somaCpf2 * 2) * 10) % 11;
                
                if (tamanho == 11) {
                    dig1 = Integer.parseInt(""+cpf.charAt(9));
                    dig2 = Integer.parseInt(""+cpf.charAt(10));
                } else {
                    dig1 = Integer.parseInt(""+cpf.charAt(10));
                    dig2 = Integer.parseInt(""+cpf.charAt(11));                    
                }
                
                if (somaCpf1 != dig1 || somaCpf2 != dig2) {
                    retorno = false;
                }
            }
        } else {
            retorno = false;
        }
        return retorno;
    }
    
    /**
     * Metodo para conferir se uma data e valida.
     * 
     * @param data - data a ser verificada.
     * 
     * @return true  - data valida.
     *         false - data invalida.
     */    
    public boolean eDataValida(Date data) {
        boolean retorno;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);
            calendar.setTime(data);
            calendar.getTime(); // se a data for invalida nao sera possivel obte-la
            retorno = true;
        } catch (Exception ex) {
            retorno = false;
        }
        return retorno;
    }
}
