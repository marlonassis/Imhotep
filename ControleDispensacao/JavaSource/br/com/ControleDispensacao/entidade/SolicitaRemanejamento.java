package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;

@Entity
@Table(name = "tb_solicita_remanejamento")
public class SolicitaRemanejamento {
	private int idSolicitaRemanejamento;
	private Unidade unidadeSolicitada;
	private Unidade unidadeSolicitante;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private TipoSituacaoEnum status;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_solicita_remanejamento_id_solicita_remanejamento_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_solicita_remanejamento", unique = true, nullable = false)
	public int getIdSolicitaRemanejamento() {
		return idSolicitaRemanejamento;
	}
	public void setIdSolicitaRemanejamento(int idSolicitaRemanejamento) {
		this.idSolicitaRemanejamento = idSolicitaRemanejamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unid_solicitada")
	public Unidade getUnidadeSolicitada() {
		return unidadeSolicitada;
	}
	public void setUnidadeSolicitada(Unidade unidadeSolicitada) {
		this.unidadeSolicitada = unidadeSolicitada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unid_solicitante")
	public Unidade getUnidadeSolicitante() {
		return unidadeSolicitante;
	}
	public void setUnidadeSolicitante(Unidade unidadeSolicitante) {
		this.unidadeSolicitante = unidadeSolicitante;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_incl")
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
	
	@Column(name = "status_2")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return status;
	}
	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Usuario))
			return false;
		
		return ((SolicitaRemanejamento)obj).getIdSolicitaRemanejamento() == this.idSolicitaRemanejamento;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + unidadeSolicitada.getNome().concat(dataInclusao.toString()) .hashCode();
	}

	@Override
	public String toString() {
		return unidadeSolicitante.getNome().concat(" - ").concat(unidadeSolicitada.getNome());
	}
}
