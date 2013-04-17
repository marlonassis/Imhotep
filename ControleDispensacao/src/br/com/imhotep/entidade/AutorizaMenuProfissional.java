package br.com.imhotep.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_autoriza_menu_profissional")
public class AutorizaMenuProfissional {
	private int idAutorizaMenuProfissional;
	private Profissional profissional;
	private Menu menu;
	private Profissional profissionalInsercao;
	private Date dataCriacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_autoriza_menu_profissional_id_autoriza_menu_profissional_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_autoriza_menu_profissional", unique = true, nullable = false)
	public int getIdAutorizaMenuProfissional() {
		return this.idAutorizaMenuProfissional;
	}
	
	public void setIdAutorizaMenuProfissional(int idAutorizaMenuProfissional){
		this.idAutorizaMenuProfissional = idAutorizaMenuProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu")
	public Menu getMenu(){
		return menu;
	}
	
	public void setMenu(Menu menu){
		this.menu = menu;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional(){
		return profissional;
	}
	
	public void setProfissional(Profissional profissional){
		this.profissional = profissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao(){
		return profissionalInsercao;
	}
	
	public void setProfissionalInsercao(Profissional profissionalInsercao){
		this.profissionalInsercao = profissionalInsercao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AutorizaMenuProfissional))
			return false;
		
		return ((AutorizaMenuProfissional)obj).getIdAutorizaMenuProfissional() == this.idAutorizaMenuProfissional;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + profissional.hashCode() + menu.hashCode();
	}

	@Override
	public String toString() {
		return profissional.getNome().concat(" - ").concat(menu.getDescricao());
	}
	
}
