/*
 * Classe ClientesDAO
 *
 * Classe utilizada para controle dos clientes, contendo
 * metodos utilitarios referentes ao banco de dados.
 *
 * Versao: 1.0.0
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Couth
 */
public class VariantesEstoqueDAO implements ClasseDAO{
    private int ID_Variante;
    private String Nome;
    private String CPF;
    private String CNPJ;
    private String TelefoneCelular;
    private String TelefoneFixo;
    private String CEP;
    private String Rua;
    private String Complemento;
    private String Bairro;
    private Date DataCadastro;
    private String NomeFantasia;
    private char Tipo;
    
    public ResultSet rsDados;
    public String MensagemErro;
            
    @Override
    public boolean Inserir() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("insert into variantesestoque  "
                    + "(ID_Variante, Nome, CPF, TelefoneCelular, TelefoneFixo, CEP, Rua, Complemento, Bairro, "
                    + "DataCadastro, Tipo) "
                    + "values(?,?,?,?,?,?,?,?,?,str_to_date(?,\"%d/%m/%Y %H:%i:%s\"),?)");
            SQL.setInt(1, this.getID_Variante());
            SQL.setString(2, this.getNome());
            SQL.setString(3, this.getCPF());
            SQL.setString(4, this.getTelefoneCelular());
            SQL.setString(5, this.getTelefoneCelular());
            SQL.setString(6, this.getCEP());
            SQL.setString(7, this.getRua());
            SQL.setString(8, this.getComplemento());
            SQL.setString(9, this.getBairro());
            SQL.setDate(10, (java.sql.Date) this.getDataCadastro());
            SQL.setString(11, String.valueOf(this.getTipo()));
                     
            try {
                SQL.executeUpdate();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = e.getMessage();
            }
            
            if (!Executou){
                MensagemErro = "Não foi possível inserir: " + MensagemErro;
            }

            SQL = con.prepareStatement("select max(ID_Variante) as ID from variantesestoque");
            this.rsDados = SQL.executeQuery();
            
        } catch (SQLException ex) {
            MensagemErro = "Não foi possível inserir: " + ex.getMessage();
        }            
        return Executou;
    }
    
    @Override
    public boolean Editar() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("	UPDATE VariantesEstoque\n" +
                                                         "		SET Nome = ? \n" +
                                                         "		   ,CPF = ? \n" +
                                                         "		   ,CNPJ = ? \n" +
                                                         "		   ,TelefoneCelular = ? \n" +
                                                         "		   ,TelefoneFixo = ? \n" +
                                                         "		   ,CEP = ? \n" +
                                                         "		   ,Rua = ? \n" +
                                                         "		   ,Complemento = ? \n" +
                                                         "		   ,Bairro = ? \n" +
                                                         "		   ,DataCadastro = ? \n" +
                                                         "		   ,NomeFantasia = ? \n" +
                                                         "		   ,Tipo = ? \n" +
                                                         "	WHERE ID_Variante = ? ");
            SQL.setString(1, this.getNome());
            SQL.setString(2, this.getCPF());
            SQL.setString(3, this.getTelefoneCelular());
            SQL.setString(4, this.getTelefoneCelular());
            SQL.setString(5, this.getCEP());
            SQL.setString(6, this.getRua());
            SQL.setString(7, this.getComplemento());
            SQL.setString(8, this.getBairro());
            SQL.setDate(9, (java.sql.Date) this.getDataCadastro());
            SQL.setString(10, String.valueOf(this.getTipo()));
            SQL.setInt(11, this.getID_Variante());                     
            try {
                SQL.executeUpdate();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = e.getMessage();
            }
            
            if (!Executou){
                MensagemErro = "Não foi possível editar: " + MensagemErro;
            }

            SQL = con.prepareStatement("select * from variantesestoque where ID_Variante = ?");
            SQL.setInt(1, this.getID_Variante());
            this.rsDados = SQL.executeQuery();
            SetVariante();            
        } catch (SQLException ex) {
            MensagemErro = "Não foi possível editar: " + ex.getMessage();
        }            
        return Executou;
    }
    
    private void SetVariante () {
        try {
            this.setID_Variante(rsDados.getInt("ID_Variante"));
            this.setNome(rsDados.getString("Nome"));
            this.setCPF(rsDados.getString("CPF"));
            this.setCNPJ(rsDados.getString("CNPJ"));
            this.setTelefoneCelular(rsDados.getString("TelefoneCelular"));
            this.setTelefoneFixo(rsDados.getString("TelefoneFixo"));
            this.setCEP(rsDados.getString("CEP"));
            this.setRua(rsDados.getString("Rua"));
            this.setComplemento(rsDados.getString("Complemento"));
            this.setBairro(rsDados.getString("Bairro"));
            this.setDataCadastro(rsDados.getDate("DataCadastro"));
            this.setNomeFantasia(rsDados.getString("NomeFantasia"));
            this.setTipo(rsDados.getString("Tipo").charAt(0));   
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Erro inesperdo", "Comunique a Gládio Softwares.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void Limpar () {
        this.setID_Variante(0);
        this.setNome("");
        this.setCPF("");
        this.setCNPJ("");
        this.setTelefoneCelular("");
        this.setTelefoneFixo("");
        this.setCEP("");
        this.setRua("");
        this.setComplemento("");
        this.setBairro("");
        this.setDataCadastro(null);
        this.setNomeFantasia("");
        this.setTipo("".charAt(0));
    }
    
    @Override
    public boolean Pesquisar() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where ID_Variante = ?");   
            SQL.setInt(1, this.getID_Variante());
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
                SetVariante();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
    /**
     *
     * @param Tipo
     * @return
     */
    
    public boolean Pesquisar(char Tipo) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where Tipo = '?'");   
            SQL.setString(1, String.valueOf(this.getTipo()));
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
    public boolean Pesquisar(String Nome) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where Nome like '?%'");   
            SQL.setString(1, this.getNome());
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
     public boolean Pesquisar(int CPF) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where CPF = '?'");   
            SQL.setString(1, this.getCPF());
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
    public boolean Pesquisar(Date DataInicial, Date DataFinal) {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("select * from variantesestoque where DataCadastro between ? and ? ");   
            SQL.setDate(1, (java.sql.Date) DataInicial);
            SQL.setDate(2, (java.sql.Date) DataFinal);
            
            try {
                rsDados = SQL.executeQuery();
                Executou = rsDados.next();
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a busca dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a busca dos dados: " + ex.getMessage();
        }
        
        return Executou;
    }
    
    @Override
    public boolean Deletar() {
        Connection con;
        MensagemErro = "";
        boolean Executou = true;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement SQL = con.prepareStatement("delete from variantesestoque where ID_Variante = ?");   
            SQL.setInt(1, this.ID_Variante);
            
            try {
                SQL.executeUpdate();
                rsDados = null;
                Executou = true;
            } catch (SQLException e) {
                Executou = false;
                MensagemErro = "Erro ao realizar a exclusão dos dados: " + e.getMessage();
            }                       
            
        } catch (SQLException ex) {
            MensagemErro = "Erro ao realizar a exclusão dos dados: " + ex.getMessage();
        }
        
        Limpar();
        return Executou;
    }

    public int getID_Variante() {
        return ID_Variante;
    }

    public void setID_Variante(int ID_Variante) {
        this.ID_Variante = ID_Variante;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getTelefoneCelular() {
        return TelefoneCelular;
    }

    public void setTelefoneCelular(String TelefoneCelular) {
        this.TelefoneCelular = TelefoneCelular;
    }

    public String getTelefoneFixo() {
        return TelefoneFixo;
    }

    public void setTelefoneFixo(String TelefoneFixo) {
        this.TelefoneFixo = TelefoneFixo;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getRua() {
        return Rua;
    }

    public void setRua(String Rua) {
        this.Rua = Rua;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String Complemento) {
        this.Complemento = Complemento;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String Bairro) {
        this.Bairro = Bairro;
    }

    public Date getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Date DataCadastro) {
        this.DataCadastro = DataCadastro;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String NomeFantasia) {
        this.NomeFantasia = NomeFantasia;
    }

    public char getTipo() {
        return Tipo;
    }

    public void setTipo(char Tipo) {
        this.Tipo = Tipo;
    }    
}
