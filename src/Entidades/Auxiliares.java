/*
 * Classe Auxiliares
 *
 * Classe contendo alguns metodos auxiliares de modo geral.
 *
 * Versao: 1.0.0
 */
package Entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
     * @return true  -> telefone fixo valido.
     *         false -> telefone fixo invalido.
     */
    public boolean eTelFixoValido(String telFixo) {
        boolean r = true;
        // CODIGO TESTE
        r = telFixo.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
        return r;
    }
    
    /**
     * Metodo para conferir se o formato do telefone celular e valido.
     * 
     * @param telCelular - telefone celular a ser verificado.
     * 
     * @return true  -> telefone celular valido.
     *         false -> telefone celular invalido.
     */
    public boolean eTelCelularValido(String telCelular) {
        boolean r;
        // CODIGO TESTE
        r = telCelular.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}");
        return r;
    }
    
    /**
     * Metodo para conferir se o formato do cep e valido.
     * 
     * @param cep - cep a ser verificado.
     * 
     * @return true  -> cep valido.
     *         false -> cep invalido.
     */
    public boolean eCepValido(String cep) {
        boolean r = true;
        int tamanho = cep.length();
        if (tamanho == 8 || tamanho == 9) {
            int i = 0;
            // testar se o formato do cep e valido.
            while (i < tamanho && r) {
                if (cep.charAt(i) < '0' || cep.charAt(i) > '9') {
                    if (cep.charAt(i) == '-') {
                        if (tamanho != 9 || i != 5) {
                            r = false;
                        }
                    } else {
                        r = false;
                    }
                }
            }
        } else {
            r = false;
        }
        return r;
    }
    
    /**
     * Metodo para conferir se um cpf e valido.
     * 
     * @param cpf - cpf a ser verificado.
     * 
     * @return true  -> cpf valido.
     *         false -> cpf invalido.
     */
    public boolean eCpfValido(String cpf) {
        boolean r = true;
        int tamanho = cpf.length();
        if (tamanho == 11 || tamanho == 12) {
            int i = 0;
            // testar se o formato do cpf e valido.
            while (i < tamanho && r) {
                if (cpf.charAt(i) < '0' || cpf.charAt(i) > '9') {
                    if (cpf.charAt(i) == '-') {
                        if (tamanho != 12 || i != 9) {
                            r = false;
                        }
                    } else {
                        r = false;
                    }
                }
            }
            
            if (r) {
                int somaCpf1 = 0,
                    somaCpf2 = 0,
                    dig1, dig2,
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
                somaCpf2 = somaCpf2 * 2;
                somaCpf2 = (somaCpf2 * 10) % 11;
                
                if (tamanho == 11) {
                    dig1 = Integer.parseInt(""+cpf.charAt(9));
                    dig2 = Integer.parseInt(""+cpf.charAt(10));
                } else {
                    dig1 = Integer.parseInt(""+cpf.charAt(10));
                    dig2 = Integer.parseInt(""+cpf.charAt(11));                    
                }
                
                if (somaCpf1 != dig1 && somaCpf2 != dig2) {
                    r = false;
                }
            }
        } else {
            r = false;
        }
        return r;
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
}
