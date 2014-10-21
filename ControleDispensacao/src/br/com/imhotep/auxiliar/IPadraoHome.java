/**
 * 
 */
package br.com.imhotep.auxiliar;



/**
 * @author marlonassis
 *
 */
public interface IPadraoHome {
	
	/**
	 * M�todo que persist os dados no banco
	 * @return true se conseguir enviar os dados
	 */
	public abstract boolean enviar();
	/**
	 * M�todo que atualiza os dados no banco 
	 * @return true se conseguir atualizar
	 */
	public boolean atualizar();
	/**
	 * M�todo que apaga os dados no banco
	 * @return true se conseguir apagar
	 */
	abstract boolean apagar();
//	/**
//	 * M�todo que retorna todos os registros do usupario logado
//	 * @return Collection de objetos gen�ricos
//	 */
//	public abstract Collection<T> buscar();
//	/**
//	 * M�todo que retorna os registros de uma consulta criada pelo usu�rio
//	 * @param String arg0
//	 * @return Collection de objetos gen�ricos
//	 */
//	public abstract Collection<T> buscar(String sql);
	
}
