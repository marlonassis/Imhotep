package br.com.imhotep.auxiliar;


import java.util.Arrays;

import org.primefaces.model.TreeNode;


/**
 * @author marlonassis
 *
 */
public class DefaultTreeNodeArray{
	
	public TreeNode[] addElemento(TreeNode[] array, TreeNode elemento) {
		if(array==null){
			return new TreeNode[] {elemento};
		}
		TreeNode[] result = Arrays.copyOf(array, array==null ? 1 : array.length+1);
	    result[array.length] = elemento;
	    return result;
	}

}
