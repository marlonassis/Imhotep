package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "unidade_has_profissional")
public class AutorizaUnidadeProfissional {
	private int idAutorizaUnidadeProfissional;
	private Unidade unidade;
	private Profissional profissional;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	
	@Id
	@GeneratedValue
	@Column(name = "id_unidade_has_profissional")
	public int getIdAutorizaUnidadeProfissional() {
		return idAutorizaUnidadeProfissional;
	}
	public void setIdAutorizaUnidadeProfissional(
			int idAutorizaUnidadeProfissional) {
		this.idAutorizaUnidadeProfissional = idAutorizaUnidadeProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unidade_id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profissional_id_profissional")
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_incl", length = 13)
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_incl")
	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}
	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AutorizaUnidadeProfissional))
			return false;
		
		return ((AutorizaUnidadeProfissional)obj).getIdAutorizaUnidadeProfissional() == this.idAutorizaUnidadeProfissional;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 +unidade.getNome().hashCode();
	}

	@Override
	public String toString() {
		return profissional.getNome().concat(" - ").concat(unidade.getNome());
	}
}
