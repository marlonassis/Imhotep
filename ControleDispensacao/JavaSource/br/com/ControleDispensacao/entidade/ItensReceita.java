package br.com.ControleDispensacao.entidade;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;

@Entity
@Table(name = "itens_receita")
public class ItensReceita {
	private int idItensReceita;
	private Material material;
	private Receita receita;
	private Integer quantidadePrescrita;
	private Integer tempoTratamento;
	private Integer quantidadeDispensadaAnteriormente;
	private Integer quantidadeDispensadaMes;
	private Date dataUltimaDispensacao;
	private String identificadorReceitaControlada;
	private TipoSituacaoEnum status;
	private MotivoFimReceita motivoFimReceita;
	private Date dataFimReceita;
	private String observacao;
	private Usuario usuariFimReceita;
	
	@Id
	@GeneratedValue
	@Column(name = "id_itens_receita")
	public int getIdItensReceita() {
		return idItensReceita;
	}
	public void setIdItensReceita(int idItensReceita) {
		this.idItensReceita = idItensReceita;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "material_id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "receita_id_receita")
	public Receita getReceita() {
		return receita;
	}
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	@Column(name = "qtde_prescrita")
	public Integer getQuantidadePrescrita() {
		return quantidadePrescrita;
	}
	public void setQuantidadePrescrita(Integer quantidadePrescrita) {
		this.quantidadePrescrita = quantidadePrescrita;
	}
	
	@Column(name = "tempo_tratamento")
	public Integer getTempoTratamento() {
		return tempoTratamento;
	}
	public void setTempoTratamento(Integer tempoTratamento) {
		this.tempoTratamento = tempoTratamento;
	}
	
	@Column(name = "qtde_disp_anterior")
	public Integer getQuantidadeDispensadaAnteriormente() {
		return quantidadeDispensadaAnteriormente;
	}
	public void setQuantidadeDispensadaAnteriormente(
			Integer quantidadeDispensadaAnteriormente) {
		this.quantidadeDispensadaAnteriormente = quantidadeDispensadaAnteriormente;
	}
	
	@Column(name = "qtde_disp_mes")
	public Integer getQuantidadeDispensadaMes() {
		return quantidadeDispensadaMes;
	}
	public void setQuantidadeDispensadaMes(Integer quantidadeDispensadaMes) {
		this.quantidadeDispensadaMes = quantidadeDispensadaMes;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_ult_disp")
	public Date getDataUltimaDispensacao() {
		return dataUltimaDispensacao;
	}
	public void setDataUltimaDispensacao(Date dataUltimaDispensacao) {
		this.dataUltimaDispensacao = dataUltimaDispensacao;
	}
	
	@Column(name = "num_receita_controlada")
	public String getIdentificadorReceitaControlada() {
		return identificadorReceitaControlada;
	}
	public void setIdentificadorReceitaControlada(String identificadorReceitaControlada) {
		this.identificadorReceitaControlada = identificadorReceitaControlada;
	}
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return status;
	}
	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "motivo_fim_receita_id_motivo_fim_receita")
	public MotivoFimReceita getMotivoFimReceita() {
		return motivoFimReceita;
	}
	public void setMotivoFimReceita(MotivoFimReceita motivoFimReceita) {
		this.motivoFimReceita = motivoFimReceita;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_fim_receita")
	public Date getDataFimReceita() {
		return dataFimReceita;
	}
	public void setDataFimReceita(Date dataFimReceita) {
		this.dataFimReceita = dataFimReceita;
	}
	
	@Column(name = "ds_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usua_fim_receita")
	public Usuario getUsuariFimReceita() {
		return usuariFimReceita;
	}
	public void setUsuariFimReceita(Usuario usuariFimReceita) {
		this.usuariFimReceita = usuariFimReceita;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ItensReceita))
			return false;
		
		return ((ItensReceita)obj).getIdItensReceita() == this.idItensReceita;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + material.getDescricao().concat(receita.getNumero().toString()).hashCode();
	}

	@Override
	public String toString() {
		return receita.getNumero().toString().concat(" - ").concat(material.getDescricao());
	}
	
}
