/**
 * 
 */
package br.com.Imhotep.auxiliar;



/**
 * @author marlonassis
 *
 */
public interface IPadraoHome {
	
	/**
	 * Método que persist os dados no banco
	 * @return true se conseguir enviar os dados
	 */
	public abstract boolean enviar();
	/**
	 * Método que atualiza os dados no banco 
	 * @return true se conseguir atualizar
	 */
	public boolean atualizar();
	/**
	 * Método que apaga os dados no banco
	 * @return true se conseguir apagar
	 */
	abstract boolean apagar();
//	/**
//	 * Método que retorna todos os registros do usupario logado
//	 * @return Collection de objetos genéricos
//	 */
//	public abstract Collection<T> buscar();
//	/**
//	 * Método que retorna os registros de uma consulta criada pelo usuário
//	 * @param String arg0
//	 * @return Collection de objetos genéricos
//	 */
//	public abstract Collection<T> buscar(String sql);
	
}
