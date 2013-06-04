package br.com.imhotep.entidade;

import java.util.Date;
import java.util.List;

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

import br.com.imhotep.enums.TipoStatusDispensacaoEnum;

@Entity
@Table(name = "tb_solicitacao_medicamento_unidade")
public class SolicitacaoMedicamentoUnidade {
	
	private int idSolicitacaoMedicamentoUnidade;
	private Unidade unidadeDestino;
	private Unidade unidadeProfissionalInsercao;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private TipoStatusDispensacaoEnum statusDispensacao;
	private List<SolicitacaoMedicamentoUnidadeItem> itens;
	private Date dataDispensacao;
	private Profissional profissionalDispensacao;
	private String justificativa;
	private Profissional profissionalReceptor;
	
	
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
	@JoinColumn(name = "id_unidade_destino")
	public Unidade getUnidadeDestino(){
		return unidadeDestino;
	}
	
	public void setUnidadeDestino(Unidade unidadeDestino){
		this.unidadeDestino = unidadeDestino;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao(){
		return profissionalInsercao;
	}
	
	public void setProfissionalInsercao(Profissional profissionalInsercao){
		this.profissionalInsercao = profissionalInsercao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_profissional_insercao")
	public Unidade getUnidadeProfissionalInsercao(){
		return unidadeProfissionalInsercao;
	}
	
	public void setUnidadeProfissionalInsercao(Unidade unidadeProfissionalInsercao){
		this.unidadeProfissionalInsercao = unidadeProfissionalInsercao;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "solicitacaoMedicamentoUnidade")
	public List<SolicitacaoMedicamentoUnidadeItem> getItens() {
		return itens;
	}
	public void setItens(List<SolicitacaoMedicamentoUnidadeItem> itens) {
		this.itens = itens;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_dispensacao")
	public Date getDataDispensacao() {
		return dataDispensacao;
	}

	public void setDataDispensacao(Date dataDispensacao) {
		this.dataDispensacao = dataDispensacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_dispensacao")
	public Profissional getProfissionalDispensacao() {
		return profissionalDispensacao;
	}

	public void setProfissionalDispensacao(
			Profissional profissionalDispensacao) {
		this.profissionalDispensacao = profissionalDispensacao;
	}
	
	@Column(name="cv_justificativa")
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_receptor")
	public Profissional getProfissionalReceptor() {
		return profissionalReceptor;
	}

	public void setProfissionalReceptor(Profissional profissionalReceptor) {
		this.profissionalReceptor = profissionalReceptor;
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
	    return hash * 31 + profissionalInsercao.hashCode() + dataInsercao.hashCode();
	}

	@Override
	public String toString() {
		return unidadeDestino.getNome();
	}
	
}
