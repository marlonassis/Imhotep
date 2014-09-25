package br.com.imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.enums.TipoBloqueioLoteEnum;
import br.com.remendo.PadraoRaiz;

//TODO TAF 5
/**
 * Criada por Asclepíades Neto 
 * Data: 18/09/2014
 * Funcionalidade: Sinal alertando a validade do produto
 * XHTML: /PaginasWeb/Painel/Paineis/painelMateriaisAlmoxarifadoVencido.xhtml
 */

@ManagedBean
@SessionScoped
public class BloqueioLoteAlmoxarifadoRaiz extends PadraoRaiz<EstoqueAlmoxarifado> {
	private boolean loteEncontrado;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
	
	public void carregarEstoqueConsultaMaterial(EstoqueAlmoxarifado estoque){
		setLoteEncontrado(true);
		setInstancia(estoque);
	}
	
	public int materialVencido(Date validade){
		if(validade != null){
			Calendar hoje = Calendar.getInstance();
			Calendar vali = Calendar.getInstance();

			vali.setTime(new Utilitarios().ajustarUltimaHoraDia(validade));
			
			if(vali.before(hoje)){
				// o material está vencido
				return 0;
			}
	        else{
	        	hoje.set(Calendar.MONTH, hoje.get(Calendar.MONTH) + 1);
	            if(vali.before(hoje)){
	            	// o material vence no prazo de trê mes
	    		    return 1;
			    }
	            else{
	            	hoje.set(Calendar.MONTH, hoje.get(Calendar.MONTH) + 2);
	                if(vali.before(hoje)){
	                	// o material vence no prazo de seis mes
	        	        return 2;
			        }
	                return 3;
	            }
	        }
		}
		//TODO se a data é nula, logo ele não vence?
		return 3;
	}

	public void bloqueioAutomaticoEstoque(){
		getInstancia().setBloqueado(true);
		getInstancia().setTipoBloqueio(TipoBloqueioLoteEnum.V);
		atualizar();
	}
	
	public boolean isLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}
	

	
	
}
