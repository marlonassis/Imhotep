package br.com.imhotep.entidade;

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

import br.com.imhotep.enums.TipoStatusDispensacaoEnum;

@Entity
@Table(name = "tb_solicitacao_medicamento_unidade")
public class SolicitacaoMedicamentoUnidade {
	
	private int idSolicitacaoMedicamentoUnidade;
	private Integer quantidadeSolicitada;
	private Material material;
	private Unidade unidade;
	private Unidade unidadeProfissional;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private TipoStatusDispensacaoEnum statusDispensacao;
	private Integer quantidadeDipensada;
	private MovimentoLivro movimentoLivro;
	private Profissional profissionalLiberacao;
	private Date dataLiberacao;
	private String justificativa;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_solicitacao_medicamento_un_id_solicitacao_medicamento_un_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_solicitacao_medicamento_unidade", unique = true, nullable = false)
	public int getIdSolicitacaoMedicamentoUnidade() {
		return this.idSolicitacaoMedicamentoUnidade;
	}
	
	public void setIdSolicitacaoMedicamentoUnidade(int idSolicitacaoMedicamentoUnidade){
		this.idSolicitacaoMedicamentoUnidade = idSolicitacaoMedicamentoUnidade;
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
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade(){
		return unidade;
	}
	
	public void setUnidade(Unidade unidade){
		this.unidade = unidade;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_profissional")
	public Unidade getUnidadeProfissional(){
		return unidadeProfissional;
	}
	
	public void setUnidadeProfissional(Unidade unidadeProfissional){
		this.unidadeProfissional = unidadeProfissional;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}
	
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@Column(name="tp_status_dispensacao")
	@Enumerated(EnumType.STRING)
	public TipoStatusDispensacaoEnum getStatusDispensacao() {
		return statusDispensacao;
	}

	public void setStatusDispensacao(TipoStatusDispensacaoEnum statusDispensacao) {
		this.statusDispensacao = statusDispensacao;
	}

	@Column(name="in_quantidade_dipensada")
	public Integer getQuantidadeDipensada() {
		return quantidadeDipensada;
	}

	public void setQuantidadeDipensada(Integer quantidadeDipensada) {
		this.quantidadeDipensada = quantidadeDipensada;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")
	public MovimentoLivro getMovimentoLivro() {
		return movimentoLivro;
	}

	public void setMovimentoLivro(MovimentoLivro movimentoLivro) {
		this.movimentoLivro = movimentoLivro;
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

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof SolicitacaoMedicamentoUnidade))
			return false;
		
		return ((SolicitacaoMedicamentoUnidade)obj).getIdSolicitacaoMedicamentoUnidade() == this.idSolicitacaoMedicamentoUnidade;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + profissionalInsercao.hashCode() + unidade.hashCode();
	}

	@Override
	public String toString() {
		return material.getDescricao();
	}
	
}
