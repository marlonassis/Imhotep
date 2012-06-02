package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidadeExtra.Ajuda;

@ManagedBean(name="ajudaHome")
@SessionScoped
public class AjudaHome{
	
	private List<Ajuda> ajudaList = new ArrayList<Ajuda>();
	private String nomeMenuConsulta;
	private final String URL_TEMPLATE = "/ControleDispensacao/PaginasWeb/Ajuda/Template/";
	
	public List<Ajuda> getListaAjuda(){
		ajudaList = new ArrayList<Ajuda>();
		Ajuda ajuda = new Ajuda();
		ajuda.setIdAjuda(1);
		ajuda.setNomeMenu("Cadastro de Material");
		ajuda.setTemplate(URL_TEMPLATE + "ajudaCadastroMaterial.jsf");
		ajudaList.add(ajuda);
		return ajudaList;
	}
	
	public String getNomeMenuConsulta() {
		return nomeMenuConsulta;
	}

	public void setNomeMenuConsulta(String nomeMenuConsulta) {
		this.nomeMenuConsulta = nomeMenuConsulta;
	}
	
}
