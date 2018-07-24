/*
 * Classe ProdutosMovimentosDAO
 *
 * Classe utilizada para controle dos movimentos dos produtos.
 *
 * Contem metodos de manipulacao do banco de dados.
 *
 * Versao: 1.0.0
 */
package DAO;

import Entidades.GladioError;
import Interface.ClasseDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER | Couth
 */
public class ProdutosMovimentosDAO implements ClasseDAO {
    
    /*
    * Atributos da classe
    */
    private int idProdutoMovimento;
    private int idMovimento;
    private int idProduto;
    private double quantidade;
    
    private final GladioError ge = new GladioError();

    public ResultSet rsDados;
    
    // --------------------------------------------------------------------------------------------------------------- INICIO INTERFACE
    /**
     * Metodo para inserir um novo dado na tabela produtosmovimentos.
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
                PreparedStatement SQL = con.prepareStatement("insert into produtosmovimentos "
                    + "(ID_Movimento,ID_Produto,Quantidade) "
                    + "values(?,?,?)");
                SQL.setInt(1, getIdMovimento());
                SQL.setInt(2, getIdProduto());
                SQL.setDouble(3, getQuantidade());
                SQL.executeUpdate();

                ResultSet rs = getUltimoIdProdutoMovimento();
                if (rs == null) {
                    ge.setCodError(0); // NUMERO COD ERRO
                    throw new Exception(ge.msgError());
                } else {
                    int id = rs.getInt(1);
                    setIdProdutoMovimento(id);
                    SQL = con.prepareStatement("select * from produtosmovimentos where ID_ProdutoMovimento = ?");
                    SQL.setInt(1, getIdProdutoMovimento());
                    setRsDados(SQL.executeQuery());
                    executou = getRsDados().next();
                    
                    setProdutoMovimento();
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
     * Metodo para insercao de dados na tabela produtosmovimentos a partir de um
     * resultset
     * 
     * @param rsInserir - resultset contendo os dados a serem inseridos.
     * 
     * @return true  - insercao efetuada com sucesso.
     *         false - erro na insercao.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    public boolean inserir(ResultSet rsInserir) throws Exception {
        if (rsInserir != null) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL;
                String statement = "insert into produtosmovimentos (ID_Movimento,ID_Produto,Quantidade) values ";
                while (rsInserir.next()) {                    
                    SQL = con.prepareStatement(statement + 
                            " (" + rsInserir.getInt(1) +
                            ", " + rsInserir.getInt(2) +
                            ", " + rsInserir.getDouble(3) + ")");
                    
                    SQL.executeUpdate();
                    
                    limpar();
                }
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
     * Metodo para edicao de dados na tabela produtosmovimentos.
     * 
     * ATENCAO: A tabela produtosmovimentos nao suporta edicao de dados.
     * 
     * @return false.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean editar() throws Exception {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo para exclusao de dados na tabela produtosmovimentos.
     * 
     * ATENCAO: A tabela produtosmovimentos nao suporta exclusao de dados.
     * 
     * @return false.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    @Override
    public boolean deletar() throws Exception {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo para pesquisar um dado ja existente na tabela produtosmovimentos a
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
                PreparedStatement SQL = con.prepareStatement("select * from produtosmovimentos where ID_Movimento = ?");
                SQL.setInt(1, getIdMovimento());
                setRsDados(SQL.executeQuery());
                executou = getRsDados().next();
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
     * Metodo para pesquisar um dado ja existente na tabela produtosmovimentos a
     * partir do seu id.
     * 
     * @param idProduto - id do produto a ser pesquisado.
     * 
     * @return true  - pesquisa efetuada com sucesso.
     *         false - erro na pesquisa.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    public boolean pesquisar(int idProduto) throws Exception {
        if (conferir(2)) {
            boolean executou = false;
            try {
                Connection con = new ConexaoDAO().conectar();
                PreparedStatement SQL = con.prepareStatement("select * from produtosmovimentos where ID_Produto = ?");
                SQL.setInt(1, getIdProduto());
                setRsDados(SQL.executeQuery());
                executou = getRsDados().next();
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
    public void limpar() {
        setIdMovimento(0);
        setIdProduto(0);
        setIdProdutoMovimento(0);
        setQuantidade(0.0);
    }
    // --------------------------------------------------------------------------------------------------------------- FIM INTERFACE
    
    // --------------------------------------------------------------------------------------------------------------- INICIO AUXILIARES    
    /**
     * Metodo para obter os dados da ultima acao e passa-los para a classe.
     * 
     * @throws java.lang.Exception - Mensagem referente ao codigo de erro.
     */
    private void setProdutoMovimento() throws Exception {
        try {
            setIdMovimento(getRsDados().getInt("ID_Movimento"));
            setIdProduto(getRsDados().getInt("ID_Produto"));
            setIdProdutoMovimento(getRsDados().getInt("ID_ProdutoMovimento"));
            setQuantidade(getRsDados().getDouble("Quantidade"));
        } catch (SQLException e) {
            ge.setCodError(0); // NUMERO COD ERRO
            throw new Exception(ge.msgError());
            //JOptionPane.showConfirmDialog(null, "Erro inesperdo", "Comunique a Gládio Softwares.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Metodo para obter o proximo id da tabela produtosmovimentos.
     * 
     * @return resultado obtido na pesquisa.
     * 
     * @throws java.lang.SQLException - Erro referente ao banco de dados.
     */
    private ResultSet getUltimoIdProdutoMovimento() throws SQLException {
        Connection con = new ConexaoDAO().conectar();
        PreparedStatement SQL = con.prepareStatement("select ID_ProdutoMovimento from produtosmovimentos " +
            "where ID_ProdutoMovimento in (select max(ID_ProdutoMovimento) from produtosmovimentos)");     
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
     * 0 - Inserir | Inserir(ResultSet)
     * 1 - Pesquisar
     * 2 - PesquisarIdProduto
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
                    if (getIdProduto() < 0) {
                        retorno = false;
                    } else {
                        if (getQuantidade() < 0.0) {
                            retorno = false;
                        }
                    }
                }
                break;
            case 1:
                if (getIdMovimento() < 0) {
                    retorno = false;
                }
                break;
            case 2:
                if (getIdProduto() < 0) {
                    retorno = false;
                }
                break;
            default:
                retorno = false;
                break;
        }
        return retorno;
    }
    // --------------------------------------------------------------------------------------------------------------- FIMM AUXILIARES
    
    // --------------------------------------------------------------------------------------------------------------- INICIO SET|GET
    public int getIdProdutoMovimento() {
        return idProdutoMovimento;
    }

    public void setIdProdutoMovimento(int idProdutoMovimento) {
        this.idProdutoMovimento = idProdutoMovimento;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    
    public ResultSet getRsDados() {
        return rsDados;
    }

    public void setRsDados(ResultSet rsDados) {
        this.rsDados = rsDados;
    }
    // --------------------------------------------------------------------------------------------------------------- INICIO SET|GET
}
