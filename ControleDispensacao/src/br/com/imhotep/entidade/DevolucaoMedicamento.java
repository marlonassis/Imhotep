package br.com.imhotep.entidade;

import java.io.Serializable;
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

import br.com.imhotep.enums.TipoStatusDevolucaoItemEnum;

@Entity
@Table(name = "tb_devolucao_medicamento")
public class DevolucaoMedicamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idDevolucaoMedicamento;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	private Profissional profissionalConfirmacao;
	private Date dataRecebimento;
	private Date dataFechamento;
	private TipoStatusDevolucaoItemEnum status;
	private Unidade unidadeDevolucao;
	private String justificativa;
	private List<DevolucaoMedicamentoItem> itens;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_devolucao_medicamento_id_devolucao_medicamento_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_devolucao_medicamento", unique = true, nullable = false)
	public int getIdDevolucaoMedicamento() {
		return idDevolucaoMedicamento;
	}

	public void setIdDevolucaoMedicamento(int idDevolucaoMedicamento) {
		this.idDevolucaoMedicamento = idDevolucaoMedicamento;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}

	public void setProfissionalInsercao(Profissional profissionalInsercao) {
		this.profissionalInsercao = profissionalInsercao;
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
	@JoinColumn(name = "id_profissional_confirmacao")
	public Profissional getProfissionalConfirmacao() {
		return profissionalConfirmacao;
	}

	public void setProfissionalConfirmacao(Profissional profissionalConfirmacao) {
		this.profissionalConfirmacao = profissionalConfirmacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_recebimento")
	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_fechamento")
	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	
	@Column(name="tp_status")
	@Enumerated(EnumType.STRING)
	public TipoStatusDevolucaoItemEnum getStatus() {
		return status;
	}

	public void setStatus(TipoStatusDevolucaoItemEnum status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_devolucao")
	public Unidade getUnidadeDevolucao() {
		return unidadeDevolucao;
	}

	public void setUnidadeDevolucao(Unidade unidadeDevolucao) {
		this.unidadeDevolucao = unidadeDevolucao;
	}

	@Column(name="cv_justificativa")
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "devolucaoMedicamento")
	public List<DevolucaoMedicamentoItem> getItens() {
		return itens;
	}
	public void setItens(List<DevolucaoMedicamentoItem> itens) {
		this.itens = itens;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof DevolucaoMedicamento))
			return false;
		
		return ((DevolucaoMedicamento)obj).getIdDevolucaoMedicamento() == this.idDevolucaoMedicamento;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + profissionalInsercao.hashCode() + dataInsercao.hashCode();
	}
	
}
