package br.com.ControleDispensacao.entidade;

import java.text.SimpleDateFormat;
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

import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_reserva_material_prescricao")
public class ReservaMaterialPrescricao {
	private int idReservaMaterialPrescricao;
	private Material material;
	private String referenciaUnica;
	private TipoStatusEnum dispensado;
	private Date dataReserva;
	private Integer quantidade;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_reserva_material_prescrica_id_reserva_material_prescrica_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_reserva_material_prescricao", unique = true, nullable = false)
	public int getIdReservaMaterialPrescricao() {
		return idReservaMaterialPrescricao;
	}
	public void setIdReservaMaterialPrescricao(int idReservaMaterialPrescricao) {
		this.idReservaMaterialPrescricao = idReservaMaterialPrescricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Column(name = "ds_referencia_unica")
	public String getReferenciaUnica() {
		return referenciaUnica;
	}
	public void setReferenciaUnica(String referenciaUnica) {
		this.referenciaUnica = referenciaUnica;
	}
	
	@Column(name = "in_quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name = "tp_dispensado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getDispensado() {
		return dispensado;
	}
	public void setDispensado(TipoStatusEnum dispensado) {
		this.dispensado = dispensado;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_reserva")
	public Date getDataReserva() {
		return dataReserva;
	}
	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ReservaMaterialPrescricao))
			return false;
		
		return ((ReservaMaterialPrescricao)obj).getIdReservaMaterialPrescricao() == this.idReservaMaterialPrescricao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + referenciaUnica.hashCode();
	}

	@Override
	public String toString() {
		return "Material: ".concat(material.getDescricao()).concat(" - Data Reserva: ").concat(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(dataReserva)).concat(" - Dispesado: ").concat(new SimpleDateFormat("dd/MM/yyyy").format(getDispensado().getLabel()));
	}
	
}
