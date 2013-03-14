package br.com.Imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.auxiliar.Parametro;
import br.com.Imhotep.entidade.Chamado;
import br.com.Imhotep.entidade.Profissional;
import br.com.Imhotep.enums.TipoChamadoStatusEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="chamadoRaiz")
@SessionScoped
public class ChamadoRaiz extends PadraoHome<Chamado>{
	
	public boolean abrirChamado(){
		try {
			getInstancia().setProfissionalSolicitante(Autenticador.getInstancia().getProfissionalAtual());
			getInstancia().setDataSolicitacao(new Date());
			getInstancia().setTipoChamadoStatus(TipoChamadoStatusEnum.A);
			getInstancia().setUnidadeSolicitante(Autenticador.getInstancia().getUnidadeAtual());
			getInstancia().setUnidadeReceptora(Parametro.unidadeCPD());
			if(super.enviar()){
				super.novaInstancia();
				return true;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean pegarChamado(Chamado chamado){
		Profissional profissionalAtendimento = new ConsultaGeral<Profissional>().
				consultaUnica(new StringBuilder("select o.profissionalAtendimento from Chamado o where o.idChamado = "+chamado.getIdChamado()));
		if(profissionalAtendimento == null){
			try {
				chamado.setProfissionalAtendimento(Autenticador.getInstancia().getProfissionalAtual());
				chamado.setDataAtendimento(new Date());
				setInstancia(chamado);
				if(super.atualizar()){
					super.novaInstancia();
					return true;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			mensagem("Esste chamado não está mais disponível.", null, Constantes.INFO);
		}
		return false;
	}
	
	
	
}
