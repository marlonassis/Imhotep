package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Collections;
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

import br.com.imhotep.comparador.MaterialGrupoSolicitacaoAlmoxarifadoItemComparador;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;

@Entity
@Table(name = "tb_solicitacao_material_almoxarifado_unidade")
public class SolicitacaoMaterialAlmoxarifadoUnidade implements Serializable {
	private static final long serialVersionUID = 4442060897106193327L;
	
	private int idSolicitacaoMaterialAlmoxarifadoUnidade;
	private Unidade unidadeDestino;
	private Unidade unidadeProfissionalInsercao;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private TipoStatusDispensacaoEnum statusDispensacao;
	private List<SolicitacaoMaterialAlmoxarifadoUnidadeItem> itens;
	private Date dataDispensacao;
	private Profissional profissionalDispensacao;
	private String justificativa;
	private Profissional profissionalReceptor;
	private Profissional profissionalLock;
	private Date dataFechamento;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_solicitacao_material_almox_id_solicitacao_material_almox_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_solicitacao_material_almoxarifado_unidade", unique = true, nullable = false)
	public int getIdSolicitacaoMaterialAlmoxarifadoUnidade() {
		return this.idSolicitacaoMaterialAlmoxarifadoUnidade;
	}
	
	public void setIdSolicitacaoMaterialAlmoxarifadoUnidade(int idSolicitacaoMaterialAlmoxarifadoUnidade){
		this.idSolicitacaoMaterialAlmoxarifadoUnidade = idSolicitacaoMaterialAlmoxarifadoUnidade;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "solicitacaoMaterialAlmoxarifadoUnidade")
	public List<SolicitacaoMaterialAlmoxarifadoUnidadeItem> getItens() {
		if(itens != null)
			Collections.sort(itens, new MaterialGrupoSolicitacaoAlmoxarifadoItemComparador());
		return itens;
	}
	public void setItens(List<SolicitacaoMaterialAlmoxarifadoUnidadeItem> itens) {
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_lock")
	public Profissional getProfissionalLock() {
		return profissionalLock;
	}

	public void setProfissionalLock(Profissional profissionalLock) {
		this.profissionalLock = profissionalLock;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_fechamento")
	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataDispensacao == null) ? 0 : dataDispensacao.hashCode());
		result = prime * result
				+ ((dataFechamento == null) ? 0 : dataFechamento.hashCode());
		result = prime * result
				+ ((dataInsercao == null) ? 0 : dataInsercao.hashCode());
		result = prime * result + idSolicitacaoMaterialAlmoxarifadoUnidade;
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime
				* result
				+ ((profissionalDispensacao == null) ? 0
						: profissionalDispensacao.hashCode());
		result = prime
				* result
				+ ((profissionalInsercao == null) ? 0 : profissionalInsercao
						.hashCode());
		result = prime
				* result
				+ ((profissionalLock == null) ? 0 : profissionalLock.hashCode());
		result = prime
				* result
				+ ((profissionalReceptor == null) ? 0 : profissionalReceptor
						.hashCode());
		result = prime
				* result
				+ ((statusDispensacao == null) ? 0 : statusDispensacao
						.hashCode());
		result = prime * result
				+ ((unidadeDestino == null) ? 0 : unidadeDestino.hashCode());
		result = prime
				* result
				+ ((unidadeProfissionalInsercao == null) ? 0
						: unidadeProfissionalInsercao.hashCode());
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
		SolicitacaoMaterialAlmoxarifadoUnidade other = (SolicitacaoMaterialAlmoxarifadoUnidade) obj;
		if (dataDispensacao == null) {
			if (other.dataDispensacao != null)
				return false;
		} else if (!dataDispensacao.equals(other.dataDispensacao))
			return false;
		if (dataFechamento == null) {
			if (other.dataFechamento != null)
				return false;
		} else if (!dataFechamento.equals(other.dataFechamento))
			return false;
		if (dataInsercao == null) {
			if (other.dataInsercao != null)
				return false;
		} else if (!dataInsercao.equals(other.dataInsercao))
			return false;
		if (idSolicitacaoMaterialAlmoxarifadoUnidade != other.idSolicitacaoMaterialAlmoxarifadoUnidade)
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (profissionalDispensacao == null) {
			if (other.profissionalDispensacao != null)
				return false;
		} else if (!profissionalDispensacao
				.equals(other.profissionalDispensacao))
			return false;
		if (profissionalInsercao == null) {
			if (other.profissionalInsercao != null)
				return false;
		} else if (!profissionalInsercao.equals(other.profissionalInsercao))
			return false;
		if (profissionalLock == null) {
			if (other.profissionalLock != null)
				return false;
		} else if (!profissionalLock.equals(other.profissionalLock))
			return false;
		if (profissionalReceptor == null) {
			if (other.profissionalReceptor != null)
				return false;
		} else if (!profissionalReceptor.equals(other.profissionalReceptor))
			return false;
		if (statusDispensacao != other.statusDispensacao)
			return false;
		if (unidadeDestino == null) {
			if (other.unidadeDestino != null)
				return false;
		} else if (!unidadeDestino.equals(other.unidadeDestino))
			return false;
		if (unidadeProfissionalInsercao == null) {
			if (other.unidadeProfissionalInsercao != null)
				return false;
		} else if (!unidadeProfissionalInsercao
				.equals(other.unidadeProfissionalInsercao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return unidadeDestino.getNome();
	}
	
}
