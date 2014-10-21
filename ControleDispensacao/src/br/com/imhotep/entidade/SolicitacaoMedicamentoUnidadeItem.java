package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;

@Entity
@Table(name = "tb_solicitacao_medicamento_unidade_item")
public class SolicitacaoMedicamentoUnidadeItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idSolicitacaoMedicamentoUnidadeItem;
	private Integer quantidadeSolicitada;
	private Material material;
	private Unidade unidadeProfissionalLiberacao;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private Profissional profissionalLiberacao;
	private Date dataLiberacao;
	private String justificativa;
	private SolicitacaoMedicamentoUnidade solicitacaoMedicamentoUnidade;
	private TipoStatusSolicitacaoItemEnum statusItem;
	private Set<DispensacaoSimples> dispensacacoes;
	
	public SolicitacaoMedicamentoUnidadeItem(){
		super();
	}
	
	public SolicitacaoMedicamentoUnidadeItem(Integer quantidadeSolicitada, Material material){
		this.quantidadeSolicitada = quantidadeSolicitada;
		this.material = material;
	}
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_solicitacao_medicamento_un_id_solicitacao_medicamento_u_seq1")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_solicitacao_medicamento_unidade_item", unique = true, nullable = false)
	public int getIdSolicitacaoMedicamentoUnidadeItem() {
		return this.idSolicitacaoMedicamentoUnidadeItem;
	}
	
	public void setIdSolicitacaoMedicamentoUnidadeItem(int idSolicitacaoMedicamentoUnidadeItem){
		this.idSolicitacaoMedicamentoUnidadeItem = idSolicitacaoMedicamentoUnidadeItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial(){
		return material;
	}
	
	public void setMaterial(Material material){
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_profissional_liberacao")
	public Unidade getUnidadeProfissionalLiberacao(){
		return unidadeProfissionalLiberacao;
	}
	
	public void setUnidadeProfissionalLiberacao(Unidade unidadeProfissionalLiberacao){
		this.unidadeProfissionalLiberacao = unidadeProfissionalLiberacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao(){
		return profissionalInsercao;
	}
	
	public void setProfissionalInsercao(Profissional profissionalInsercao){
		this.profissionalInsercao = profissionalInsercao;
	}
	
	@Column(name = "in_quantidade_solicitada")
	public Integer getQuantidadeSolicitada(){
		return quantidadeSolicitada;
	}
	
	public void setQuantidadeSolicitada(Integer quantidadeSolicitada){
		this.quantidadeSolicitada = quantidadeSolicitada;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}
	
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_liberacao")
	public Profissional getProfissionalLiberacao() {
		return profissionalLiberacao;
	}

	public void setProfissionalLiberacao(Profissional profissionalLiberacao) {
		this.profissionalLiberacao = profissionalLiberacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_liberacao")
	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	@Column(name="cv_justificativa")
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_solicitacao_medicamento_unidade")
	public SolicitacaoMedicamentoUnidade getSolicitacaoMedicamentoUnidade() {
		return solicitacaoMedicamentoUnidade;
	}

	public void setSolicitacaoMedicamentoUnidade(SolicitacaoMedicamentoUnidade solicitacaoMedicamentoUnidade) {
		this.solicitacaoMedicamentoUnidade = solicitacaoMedicamentoUnidade;
	}

	@Column(name="tp_tipo_status_item")
	@Enumerated(EnumType.STRING)
	public TipoStatusSolicitacaoItemEnum getStatusItem() {
		return statusItem;
	}

	public void setStatusItem(TipoStatusSolicitacaoItemEnum statusItem) {
		this.statusItem = statusItem;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "solicitacaoMedicamentoUnidadeItem")
	public Set<DispensacaoSimples> getDispensacoes() {
		return dispensacacoes;
	}
	public void setDispensacoes(Set<DispensacaoSimples> dispensacacoes) {
		this.dispensacacoes = dispensacacoes;
	}
	
	@Transient
	public List<DispensacaoSimples> getDispensacoesList(){
		ArrayList<DispensacaoSimples> list = new ArrayList<DispensacaoSimples>(getDispensacoes());
		return list;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataInsercao == null) ? 0 : dataInsercao.hashCode());
		result = prime * result
				+ ((dataLiberacao == null) ? 0 : dataLiberacao.hashCode());
		result = prime * result + idSolicitacaoMedicamentoUnidadeItem;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime
				* result
				+ ((profissionalInsercao == null) ? 0 : profissionalInsercao
						.hashCode());
		result = prime
				* result
				+ ((profissionalLiberacao == null) ? 0 : profissionalLiberacao
						.hashCode());
		result = prime
				* result
				+ ((quantidadeSolicitada == null) ? 0 : quantidadeSolicitada
						.hashCode());
		result = prime
				* result
				+ ((solicitacaoMedicamentoUnidade == null) ? 0
						: solicitacaoMedicamentoUnidade.hashCode());
		result = prime * result
				+ ((statusItem == null) ? 0 : statusItem.hashCode());
		result = prime
				* result
				+ ((unidadeProfissionalLiberacao == null) ? 0
						: unidadeProfissionalLiberacao.hashCode());
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
		SolicitacaoMedicamentoUnidadeItem other = (SolicitacaoMedicamentoUnidadeItem) obj;
		if (dataInsercao == null) {
			if (other.dataInsercao != null)
				return false;
		} else if (!dataInsercao.equals(other.dataInsercao))
			return false;
		if (dataLiberacao == null) {
			if (other.dataLiberacao != null)
				return false;
		} else if (!dataLiberacao.equals(other.dataLiberacao))
			return false;
		if (idSolicitacaoMedicamentoUnidadeItem != other.idSolicitacaoMedicamentoUnidadeItem)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (profissionalInsercao == null) {
			if (other.profissionalInsercao != null)
				return false;
		} else if (!profissionalInsercao.equals(other.profissionalInsercao))
			return false;
		if (profissionalLiberacao == null) {
			if (other.profissionalLiberacao != null)
				return false;
		} else if (!profissionalLiberacao.equals(other.profissionalLiberacao))
			return false;
		if (quantidadeSolicitada == null) {
			if (other.quantidadeSolicitada != null)
				return false;
		} else if (!quantidadeSolicitada.equals(other.quantidadeSolicitada))
			return false;
		if (solicitacaoMedicamentoUnidade == null) {
			if (other.solicitacaoMedicamentoUnidade != null)
				return false;
		} else if (!solicitacaoMedicamentoUnidade
				.equals(other.solicitacaoMedicamentoUnidade))
			return false;
		if (statusItem != other.statusItem)
			return false;
		if (unidadeProfissionalLiberacao == null) {
			if (other.unidadeProfissionalLiberacao != null)
				return false;
		} else if (!unidadeProfissionalLiberacao
				.equals(other.unidadeProfissionalLiberacao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if(material != null)
			return material.getDescricao();
		return "";
	}
	
}
