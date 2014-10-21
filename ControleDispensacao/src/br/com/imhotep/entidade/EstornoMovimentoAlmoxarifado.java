package br.com.imhotep.entidade;

import java.io.Serializable;
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
@Table(name = "tb_estorno_movimento_almoxarifado")
public class EstornoMovimentoAlmoxarifado  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idEstornoMovimentoAlmoxarifado;
	private Profissional profissionalEstorno;
	private Date dataEstorno;
	private String justificativa;
	private String movimentoCompleto;
	private Integer quantidadeEstornada;
	private EstoqueAlmoxarifado estoqueAlmoxarifado;
	private TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estorno_movimento_almoxari_id_estorno_movimento_almoxari_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estorno_movimento_almoxarifado", unique = true, nullable = false)
	public int getIdEstornoMovimentoAlmoxarifado() {
		return idEstornoMovimentoAlmoxarifado;
	}
	public void setIdEstornoMovimentoAlmoxarifado(int idEstornoMovimentoAlmoxarifado) {
		this.idEstornoMovimentoAlmoxarifado = idEstornoMovimentoAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_estorno")
	public Profissional getProfissionalEstorno() {
		return profissionalEstorno;
	}
	public void setProfissionalEstorno(Profissional profissionalEstorno) {
		this.profissionalEstorno = profissionalEstorno;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_estorno")
	public Date getDataEstorno() {
		return dataEstorno;
	}
	public void setDataEstorno(Date dataEstorno) {
		this.dataEstorno = dataEstorno;
	}
	
	@Column(name="cv_justificativa")
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	@Column(name="cv_movimento_completo")
	public String getMovimentoCompleto(){
		return movimentoCompleto;
	}
	
	public void setMovimentoCompleto(String movimentoCompleto){
		this.movimentoCompleto = movimentoCompleto;
	}
	
	@Column(name="in_quantidade_estornada")
	public Integer getQuantidadeEstornada() {
		return quantidadeEstornada;
	}
	public void setQuantidadeEstornada(Integer quantidadeEstornada) {
		this.quantidadeEstornada = quantidadeEstornada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque_almoxarifado")
	public EstoqueAlmoxarifado getEstoqueAlmoxarifado() {
		return estoqueAlmoxarifado;
	}
	public void setEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		this.estoqueAlmoxarifado = estoqueAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_movimento_almoxarifado")
	public TipoMovimentoAlmoxarifado getTipoMovimentoAlmoxarifado() {
		return tipoMovimentoAlmoxarifado;
	}
	public void setTipoMovimentoAlmoxarifado(TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado) {
		this.tipoMovimentoAlmoxarifado = tipoMovimentoAlmoxarifado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataEstorno == null) ? 0 : dataEstorno.hashCode());
		result = prime * result + idEstornoMovimentoAlmoxarifado;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime
				* result
				+ ((movimentoCompleto == null) ? 0 : movimentoCompleto
						.hashCode());
		result = prime
				* result
				+ ((profissionalEstorno == null) ? 0 : profissionalEstorno
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstornoMovimentoAlmoxarifado other = (EstornoMovimentoAlmoxarifado) obj;
		if (dataEstorno == null) {
			if (other.dataEstorno != null)
				return false;
		} else if (!dataEstorno.equals(other.dataEstorno))
			return false;
		if (idEstornoMovimentoAlmoxarifado != other.idEstornoMovimentoAlmoxarifado)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (movimentoCompleto == null) {
			if (other.movimentoCompleto != null)
				return false;
		} else if (!movimentoCompleto.equals(other.movimentoCompleto))
			return false;
		if (profissionalEstorno == null) {
			if (other.profissionalEstorno != null)
				return false;
		} else if (!profissionalEstorno.equals(other.profissionalEstorno))
			return false;
		return true;
	}
	
	
}
