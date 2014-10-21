/**
 * 
 */
package br.com.remendo.interfaces;



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
	 * M�todo que atualiza os dados no banco 
	 * @return true se conseguir atualizar
	 */
	public Object atualizarGenerico(Object obj);
	
	/**
	 * M�todo que apaga os dados no banco
	 * @return true se conseguir apagar
	 */
	abstract boolean apagar();
	
	/**
	 * M�todo que executa um hql de para delete ou update sem objeto relacionado.
	 * @return n�mero de registros alterados ou null caso tenha ocorrido algum erro
	 */
	abstract Integer executa(String sql);
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
