/*
 * Classe VariantesEstoqueDAO
 *
 * Classe utilizada para controle dos variantes do estoque.
 *
 * Contem metodos de manipulacao do banco de dados.
 *
 * Versao: 1.0.0
 */
package DAO;

import Entidades.Auxiliares;
import Entidades.GladioError;
import Interface.ClasseDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author ACER | Couth
 */
public class VariantesEstoqueDAO implements ClasseDAO {
    
    /*
    * Atributos da classe
    */
    private int idVariante;
    private String nome;
    private String cpf;
    private String cnpj;
    private String telefoneCelular;
    private String telefoneFixo;
    private String cep;
    private String rua;
    private String complemento;
    private String bairro;
    private Date dataCadastro;
    private String nomeFantasia;
    private char tipo;    
    
    private final GladioError ge = new GladioError();
    private final Auxiliares aux = new Auxiliares();
    
    public ResultSet rsDados;
            
    // --------------------------------------------------------------------------------------------------------------- INICIO INTERFACE
    /**
     * Metodo para inserir um novo dado na tabela variantesestoque.
     * 
     * @return true  - insercao efetuada com sucesso.
     *         false - erro na insercao.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean inserir() throws Exception {
        if (conferir(0)){
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("insert into variantesestoque "
                    + "(ID_Variante, Nome, CPF, TelefoneCelular, TelefoneFixo, CEP, Rua, Complemento, Bairro, "
                    + "DataCadastro, Tipo) "
                    + "values(?,?,?,?,?,?,?,?,?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?)");
                SQL.setInt(1, getIdVariante());
                SQL.setString(2, getNome());
                SQL.setString(3, getCpf());
                SQL.setString(4, getTelefoneCelular());
                SQL.setString(5, getTelefoneCelular());
                SQL.setString(6, getCep());
                SQL.setString(7, getRua());
                SQL.setString(8, getComplemento());
                SQL.setString(9, getBairro());
                SQL.setDate(10, (java.sql.Date) getDataCadastro());
                SQL.setString(11, ""+getTipo());
                SQL.executeUpdate();

                SQL = con.prepareStatement("select * from variantesestoque where ID_Variante = ?");
                SQL.setInt(1, getIdVariante());
                setRsDados(SQL.executeQuery());
                executou = true;
                
                setVariante();
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para editar um dado ja existente na tabela variantesestoque.
     * 
     * @return true  - edicao efetuada com sucesso.
     *         false - erro na edicao.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean editar() throws Exception {
        if (conferir(0)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("UPDATE VariantesEstoque " +
                    "SET Nome = ?, " +
                    "CPF = ?, " +
                    "CNPJ = ?, " +
                    "TelefoneCelular = ?, " +
                    "TelefoneFixo = ?, " +
                    "CEP = ?, " +
                    "Rua = ?, " +
                    "Complemento = ?, " +
                    "Bairro = ?, " +
                    "DataCadastro = ?, " +
                    "NomeFantasia = ?, " +
                    "Tipo = ?, " +
                    "WHERE ID_Variante = ?");
                SQL.setString(1, getNome());
                SQL.setString(2, getCpf());
                SQL.setString(3, getTelefoneCelular());
                SQL.setString(4, getTelefoneCelular());
                SQL.setString(5, getCep());
                SQL.setString(6, getRua());
                SQL.setString(7, getComplemento());
                SQL.setString(8, getBairro());
                SQL.setDate(9, (java.sql.Date) getDataCadastro());
                SQL.setString(10, String.valueOf(getTipo()));
                SQL.setInt(11, getIdVariante());  
                SQL.executeUpdate();

                SQL = con.prepareStatement("select * from variantesestoque where ID_Variante = ?");
                SQL.setInt(1, getIdVariante());
                setRsDados(SQL.executeQuery());
                executou = getRsDados().next();
                
                setVariante();
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para excluir um dado ja existente na tabela variantesestoque.
     * 
     * @return true  - exclusao efetuada com sucesso.
     *         false - erro na exclusao.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean deletar() throws Exception {
        if (conferir(1)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select count(1) as Count from movimentosestoque where ID_Variante = ?");
                SQL.setInt(1, getIdVariante());
                ResultSet rs = SQL.executeQuery();
                if (rs == null) {
                    ge.setCodError(0); // NUMERO COD ERRO
                    throw new Exception(ge.msgError());
                } else {
                    if (rs.getInt("Count") > 0) {
                        ge.setCodError(0); // NUMERO COD ERRO
                        throw new Exception(ge.msgError());
                    } else {
                        SQL = con.prepareStatement("delete from variantesestoque where ID_Variante = ?");   
                        SQL.setInt(1, getIdVariante());
                        SQL.executeUpdate();
                        setRsDados(null);
                        executou = true;
                    }
                }
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            limpar();
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para pesquisar um dado ja existente na tabela variantesestoque a
     * partir do seu id.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean pesquisar() throws Exception {
        if (conferir(1)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where ID_Variante = ?");
                SQL.setInt(1, getIdVariante());
                setRsDados(SQL.executeQuery());
                executou = true;
                setVariante();
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para pesquisar um dado ja existente na tabela variantesestoque a
     * partir do seu tipo.
     * 
     * @param tipo - tipo a ser pesquisado.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    public boolean pesquisar(char tipo) throws Exception {
        if (tipo == '?' || tipo == '?') {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where Tipo = '?'");
                SQL.setString(1, ""+getTipo());
                setRsDados(SQL.executeQuery());
                executou = true;
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para pesquisar um dado ja existente na tabela variantesestoque a
     * partir do seu nome.
     * 
     * @param nome - nome a ser pesquisado.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    public boolean pesquisar(String nome) throws Exception {
        if (nome != null && nome.length() > 0) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where Nome like '?%'");
                SQL.setString(1, getNome());
                setRsDados(SQL.executeQuery());
                executou = true;
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para pesquisar um dado ja existente na tabela variantesestoque a
     * partir do seu cpf.
     * 
     * @param cpf - cpf a ser pesquisado.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
     public boolean pesquisar(int cpf) throws Exception {
        if (aux.eCpfValido(""+cpf)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where CPF = '?'");
                SQL.setString(1, ""+cpf);
                setRsDados(SQL.executeQuery());
                executou = true;
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para pesquisar dados da tabela variantesestoque dentro de um periodo.
     * 
     * @param dataInicial - data inicial do periodo a ser pesquisado.
     * 
     * @param dataFinal   - data final do periodo a ser pesquisado.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    public boolean pesquisar(Date dataInicial, Date dataFinal) throws Exception {
        if (aux.eDataValida(dataInicial) && aux.eDataValida(dataFinal)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where DataCadastro between ? and ?");   
                SQL.setDate(1, (java.sql.Date) dataInicial);
                SQL.setDate(2, (java.sql.Date) dataFinal);
                setRsDados(SQL.executeQuery());
                executou = true;
            } catch (SQLException sqlE) {
                ge.setCodError(0); // NUMERO COD ERRO
                throw new Exception(ge.msgError());
                //mensagemErro = "Não foi possível inserir: " + sqlE.getMessage();
            }
            return executou;
        } else {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para limpar os set's desta classe.
     */
    @Override
    public void limpar () {
        setIdVariante(0);
        setNome("");
        setCpf("");
        setCnpj("");
        setTelefoneCelular("");
        setTelefoneFixo("");
        setCep("");
        setRua("");
        setComplemento("");
        setBairro("");
        setDataCadastro(null);
        setNomeFantasia("");
        setTipo("".charAt(0));
    }
    // --------------------------------------------------------------------------------------------------------------- FIM INTERFACE

    // --------------------------------------------------------------------------------------------------------------- INICIO AUXILIARES
    /**
     * Metodo para obter os dados da ultima acao e passa-los para a classe.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    private void setVariante () throws Exception {
        try {
            setIdVariante(getRsDados().getInt("ID_Variante"));
            setNome(getRsDados().getString("Nome"));
            setCpf(getRsDados().getString("CPF"));
            setCnpj(getRsDados().getString("CNPJ"));
            setTelefoneCelular(getRsDados().getString("TelefoneCelular"));
            setTelefoneFixo(getRsDados().getString("TelefoneFixo"));
            setCep(getRsDados().getString("CEP"));
            setRua(getRsDados().getString("Rua"));
            setComplemento(getRsDados().getString("Complemento"));
            setBairro(getRsDados().getString("Bairro"));
            setDataCadastro(getRsDados().getDate("DataCadastro"));
            setNomeFantasia(getRsDados().getString("NomeFantasia"));
            setTipo(getRsDados().getString("Tipo").charAt(0));   
        } catch (SQLException e) {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
            //JOptionPane.showConfirmDialog(null, "Erro inesperdo", "Comunique a Gládio Softwares.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para conferir se os parametros referente ao codigo estao corretos.
     * 
     * Codigos:
     * 0 - Inserir | Editar
     * 1 - Deletar | Pesquisar
     * 
     * @param cod - codigo referente ao metodo utilizado.
     * 
     * @return true  - parametros corretos.
     *         false - parametros incorretos.
     */
    private boolean conferir(int cod) {
        boolean retorno = true;
        switch (cod) {
            case 0:
                if (getIdVariante() < 0) {
                    retorno = false;
                } else {
                    if (getNome() == null || getNome().length() <= 0) {
                        retorno = false;
                    } else {
                        if (!aux.eCpfValido(getCpf())) {
                            retorno = false;
                        } else {
                            if (!aux.eTelCelularValido(getTelefoneCelular())) {
                                retorno = false;
                            } else {
                                if (!aux.eTelFixoValido(getTelefoneFixo())) {
                                    retorno = false;
                                } else {
                                    if (!aux.eCepValido(getCep())) {
                                        retorno = false;
                                    } else {
                                        if (getRua() == null ||
                                            getRua().length() <= 0) {
                                            retorno = false;
                                        } else {
                                            if (getComplemento() == null ||
                                                getComplemento().length() <= 0) {
                                                retorno = false;
                                            } else {
                                                if (getBairro() == null ||
                                                    getBairro().length() <= 0) {
                                                    retorno = false;
                                                } else {
                                                    if (!aux.eDataValida(getDataCadastro())) {
                                                        retorno = false;
                                                    } else {
                                                        if (getTipo() != '?' && getTipo() != '?') {
                                                            retorno = false;
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
                break;
            case 1:
                if (getIdVariante() < 0) {
                    retorno = false;
                }
                break;
            default:
                retorno = false;
                break;
        }
        return retorno;
    }
    // --------------------------------------------------------------------------------------------------------------- FIM AUXILIARES
    
    // --------------------------------------------------------------------------------------------------------------- INICIO SET|GET   
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }  
    
    public ResultSet getRsDados() {
        return rsDados;
    }

    public void setRsDados(ResultSet rsDados) {
        this.rsDados = rsDados;
    }
    // --------------------------------------------------------------------------------------------------------------- FIM SET|GET  
}