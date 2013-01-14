package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Doacao;
import br.com.ControleDispensacao.entidade.Hospital;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.enums.TipoOperacaoEnum;
import br.com.remendo.PadraoHome;

@ManagedBean(name="doacaoRaiz")
@SessionScoped
public class DoacaoRaiz extends PadraoHome<Doacao>{
	
	public DoacaoRaiz(Date dataDoacao, Hospital hospital, Material material, Integer quantidade, TipoOperacaoEnum tipoEntrada) {
		getInstancia().setDataDoacao(dataDoacao);
		getInstancia().setHospital(hospital);
		getInstancia().setMaterial(material);
		getInstancia().setQuantidade(quantidade);
		getInstancia().setTipoEntrada(tipoEntrada);
	}
	
}