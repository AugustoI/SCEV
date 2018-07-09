/*
 * Interface GladioError
 *
 * Interface utilizada para controle de possiveis erros
 * que possam vir a acontecer durante a executacao das aplicacoes.
 *
 * Versao: 1.0.0
 */
package Interface;

/**
 *
 * @author Couth
 */
public interface GladioError {
    
    public int     codError = 0;
    
    public void    setCodError(int codError);
    
    public int     getCodError();
    
    public boolean hasError();
    
    public String  msgError();
    
    public String  msgErrorByCod(int codError);
}
