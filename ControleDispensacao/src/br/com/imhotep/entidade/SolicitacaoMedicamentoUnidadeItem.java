package br.com.imhotep.entidade;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;

@Entity
@Table(name = "tb_solicitacao_medicamento_unidade_item")
public class SolicitacaoMedicamentoUnidadeItem implements Serializable {
	
	/**
	 * 
	 */
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
	private DispensacaoSimples dispensacaoSimples;
	
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

	@OneToOne(mappedBy="solicitacaoMedicamentoUnidadeItem")  
	public DispensacaoSimples getDispensacaoSimples() {
		return dispensacaoSimples;
	}
	public void setDispensacaoSimples(DispensacaoSimples dispensacaoSimples) {
		this.dispensacaoSimples = dispensacaoSimples;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof SolicitacaoMedicamentoUnidadeItem))
			return false;
		
		return ((SolicitacaoMedicamentoUnidadeItem)obj).getIdSolicitacaoMedicamentoUnidadeItem() == this.idSolicitacaoMedicamentoUnidadeItem;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + profissionalInsercao.hashCode() + dataInsercao.hashCode();
	}

	@Override
	public String toString() {
		return material.getDescricao();
	}
	
}
