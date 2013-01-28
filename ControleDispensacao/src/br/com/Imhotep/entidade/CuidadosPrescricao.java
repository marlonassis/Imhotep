package br.com.Imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cuidados_prescricao")
public class CuidadosPrescricao {
	private int idCuidadosPrescricao;
	private Prescricao prescricao;
	private CuidadosPaciente cuidadosPaciente;
	private String descricaoOutros;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_cuidados_prescricao_id_cuidados_prescricao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_cuidados_prescricao", unique = true, nullable = false)
	public int getIdCuidadosPrescricao() {
		return this.idCuidadosPrescricao;
	}
	
	public void setIdCuidadosPrescricao(int idCuidadosPrescricao){
		this.idCuidadosPrescricao = idCuidadosPrescricao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao")
	public Prescricao getPrescricao() {
		return this.prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cuidados_paciente")
	public CuidadosPaciente getCuidadosPaciente() {
		return this.cuidadosPaciente;
	}

	public void setCuidadosPaciente(CuidadosPaciente cuidadosPaciente) {
		this.cuidadosPaciente = cuidadosPaciente;
	}
	
	@Column(name = "ds_descricao_outros")
	public String getDescricaoOutros() {
		return descricaoOutros;
	}
	public void setDescricaoOutros(String descricaoOutros) {
		this.descricaoOutros = descricaoOutros;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof CuidadosPrescricao))
			return false;
		
		return ((CuidadosPrescricao)obj).getIdCuidadosPrescricao() == this.idCuidadosPrescricao;
	}

	@Override
	public String toString() {
		return getCuidadosPaciente() !=  null ? getCuidadosPaciente().getDescricao() : getDescricaoOutros();
	}
}
