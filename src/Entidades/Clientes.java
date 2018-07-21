/*
 * Classe Clientes
 *
 * Classe utilizada para controle dos clientes, contendo
 * metodos utilitarios como set|get, alem de tratamento de erros.
 *
 * Versao: 1.0.0
 */
package Entidades;

import DAO.VariantesEstoqueDAO;
import Interface.GladioError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Couth
 */
public class Clientes implements GladioError {
    
    // ------------------------------------------------------------------------------------------------- INICIO INTERFACE GLADIOERROR
    /*
     * CODIGOS DE ERRO
     * 00 -> NAO HA ERRO.
     * 01 -> ERRO NO ID DO VARIANTE.
     * 02 -> ERRO NO NOME DO CLIENTE.
     * 03 -> ERRO NO CPF DO CLIENTE.
     * 04 -> ERRO NA TELEFONE CELULAR DO CLIENTE.
     * 05 -> ERRO NO TELEFONE FIXO DO CLIENTE.
     * 06 -> ERRO NO CEP DO CLIENTE.
     * 07 -> ERRO NA RUA DO CLIENTE.
     * 08 -> ERRO NA COMPLEMENTO DO CLIENTE.
     * 09 -> ERRO NO BAIRRO DO CLIENTE.
     * 10 -> ERRO NA DATA DE CADASTRO DO CLIENTE.
     * 11 -> ERRO NA HORA DE CADASTRO DO CLIENTE.
     * 12 -> ERRO SQL.
     * 13 -> ERRO NO CADASTRO DO CLIENTE.
     * 14 -> ERRO NA PESQUISA DE CLIENTES.
     * 15 -> ERRO NA PESQUISA DO CLIENTE PELO SEU ID.
     * 16 -> ERRO NA PESQUISA DO CLIENTE PELO SEU NOME.
     * 17 -> ERRO NA PESQUISA DO CLIENTE PELO SEU CPF.
     * 18 -> ERRO NA PESQUISA DOS CLIENTES CADASTRADOS EM DETERMINADA DATA.
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
        String msg, prefix = "[ERRO] Clientes: ";
        switch (codError) {
            case 0:
                msg = prefix+"Não há erros.";
                break;
            case 1:
                msg = prefix+"Erro no id do variante.";
                break;
            case 2:
                msg = prefix+"Erro no nome do cliente.";
                break;
            case 3:
                msg = prefix+"Erro no cpf do cliente.";
                break;
            case 4:
                msg = prefix+"Erro no telefone celular do cliente.";
                break;
            case 5:
                msg = prefix+"Erro no telefone fixo do cliente.";
                break;
            case 6:
                msg = prefix+"Erro no cep do cliente.";
                break;
            case 7:
                msg = prefix+"Erro na rua do cliente.";
                break;
            case 8:
                msg = prefix+"Erro no complemento do cliente.";
                break;
            case 9:
                msg = prefix+"Erro no bairro do cliente.";
                break;
            case 10:
                msg = prefix+"Erro na data de cadastro do cliente.";
                break;
            case 11:
                msg = prefix+"Erro na hora de cadastro do cliente.";
                break;
            case 12:
                msg = prefix+"Erro SQL: ";
                break;
            case 13:
                msg = prefix+"Erro no cadastro do cliente.";
                break;
            case 14:
                msg = prefix+"Erro na pesquisa de clientes.";
                break;
            case 15:
                msg = prefix+"Erro na pesquisa do cliente pelo seu id.";
                break;
            case 16:
                msg = prefix+"Erro na pesquisa do cliente pelo seu nome.";
                break;
            case 17:
                msg = prefix+"Erro na pesquisa do cliente pelo seu cpf.";
                break;
            case 18:
                msg = prefix+"Erro na pesquisa dos clientes cadastrados em determinada data.";
                break;
            default:
                msg = prefix+"Código de erro inválido ou não cadastrado.";
                break;
        }
        return msg;
    }
    // ------------------------------------------------------------------------------------------------- FIM INTERFACE GLADIOERROR
    
    // ------------------------------------------------------------------------------------------------- INICIO CLASSE VENDA
    private int idVariante;
    private String nome, cpf, telCelular, telFixo, cep,
                   rua, complemento, bairro, dataCadastro, horaCadastro;
    
    /*
     * Metodo para cadastrar um novo cliente sem passar a data atual.
     * Obs.: a data é gerada pela data atual do computador.
     */
    public int novoClienteEObterId(String nome, String cpf, String telCelular, String telFixo, String cep,
                                   String rua, String complemento, String bairro) throws Exception {
        
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        
        String dataFormatada = formatoData.format(date);
        String horaFormatada = formatoHora.format(date);
        
        return novoClienteEObterId(nome, cpf, telCelular, telFixo, cep, rua, complemento, bairro,
                                   dataFormatada, horaFormatada);
    }
    
    /*
     * Metodo para cadastrar um novo cliente.
     */
    public int novoClienteEObterId(String nome, String cpf, String telCelular, String telFixo, String cep,
                                   String rua, String complemento, String bairro, String dataCadastro, String horaCadastro) throws Exception {
        
        setNome(nome);
        setCpf(cpf);
        setTelCelular(telCelular);
        setTelFixo(telFixo);
        setCep(cep);
        setRua(rua);
        setComplemento(complemento);
        setBairro(bairro);
        setDataCadastro(dataCadastro);
        setHoraCadastro(horaCadastro);
        
        confereParametrosNovoCliente();
        
        if (hasError()) {
            throw new Exception(msgError());
        } else {
            try {
                VariantesEstoqueDAO cDAO = new VariantesEstoqueDAO();
                String dataHora = getDataCadastro()+" "+getHoraCadastro();
                ResultSet rs = cDAO.inserirClienteEObterId(nome, cpf, telCelular, telFixo, cep, rua, complemento, bairro,
                                                           dataHora, 'C');
                if (rs == null) {
                    setCodError(13);
                    throw new Exception(msgError());
                } else {
                    return rs.getInt(1);
                } 
            } catch (SQLException sqlE) {
                setCodError(12);
                throw new Exception(msgError()+" "+sqlE);
            }
        }
    }
    
    /*
     * Metodo para pesquisar todos os clientes.
     */
    public ResultSet pesquisarClientes() throws Exception {
        ResultSet rs;
        try {
            VariantesEstoqueDAO cDAO = new VariantesEstoqueDAO();
            rs = cDAO.pesquisarClientes();
            if (rs == null) {
                setCodError(14);
                throw new Exception(msgError());
            }
        } catch (SQLException sqlE) {
            setCodError(12);
            throw new Exception(msgError()+" "+sqlE);
        }
        return rs;
    }
    
    /*
     * Metodo para pesquisar um cliente pelo seu id.
     */
    public ResultSet pesquisarCliente(int idVariante) throws Exception {
        if (idVariante >= 0) {
            ResultSet rs;
            try {
                VariantesEstoqueDAO cDAO = new VariantesEstoqueDAO();
                rs = cDAO.pesquisarCliente(idVariante);
                if (rs == null) {
                    setCodError(15);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(12);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(1);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar um cliente pelo seu nome.
     */
    public ResultSet pesquisarCliente(String nome) throws Exception {
        if (nome == null) {
            ResultSet rs;
            try {
                VariantesEstoqueDAO cDAO = new VariantesEstoqueDAO();
                rs = cDAO.pesquisarCliente(nome);
                if (rs == null) {
                    setCodError(16);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(12);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(2);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar um cliente pelo seu cpf.
     */
    public ResultSet pesquisarClienteCPF(String cpf) throws Exception {
        if (cpf == null) {
            ResultSet rs;
            try {
                VariantesEstoqueDAO cDAO = new VariantesEstoqueDAO();
                rs = cDAO.pesquisarClienteCPF(cpf);
                if (rs == null) {
                    setCodError(17);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(12);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(2);
            throw new Exception(msgError());
        }
    }
    
    /*
     * Metodo para pesquisar todos os clientes cadastrados em determinada data.
     */
    public ResultSet pesquisarClienteDataCadastro(String dataCadastro) throws Exception {
        if (dataCadastro == null) {
            ResultSet rs;
            try {
                VariantesEstoqueDAO cDAO = new VariantesEstoqueDAO();
                rs = cDAO.pesquisarClienteDataCadastro(dataCadastro);
                if (rs == null) {
                    setCodError(18);
                    throw new Exception(msgError());
                }
            } catch (SQLException sqlE) {
                setCodError(12);
                throw new Exception(msgError()+" "+sqlE);
            }
            return rs;
        } else {
            setCodError(2);
            throw new Exception(msgError());
        }
    }
    // ------------------------------------------------------------------------------------------------- INICIO AUXILIARES CLASSE VENDA    
    /**
     * Metodo para conferir se os parametros do cliente sao validos.
     */
    public void confereParametrosNovoCliente() {
        if (getNome() == null) {
            setCodError(2);
        } else {
            Auxiliares aux = new Auxiliares();
            if (!aux.eCpfValido(getCpf())) {
                setCodError(3);
            } else {
                if (!aux.eTelCelularValido(getTelCelular())) {
                    setCodError(4);
                } else {
                    if (!aux.eTelFixoValido(getTelFixo())) {
                        setCodError(5);
                    } else {
                        if (!aux.eCepValido(getCep())) {
                            setCodError(6);
                        } else {
                            if (getRua() == null) {
                                setCodError(7);
                            } else {
                                if (getComplemento() == null) {
                                    setCodError(8);
                                } else {
                                    if (getBairro() == null) {
                                        setCodError(9);
                                    } else {
                                        if (!aux.eDataValida(getDataCadastro())) {
                                            setCodError(10);
                                        } else {
                                            if (!aux.eHoraValida(getHoraCadastro())) {
                                                setCodError(11);
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
    // ------------------------------------------------------------------------------------------------- FIM AUXILIARES CLASSE VENDA

    // ------------------------------------------------------------------------------------------------- INICIO SET|GET CLASSE MOVIMENTOS
    public int getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(int idVariante) {
        this.idVariante = idVariante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getTelFixo() {
        return telFixo;
    }

    public void setTelFixo(String telFixo) {
        this.telFixo = telFixo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getHoraCadastro() {
        return horaCadastro;
    }

    public void setHoraCadastro(String horaCadastro) {
        this.horaCadastro = horaCadastro;
    }
    // ------------------------------------------------------------------------------------------------- FIM SET|GET CLASSE MOVIMENTOS
    // ------------------------------------------------------------------------------------------------- FIM CLASSE VENDA
}
