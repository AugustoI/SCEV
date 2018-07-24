/*
 * Classe MovimentosEstoqueDAO
 *
 * Classe utilizada para controle dos movimentos do estoque.
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
public class MovimentosEstoqueDAO implements ClasseDAO {
    
    /*
    * Atributos da classe
    */
    private int idMovimento;
    private Date dataMovimento;
    private int idTipoMovimento;
    private int idVariante;
    
    private final GladioError ge = new GladioError();
    private final Auxiliares aux = new Auxiliares();
    
    public ResultSet rsDados;
        
    // --------------------------------------------------------------------------------------------------------------- INICIO INTERFACE
    /**
     * Metodo para inserir um novo dado na tabela movimentosestoque.
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
                ResultSet rs = obterProximoIdMovimentoEstoque();
                if (rs == null) {
                    ge.setCodError(0); // NUMERO COD ERRO
                    throw new Exception(ge.msgError());
                } else {
                    Connection con = new ConexaoDAO().conectar();
                    PreparedStatement SQL = con.prepareStatement("insert into MovimentosEstoque "
                        + "(ID_Movimento, DataMovimento, ID_TipoMovimento, ID_Variante) "
                        + "values(?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?,?))");
                    int id = rs.getInt(1);
                    setIdMovimento(id);
                    SQL.setInt(1, getIdMovimento());
                    SQL.setDate(2, (java.sql.Date) getDataMovimento());
                    SQL.setInt(3, getIdTipoMovimento());
                    SQL.setInt(4, getIdVariante());
                    SQL.executeUpdate();

                    SQL = con.prepareStatement("select * from MovimentosEstoque where ID_Movimento = ?");
                    SQL.setInt(1, getIdMovimento());
                    setRsDados(SQL.executeQuery());
                    executou = getRsDados().next();
                    
                    setMovimento();
                }
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
     * Metodo para editar um dado ja existente na tabela movimentosestoque.
     * 
     * @return true  - edicao efetuada com sucesso.
     *         false - erro na edicao.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean editar() throws Exception {
        if (conferir(1)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("UPDATE movimentosestoque "
                        + "SET DataMovimento = ?, "
                        + "ID_Variante = ? "
                        + "WHERE ID_Movimento = ?");
                SQL.setDate(1, (java.sql.Date) getDataMovimento());
                SQL.setInt(2, getIdVariante());
                SQL.setInt(3, getIdMovimento());
                SQL.executeUpdate();

                SQL = con.prepareStatement("select * from MovimentosEstoque where ID_Movimento = ?");
                SQL.setInt(1, getIdMovimento());
                setRsDados(SQL.executeQuery());
                executou = getRsDados().next();
                
                setMovimento();
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
     * Metodo para excluir um dado ja existente na tabela movimentosestoque.
     * 
     * @return true  - exclusao efetuada com sucesso.
     *         false - erro na exclusao.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean deletar() throws Exception {
        if (conferir(2)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("delete from movimentosestoque where ID_Movimento = ?");
                SQL.setInt(1, getIdMovimento());
                SQL.executeUpdate();
                setRsDados(null);
                executou = true;
                
                limpar();
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
        //JOptionPane.showMessageDialog(null, "Atenção", "Permitir a exclusão do movimento pode resultar em erros!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Metodo para pesquisar um dado ja existente na tabela movimentosestoque a
     * partir do seu id.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean pesquisar() throws Exception {
        if (conferir(3)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from movimentosestoque where ID_Movimento = ?");
                SQL.setInt(1, getIdMovimento());
                setRsDados(SQL.executeQuery());
                executou = getRsDados().next();
                
                setMovimento();
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
     * Metodo para pesquisar um dado ja existente na tabela movimentosestoque a
     * partir do id do seu variante.
     * 
     * @param idVariante - id a ser pesquisada.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    public boolean pesquisar(int idVariante) throws Exception {
        if (getIdVariante() < 0) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from movimentosestoque where ID_Variante = ?");
                SQL.setInt(1, getIdVariante());
                setRsDados(SQL.executeQuery());
                executou = getRsDados().next();
                
                setMovimento();
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
     * Metodo para pesquisar dados da tabela movimentosestoque dentro de um periodo.
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
                PreparedStatement SQL = con.prepareStatement("select * from movimentosestoque where DataMovimento between ? and ?");   
                SQL.setDate(1, (java.sql.Date) dataInicial);
                SQL.setDate(2, (java.sql.Date) dataFinal);
                setRsDados(SQL.executeQuery());
                executou = getRsDados().next();
                
                setMovimento();
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
        setDataMovimento(null);
        setIdMovimento(0);
        setIdTipoMovimento(0);
    }
    // --------------------------------------------------------------------------------------------------------------- FIM INTERFACE

    // --------------------------------------------------------------------------------------------------------------- INICIO AUXILIARES
    /**
     * Metodo para obter os dados da ultima acao e passa-los para a classe.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    private void setMovimento () throws Exception {
        try {
            setIdVariante(getRsDados().getInt("ID_Variante"));
            setDataMovimento(getRsDados().getDate("DataMovimento"));
            setIdMovimento(getRsDados().getInt("ID_Movimento"));
            setIdTipoMovimento(getRsDados().getInt("ID_TipoMovimento"));
        } catch (SQLException ex) {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
        }
    }
    
    /**
     * Metodo para obter o proximo id da tabela movimentosestoque.
     * 
     * @return resultado obtido na pesquisa.
     * 
     * @throws java.lang.SQLException - Erro referente ao banco de dados.
     */
    private ResultSet obterProximoIdMovimentoEstoque() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select max(ID_Movimento) + 1 as ID from MovimentosEstoque");     
        ResultSet rs = SQL.executeQuery();
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }
    
    /**
     * Metodo para conferir se os parametros referente ao codigo estao corretos.
     * 
     * Codigos:
     * 0 - Inserir
     * 1 - Editar
     * 2 - Deletar | Pesquisar
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
                if (getIdMovimento() < 0) {
                    retorno = false;
                } else {
                    if (!aux.eDataValida(getDataMovimento())) {
                        retorno = false;
                    } else {
                        if (getIdTipoMovimento() < 0) {
                            retorno = false;
                        } else {
                            if (getIdVariante() < 0) {
                                retorno = false;
                            }
                        }
                    }
                }
                break;
            case 1:
                if (!aux.eDataValida(getDataMovimento())) {
                    retorno = false;
                } else {
                    if (getIdVariante()< 0) {
                        retorno = false;
                    } else {
                        if (getIdMovimento()< 0) {
                            retorno = false;
                        }
                    }
                }
                break;
            case 2:
                if (getIdMovimento() < 0) {
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
    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public Date getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(Date dataMovimento) {
        this.dataMovimento = dataMovimento;
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
    
    public ResultSet getRsDados() {
        return rsDados;
    }

    public void setRsDados(ResultSet rsDados) {
        this.rsDados = rsDados;
    }
    // --------------------------------------------------------------------------------------------------------------- FIM SET|GET
}
