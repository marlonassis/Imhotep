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
@Table(name = "tb_solicitacao_material_almoxarifado_unidade_item")
public class SolicitacaoMaterialAlmoxarifadoUnidadeItem implements Serializable {
	private static final long serialVersionUID = 5649144355320179672L;
	
	private int idSolicitacaoMaterialAlmoxarifadoUnidadeItem;
	private Double quantidadeSolicitada;
	private MaterialAlmoxarifado materialAlmoxarifado;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private Profissional profissionalJustificativa;
	private Date dataJustificativa;
	private String justificativa;
	private SolicitacaoMaterialAlmoxarifadoUnidade solicitacaoMaterialAlmoxarifadoUnidade;
	private TipoStatusSolicitacaoItemEnum statusItem;
	private Set<DispensacaoSimplesAlmoxarifado> dispensacoes;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_solicitacao_material_almox_id_solicitacao_material_almo_seq1")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_solicitacao_material_almoxarifado_unidade_item", unique = true, nullable = false)
	public int getIdSolicitacaoMaterialAlmoxarifadoUnidadeItem() {
		return this.idSolicitacaoMaterialAlmoxarifadoUnidadeItem;
	}
	
	public void setIdSolicitacaoMaterialAlmoxarifadoUnidadeItem(int idSolicitacaoMaterialAlmoxarifadoUnidadeItem){
		this.idSolicitacaoMaterialAlmoxarifadoUnidadeItem = idSolicitacaoMaterialAlmoxarifadoUnidadeItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material_almoxarifado")
	public MaterialAlmoxarifado getMaterialAlmoxarifado(){
		return materialAlmoxarifado;
	}
	
	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado){
		this.materialAlmoxarifado = materialAlmoxarifado;
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
	public Double getQuantidadeSolicitada(){
		return quantidadeSolicitada;
	}
	
	public void setQuantidadeSolicitada(Double quantidadeSolicitada){
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
	@JoinColumn(name = "id_profissional_justificativa")
	public Profissional getProfissionalJustificativa() {
		return profissionalJustificativa;
	}

	public void setProfissionalJustificativa(Profissional profissionalJustificativa) {
		this.profissionalJustificativa = profissionalJustificativa;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_justificativa")
	public Date getDataJustificativa() {
		return dataJustificativa;
	}

	public void setDataJustificativa(Date dataJustificativa) {
		this.dataJustificativa = dataJustificativa;
	}

	@Column(name="cv_justificativa")
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_solicitacao_material_almoxarifado_unidade")
	public SolicitacaoMaterialAlmoxarifadoUnidade getSolicitacaoMaterialAlmoxarifadoUnidade() {
		return solicitacaoMaterialAlmoxarifadoUnidade;
	}

	public void setSolicitacaoMaterialAlmoxarifadoUnidade(SolicitacaoMaterialAlmoxarifadoUnidade solicitacaoMaterialAlmoxarifadoUnidade) {
		this.solicitacaoMaterialAlmoxarifadoUnidade = solicitacaoMaterialAlmoxarifadoUnidade;
	}

	@Column(name="tp_tipo_status_item")
	@Enumerated(EnumType.STRING)
	public TipoStatusSolicitacaoItemEnum getStatusItem() {
		return statusItem;
	}

	public void setStatusItem(TipoStatusSolicitacaoItemEnum statusItem) {
		this.statusItem = statusItem;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "solicitacaoMaterialAlmoxarifadoUnidadeItem")
	public Set<DispensacaoSimplesAlmoxarifado> getDispensacoes() {
		return dispensacoes;
	}
	public void setDispensacoes(Set<DispensacaoSimplesAlmoxarifado> dispensacoes) {
		this.dispensacoes = dispensacoes;
	}
	
	@Transient
	public List<DispensacaoSimplesAlmoxarifado> getDispensacoesList(){
		return new ArrayList<DispensacaoSimplesAlmoxarifado>(getDispensacoes());
	}
	
	@Transient
	public Double getQuantidadeDispensada(){
		Double total = 0d;
		for(DispensacaoSimplesAlmoxarifado ds : getDispensacoes()){
			Double quantidadeMovimentacao = ds.getMovimentoLivroAlmoxarifado().getQuantidadeMovimentacao();
			total += quantidadeMovimentacao == null ? 0 : quantidadeMovimentacao;
		}
		return total;
	}
	
	@Override
	public String toString() {
		return materialAlmoxarifado.getDescricao();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataInsercao == null) ? 0 : dataInsercao.hashCode());
		result = prime
				* result
				+ ((dataJustificativa == null) ? 0 : dataJustificativa
						.hashCode());
		result = prime * result + idSolicitacaoMaterialAlmoxarifadoUnidadeItem;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime
				* result
				+ ((materialAlmoxarifado == null) ? 0 : materialAlmoxarifado
						.hashCode());
		result = prime
				* result
				+ ((quantidadeSolicitada == null) ? 0 : quantidadeSolicitada
						.hashCode());
		result = prime
				* result
				+ ((solicitacaoMaterialAlmoxarifadoUnidade == null) ? 0
						: solicitacaoMaterialAlmoxarifadoUnidade.hashCode());
		result = prime * result
				+ ((statusItem == null) ? 0 : statusItem.hashCode());
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
		SolicitacaoMaterialAlmoxarifadoUnidadeItem other = (SolicitacaoMaterialAlmoxarifadoUnidadeItem) obj;
		if (dataInsercao == null) {
			if (other.dataInsercao != null)
				return false;
		} else if (!dataInsercao.equals(other.dataInsercao))
			return false;
		if (dataJustificativa == null) {
			if (other.dataJustificativa != null)
				return false;
		} else if (!dataJustificativa.equals(other.dataJustificativa))
			return false;
		if (idSolicitacaoMaterialAlmoxarifadoUnidadeItem != other.idSolicitacaoMaterialAlmoxarifadoUnidadeItem)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (materialAlmoxarifado == null) {
			if (other.materialAlmoxarifado != null)
				return false;
		} else if (!materialAlmoxarifado.equals(other.materialAlmoxarifado))
			return false;
		if (quantidadeSolicitada == null) {
			if (other.quantidadeSolicitada != null)
				return false;
		} else if (!quantidadeSolicitada.equals(other.quantidadeSolicitada))
			return false;
		if (solicitacaoMaterialAlmoxarifadoUnidade == null) {
			if (other.solicitacaoMaterialAlmoxarifadoUnidade != null)
				return false;
		} else if (!solicitacaoMaterialAlmoxarifadoUnidade
				.equals(other.solicitacaoMaterialAlmoxarifadoUnidade))
			return false;
		if (statusItem != other.statusItem)
			return false;
		return true;
	}
	
	
	
}
