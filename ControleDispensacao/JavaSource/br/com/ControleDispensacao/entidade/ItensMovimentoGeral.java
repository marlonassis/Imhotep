package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "tb_itens_movimento_geral")
public class ItensMovimentoGeral {
	private int idItensMovimentoGeral;
	private MovimentoGeral movimentoGeral;
	private Integer quantidade;
	private PrescricaoItem prescricaoItem;
	private Hospital hospital;
	private Date dataDoacao;
	private Date dataCriacao;
	private Estoque estoque;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_itens_movimento_geral_id_itens_movimento_geral_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_itens_movimento_geral", unique = true, nullable = false)
	public int getIdItensMovimentoGeral() {
		return idItensMovimentoGeral;
	}
	public void setIdItensMovimentoGeral(int idItensMovimentoGeral) {
		this.idItensMovimentoGeral = idItensMovimentoGeral;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name = "id_movimento_geral")
	public MovimentoGeral getMovimentoGeral() {
		return movimentoGeral;
	}
	public void setMovimentoGeral(MovimentoGeral movimentoGeral) {
		this.movimentoGeral = movimentoGeral;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name = "id_estoque")
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	
	@Column(name = "in_quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao_item")
	public PrescricaoItem getPrescricaoItem() {
		return prescricaoItem;
	}
	public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
		this.prescricaoItem = prescricaoItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hospital")
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_doacao")
	public Date getDataDoacao() {
		return dataDoacao;
	}
	public void setDataDoacao(Date dataDoacao) {
		this.dataDoacao = dataDoacao;
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
		if(!(obj instanceof Usuario))
			return false;
		
		return ((ItensMovimentoGeral)obj).getIdItensMovimentoGeral() == this.idItensMovimentoGeral;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + getEstoque().getMaterial().getDescricao().concat(getEstoque().getLote()).hashCode();
	}

	@Override
	public String toString() {
		return getEstoque().getLote().concat(" - ").concat(getEstoque().getMaterial().getDescricao());
	}

}
