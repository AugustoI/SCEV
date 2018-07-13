package Entidades;

import DAO.ConexaoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gustavo
 */
public class TipoMovimento {
    private int ID_TipoMovimento;
    private String NomeMovimento;
    private String DebitoCredito;
    
    public void BuscarTipo(int ID){
        try {
            Connection con;
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlBusca = con.prepareStatement("SELECT * FROM TiposMovimentos WHERE ID_TipoMovimento = ?");
            sqlBusca.setInt(1, ID);
            ResultSet rsSelect = sqlBusca.executeQuery();
            setID_TipoMovimento(rsSelect.getInt("ID_TipoMovimento"));
            setNomeMovimento(rsSelect.getString("NomeMovimento"));
            setDebitoCredito(rsSelect.getString("DebitoCredito"));
            sqlBusca.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro inesperado - Comunique a Gládio: " + ex.getMessage()
                    , "Erro", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    public void InserirTipoMovimento(){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlInsert = con.prepareStatement(" insert into TiposMovimentos  ( "
                    + " NomeMovimento, DebitoCredito) values (?, ?) ");
            sqlInsert.setString(1, this.getNomeMovimento());
            sqlInsert.setString(2, this.getDebitoCredito());
            
            int executeUpdate = sqlInsert.executeUpdate();
            JOptionPane.showMessageDialog( null , "Tipo de Movimento inserido." , "Inserir" , JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()
                        ,"Erro inesperado ao tentar inserir - Comunique a Gládio - " + ex.getMessage() 
                        ,JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void EditarTipoMovimento(){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlEdit = con.prepareStatement(" update TiposMovimentos  ( "
                    + " set NomeMovimento = ? "
                    + "    ,DebitoCredito = ? ");
            
            sqlEdit.setString(1, this.getNomeMovimento());
            sqlEdit.setString(2, this.getDebitoCredito());
            
            int iConfirm = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja editar esse tipo de movimento? "
                    ,"Editar",JOptionPane.YES_NO_CANCEL_OPTION);
            if (iConfirm == JOptionPane.YES_OPTION) {
                int executeUpdate = sqlEdit.executeUpdate();
                JOptionPane.showMessageDialog( null , "Edição concluida." , "Edição" , JOptionPane.INFORMATION_MESSAGE);                 
            }else {
                sqlEdit.close();
                JOptionPane.showMessageDialog( null , "Edição cancelada." , "Cancelar" , JOptionPane.WARNING_MESSAGE);                 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()
                        ,"Erro inesperado ao tentar editar - Comunique a Gládio - " + ex.getMessage() 
                        ,JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void DeletarProduto (){
        Connection con;
        try {
            con = new ConexaoDAO().conectar();
            PreparedStatement sqlConsistencia = con.prepareStatement("select isnull(count(1),0) from MovimentosEstoque ID_TipoMovimento = ?");
            
            sqlConsistencia.setInt(1, this.getID_TipoMovimento());            
            ResultSet rsConsistencia = sqlConsistencia.executeQuery();            
            if (rsConsistencia.getInt(1) > 0){
                JOptionPane.showMessageDialog(null, "Existem lançamentos realizados com esse tipo de movimento. "
                        + "Não é foi possível deletar!"
                        , "Erro", JOptionPane.ERROR_MESSAGE);
            }else{
                PreparedStatement sqlDelete = con.prepareStatement("delete from TiposMovimentos where ID_TipoMovimento = ?");
                sqlDelete.setInt(1, this.getID_TipoMovimento());
                int i = sqlDelete.executeUpdate();
                JOptionPane.showMessageDialog(null, "O tipo de movimento foi deletado com sucesso!"
                        , "Deletar", JOptionPane.INFORMATION_MESSAGE);
            }
            rsConsistencia.close();            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()
                        ,"Erro inesperado ao tentar deletar produto - Comunique a Gládio - " + ex.getMessage() 
                        ,JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getID_TipoMovimento() {
        return ID_TipoMovimento;
    }

    public void setID_TipoMovimento(int ID_TipoMovimento) {
        this.ID_TipoMovimento = ID_TipoMovimento;
    }

    public String getNomeMovimento() {
        return NomeMovimento;
    }

    public void setNomeMovimento(String NomeMovimento) {
        this.NomeMovimento = NomeMovimento;
    }

    public String getDebitoCredito() {
        return DebitoCredito;
    }

    public void setDebitoCredito(String DebitoCredito) {
        this.DebitoCredito = DebitoCredito;
    }
}
