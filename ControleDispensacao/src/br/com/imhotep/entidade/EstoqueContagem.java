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
@Table(name = "tb_estoque_contagem")
public class EstoqueContagem {

	private int idEstoqueContagem;
	private Estoque estoque;
	private Integer quantidadeAtual;
	private Integer quantidadeContada;
	private Date dataContagem;
	private Date dataCadastro;
	private Profissional responsavel;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estoque_contagem_id_estoque_contagem_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estoque_contagem", unique = true, nullable = false)
	public int getIdEstoqueContagem() {
		return idEstoqueContagem;
	}
	public void setIdEstoqueContagem(int idEstoqueContagem) {
		this.idEstoqueContagem = idEstoqueContagem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque")
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_contagem")
	public Date getDataContagem() {
		return dataContagem;
	}
	public void setDataContagem(Date dataContagem) {
		this.dataContagem = dataContagem;
	}
	
	@Column(name="in_quantidade_atual")
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
	@Column(name="in_quantidade_contada")
	public Integer getQuantidadeContada() {
		return quantidadeContada;
	}
	public void setQuantidadeContada(Integer quantidadeContada) {
		this.quantidadeContada = quantidadeContada;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_responsavel")
	public Profissional getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Profissional responsavel) {
		this.responsavel = responsavel;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result
				+ ((dataContagem == null) ? 0 : dataContagem.hashCode());
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime * result + idEstoqueContagem;
		result = prime * result
				+ ((quantidadeAtual == null) ? 0 : quantidadeAtual.hashCode());
		result = prime
				* result
				+ ((quantidadeContada == null) ? 0 : quantidadeContada
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
		EstoqueContagem other = (EstoqueContagem) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataContagem == null) {
			if (other.dataContagem != null)
				return false;
		} else if (!dataContagem.equals(other.dataContagem))
			return false;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (idEstoqueContagem != other.idEstoqueContagem)
			return false;
		if (quantidadeAtual == null) {
			if (other.quantidadeAtual != null)
				return false;
		} else if (!quantidadeAtual.equals(other.quantidadeAtual))
			return false;
		if (quantidadeContada == null) {
			if (other.quantidadeContada != null)
				return false;
		} else if (!quantidadeContada.equals(other.quantidadeContada))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		if(estoque != null)
			return estoque.toString();
		return "Vazio";
	}
}
