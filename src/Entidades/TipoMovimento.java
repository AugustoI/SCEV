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
