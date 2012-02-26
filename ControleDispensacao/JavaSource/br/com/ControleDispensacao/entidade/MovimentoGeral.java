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
@Table(name = "tb_movimento_geral")
public class MovimentoGeral {
	private int idMovimentoGeral;
	private TipoMovimento tipoMovimento;
	private Unidade unidade;
	private Prescricao prescricao;
	private String numeroDocumento;
	private String motivo;
	private String controle;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private String numeroControle;
	
	@Id
	@GeneratedValue
	@Column(name = "id_movimento_geral")
	public int getIdMovimentoGeral() {
		return idMovimentoGeral;
	}
	public void setIdMovimentoGeral(int idMovimentoGeral) {
		this.idMovimentoGeral = idMovimentoGeral;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_movimento")
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_inclusao")
	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}
	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
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
	@JoinColumn(name = "id_prescricao")
	public Prescricao getPrescricao() {
		return prescricao;
	}
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	@Column(name = "ds_numero_controle")
	public String getNumeroControle() {
		return numeroControle;
	}
	public void setNumeroControle(String numeroControle) {
		this.numeroControle = numeroControle;
	}
	
	@Column(name = "ds_numero_documento", length = 15)
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@Column(name = "ds_motivo", length = 120)
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	@Column(name = "ds_controle", length = 128)
	public String getControle() {
		return controle;
	}
	public void setControle(String controle) {
		this.controle = controle;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof MovimentoGeral))
			return false;
		
		return ((MovimentoGeral)obj).getIdMovimentoGeral() == this.idMovimentoGeral;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + usuarioInclusao.getLogin().hashCode();
	}

	@Override
	public String toString() {
		return "Movimento Geral";
	}

}
