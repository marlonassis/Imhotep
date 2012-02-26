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
@Table(name = "tb_autoriza_unidade_profissional")
public class AutorizaUnidadeProfissional {
	private int idAutorizaUnidadeProfissional;
	private Unidade unidade;
	private Profissional profissional;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	
	@Id
	@GeneratedValue
	@Column(name = "id_autoriza_unidade_profissional")
	public int getIdAutorizaUnidadeProfissional() {
		return idAutorizaUnidadeProfissional;
	}
	public void setIdAutorizaUnidadeProfissional(
			int idAutorizaUnidadeProfissional) {
		this.idAutorizaUnidadeProfissional = idAutorizaUnidadeProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_inclusao")
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
