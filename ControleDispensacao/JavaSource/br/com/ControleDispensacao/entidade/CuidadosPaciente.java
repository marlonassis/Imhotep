package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.ControleDispensacao.enums.TipoCuidadosPacienteEnum;

@Entity
@Table(name = "tb_cuidados_paciente")
public class CuidadosPaciente {
	
	private int idCuidadosPaciente;
	private String descricao;
	private TipoCuidadosPacienteEnum  tipoCuidadosPaciente;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_cuidados_paciente_id_cuidados_paciente_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_cuidados_paciente", unique = true, nullable = false)
	public int getIdCuidadosPaciente() {
		return idCuidadosPaciente;
	}
	
	public void setIdCuidadosPaciente(int idCuidadosPaciente) {
		this.idCuidadosPaciente = idCuidadosPaciente;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "tp_cuidado_paciente")
	@Enumerated(EnumType.STRING)
	public TipoCuidadosPacienteEnum getTipoCuidadosPaciente() {
		return tipoCuidadosPaciente;
	}
	
	public void setTipoCuidadosPaciente(TipoCuidadosPacienteEnum tipoCuidadosPaciente) {
		this.tipoCuidadosPaciente = tipoCuidadosPaciente;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof CuidadosPaciente))
			return false;
		
		return ((CuidadosPaciente)obj).getIdCuidadosPaciente() == this.idCuidadosPaciente;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
