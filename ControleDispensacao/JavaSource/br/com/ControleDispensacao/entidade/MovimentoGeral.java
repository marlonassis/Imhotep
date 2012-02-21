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
@Table(name = "movto_geral")
public class MovimentoGeral {
	private int idMovimentoGeral;
	private TipoMovimento tipoMovimento;
	private TipoMovimento tipoMovimentoEstornado;
	private Usuario usuarioInclusao;
	private Unidade unidade;
	private Receita receita;
	private Paciente paciente;
	private String numeroDocumento;
	private Date dataMovimento;
	private Date dataInclusao;
	private String motivo;
	private String numeroControle;
	
	
	@Id
	@GeneratedValue
	@Column(name = "id_movto_geral")
	public int getIdMovimentoGeral() {
		return idMovimentoGeral;
	}
	public void setIdMovimentoGeral(int idMovimentoGeral) {
		this.idMovimentoGeral = idMovimentoGeral;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_movto_id_tipo_movto")
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movto_estornado")
	public TipoMovimento getTipoMovimentoEstornado() {
		return tipoMovimentoEstornado;
	}
	public void setTipoMovimentoEstornado(TipoMovimento tipoMovimentoEstornado) {
		this.tipoMovimentoEstornado = tipoMovimentoEstornado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id_usuario")
	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}
	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
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
	@JoinColumn(name = "receita_id_receita")
	public Receita getReceita() {
		return receita;
	}
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paciente_id_paciente")
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@Column(name = "num_documento", length = 15)
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_movto")
	public Date getDataMovimento() {
		return dataMovimento;
	}
	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_incl")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@Column(name = "motivo", length = 120)
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	@Column(name = "num_controle", length = 128)
	public String getNumeroControle() {
		return numeroControle;
	}
	public void setNumeroControle(String numeroControle) {
		this.numeroControle = numeroControle;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Paciente))
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
