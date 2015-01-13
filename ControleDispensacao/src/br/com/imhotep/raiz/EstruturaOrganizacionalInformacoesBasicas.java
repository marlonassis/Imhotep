package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.EstruturaOrganizacionalEmail;
import br.com.imhotep.entidade.EstruturaOrganizacionalTelefone;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstruturaOrganizacionalInformacoesBasicas extends PadraoRaiz<EstruturaOrganizacional>{
	private List<EstruturaOrganizacionalEmail> emails = new ArrayList<EstruturaOrganizacionalEmail>();
	private List<EstruturaOrganizacionalTelefone> telefones = new ArrayList<EstruturaOrganizacionalTelefone>();
	private EstruturaOrganizacionalEmail email = new EstruturaOrganizacionalEmail();
	private EstruturaOrganizacionalTelefone telefone = new EstruturaOrganizacionalTelefone();
	
	public void limparEmail(){
		setEmail(new EstruturaOrganizacionalEmail());
	}
	
	public void limparTelefone(){
		setTelefone(new EstruturaOrganizacionalTelefone());
	}
	
	public void carregarEstruturaBasica(EstruturaOrganizacional estruturaOrganizacional){
		setInstancia(estruturaOrganizacional);
		atualizarTelefones();
		atualizarEmails();
	}
	
	public void atualizarEmail(){
		if(super.atualizarGenerico(getEmail()) != null){
			atualizarEmails();
		}
	}
	
	public void atualizarTelefone(){
		if(super.atualizarGenerico(getTelefone()) != null){
			atualizarTelefones();
		}
	}

	public void cadastrarEmail(){
		getEmail().setEstruturaOrganizacional(getInstancia());
		if(super.enviarGenerico(getEmail())){
			atualizarEmails();
			setEmail(new EstruturaOrganizacionalEmail());
		}
	}
	
	public void cadastrarTelefone(){
		getTelefone().setEstruturaOrganizacional(getInstancia());
		if(super.enviarGenerico(getTelefone())){
			atualizarTelefones();
			setTelefone(new EstruturaOrganizacionalTelefone());
		}
	}
	
	public void apagarEmail(){
		if(super.apagarGenerico(getEmail())){
			atualizarEmails();
		}
	}
	
	public void apagarTelefone(){
		if(super.apagarGenerico(getTelefone())){
			atualizarTelefones();
		}
	}
	
	private void atualizarTelefones(){
		setTelefones(carregarTelefones());
	}
	
	private void atualizarEmails(){
		setEmails(carregarEmails());
	}
	
	private List<EstruturaOrganizacionalTelefone> carregarTelefones(){
		List<EstruturaOrganizacionalTelefone> telefones = new ArrayList<EstruturaOrganizacionalTelefone>();
		int id = getInstancia().getIdEstruturaOrganizacional();
		String sql = "select * from administrativo.tb_estrutura_organizacional_telefone where id_estrutura_organizacional = "+id+" order by cv_telefone";
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				EstruturaOrganizacionalTelefone telefone = new EstruturaOrganizacionalTelefone();
				telefone.setIdEstruturaOrganizacionalTelefone(rs.getInt("id_estrutura_organizacional_telefone"));
				telefone.setTelefone(rs.getString("cv_telefone"));
				telefone.setEstruturaOrganizacional(getInstancia());
				telefones.add(telefone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return telefones;
	}
	
	private List<EstruturaOrganizacionalEmail> carregarEmails(){
		List<EstruturaOrganizacionalEmail> emails = new ArrayList<EstruturaOrganizacionalEmail>();
		int id = getInstancia().getIdEstruturaOrganizacional();
		String sql = "select * from administrativo.tb_estrutura_organizacional_email where id_estrutura_organizacional = "+id+" order by cv_email";
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				EstruturaOrganizacionalEmail email = new EstruturaOrganizacionalEmail();
				email.setIdEstruturaOrganizacionalEmail(rs.getInt("id_estrutura_organizacional_email"));
				email.setEmail(rs.getString("cv_email"));
				email.setEstruturaOrganizacional(getInstancia());
				emails.add(email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emails;
	}
	
	public List<EstruturaOrganizacionalEmail> getEmails() {
		return emails;
	}
	public void setEmails(List<EstruturaOrganizacionalEmail> emails) {
		this.emails = emails;
	}
	public List<EstruturaOrganizacionalTelefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<EstruturaOrganizacionalTelefone> telefones) {
		this.telefones = telefones;
	}

	public EstruturaOrganizacionalEmail getEmail() {
		return email;
	}

	public void setEmail(EstruturaOrganizacionalEmail email) {
		this.email = email;
	}

	public EstruturaOrganizacionalTelefone getTelefone() {
		return telefone;
	}

	public void setTelefone(EstruturaOrganizacionalTelefone telefone) {
		this.telefone = telefone;
	}
}
