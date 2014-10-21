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
@Table(name = "tb_estorno_movimento_farmacia")
public class EstornoMovimentoFarmacia  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idEstornoMovimentoFarmacia;
	private Profissional profissionalEstorno;
	private Date dataEstorno;
	private String justificativa;
	private String movimentoCompleto;
	private Integer quantidadeEstornada;
	private Estoque estoque;
	private TipoMovimento tipoMovimento;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estorno_movimento_farmacia_id_estorno_movimento_farmacia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estorno_movimento_farmacia", unique = true, nullable = false)
	public int getIdEstornoMovimentoFarmacia() {
		return idEstornoMovimentoFarmacia;
	}
	public void setIdEstornoMovimentoFarmacia(int idEstornoMovimentoFarmacia) {
		this.idEstornoMovimentoFarmacia = idEstornoMovimentoFarmacia;
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
	@JoinColumn(name = "id_estoque")
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_movimento")
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataEstorno == null) ? 0 : dataEstorno.hashCode());
		result = prime * result + idEstornoMovimentoFarmacia;
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
		EstornoMovimentoFarmacia other = (EstornoMovimentoFarmacia) obj;
		if (dataEstorno == null) {
			if (other.dataEstorno != null)
				return false;
		} else if (!dataEstorno.equals(other.dataEstorno))
			return false;
		if (idEstornoMovimentoFarmacia != other.idEstornoMovimentoFarmacia)
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
