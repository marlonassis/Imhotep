package br.com.Imhotep.entidade;

import java.util.Set;
import java.util.TreeSet;

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
import javax.persistence.Transient;

import br.com.Imhotep.comparador.DoseDataComparador;
import br.com.Imhotep.enums.TipoStatusEnum;
import br.com.Imhotep.enums.TipoViaAdministracaoMedicamentoEnum;

@Entity
@Table(name = "tb_prescricao_item")
public class PrescricaoItem {
	private int idPrescricaoItem;
	private Material material;
	private String referenciaUnica;
	private Prescricao prescricao;
	private String observacao;
	private Set<PrescricaoItemDose> prescricaoItemDoses;
	private Profissional profissionalLiberacao;
	private Integer quantidadeLiberada;
	private TipoStatusEnum dispensado;
	private ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI;
	private TipoStatusEnum status;
	private TipoViaAdministracaoMedicamentoEnum tipoViaAdministracaoMedicamento;
	private String outraVia;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_item_id_prescricao_item_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao_item", unique = true, nullable = false)
	public int getIdPrescricaoItem() {
		return idPrescricaoItem;
	}
	public void setIdPrescricaoItem(int idPrescricaoItem) {
		this.idPrescricaoItem = idPrescricaoItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_controle_medicacao_restrito_schi")
	public ControleMedicacaoRestritoSCHI getControleMedicacaoRestritoSCHI() {
		return controleMedicacaoRestritoSCHI;
	}
	public void setControleMedicacaoRestritoSCHI(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		this.controleMedicacaoRestritoSCHI = controleMedicacaoRestritoSCHI;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao")
	public Prescricao getPrescricao() {
		return prescricao;
	}
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_liberacao")
	public Profissional getProfissionalLiberacao() {
		return profissionalLiberacao;
	}
	public void setProfissionalLiberacao(Profissional profissionalLiberacao) {
		this.profissionalLiberacao = profissionalLiberacao;
	}
	
	@Column(name = "ds_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Column(name = "ds_referencia_unica")
	public String getReferenciaUnica() {
		return referenciaUnica;
	}
	public void setReferenciaUnica(String referenciaUnica) {
		this.referenciaUnica = referenciaUnica;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "prescricaoItem")
	public Set<PrescricaoItemDose> getPrescricaoItemDoses() {
		return prescricaoItemDoses;
	}
	public void setPrescricaoItemDoses(Set<PrescricaoItemDose> prescricaoItemDoses) {
		this.prescricaoItemDoses = prescricaoItemDoses;
	}

	@Column(name = "in_quantidade_liberada")
	public Integer getQuantidadeLiberada() {
		return quantidadeLiberada;
	}
	public void setQuantidadeLiberada(Integer quantidadeLiberada) {
		this.quantidadeLiberada = quantidadeLiberada;
	}
	
	@Column(name = "tp_dispensado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getDispensado() {
		return dispensado;
	}
	public void setDispensado(TipoStatusEnum dispensado) {
		this.dispensado = dispensado;
	}
	
	@Column(name = "tp_status")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getStatus() {
		return status;
	}
	public void setStatus(TipoStatusEnum status) {
		this.status = status;
	}
	

	@Column(name = "tp_via_administracao_medicamento")
	@Enumerated(EnumType.STRING)
	public TipoViaAdministracaoMedicamentoEnum getTipoViaAdministracaoMedicamento() {
		return tipoViaAdministracaoMedicamento;
	}
	public void setTipoViaAdministracaoMedicamento(TipoViaAdministracaoMedicamentoEnum tipoViaAdministracaoMedicamento) {
		this.tipoViaAdministracaoMedicamento = tipoViaAdministracaoMedicamento;
	}
	
	@Column(name = "cv_outra_via")
	public String getOutraVia() {
		return outraVia;
	}
	public void setOutraVia(String outraVia) {
		this.outraVia = outraVia;
	}
	
	@Transient
	public Set<PrescricaoItemDose> getPrescricaoItemDosesOrdenado() {
		if(prescricaoItemDoses != null){
			TreeSet<PrescricaoItemDose> ts = new TreeSet<PrescricaoItemDose>(new DoseDataComparador());
			ts.addAll(prescricaoItemDoses);
			return ts;
		}
		return prescricaoItemDoses;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof PrescricaoItem))
			return false;
		
		return ((PrescricaoItem)obj).getIdPrescricaoItem() == this.idPrescricaoItem;
	}
	
	@Override
	public String toString() {
		return "Material: ".concat(material.getDescricao()).concat(" - Prescrição: ").concat(String.valueOf(prescricao.getIdPrescricao()));
	}
}
