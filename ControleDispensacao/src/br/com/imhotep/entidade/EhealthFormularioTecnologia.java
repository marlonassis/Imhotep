package br.com.imhotep.entidade;

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

import br.com.imhotep.enums.TipoEhealthTipoTecnologiaEnum;

@Entity
@Table(name = "tb_ehealth_formulario_tecnologia")
public class EhealthFormularioTecnologia {
	
	private int idEhealthFormularioTecnologia;
	private EhealthFormulario ehealthFormulario;
	private TipoEhealthTipoTecnologiaEnum tipoTecnologia;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ehealth_formulario_tecnolo_id_ehealth_formulario_tecnolo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_formulario_tecnologia", unique = true, nullable = false)
	public int getIdEhealthFormularioTecnologia() {
		return idEhealthFormularioTecnologia;
	}

	public void setIdEhealthFormularioTecnologia(int idEhealthFormularioTecnologia) {
		this.idEhealthFormularioTecnologia = idEhealthFormularioTecnologia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ehealth_formulario")
	public EhealthFormulario getEhealthFormulario() {
		return ehealthFormulario;
	}
	public void setEhealthFormulario(EhealthFormulario ehealthFormulario) {
		this.ehealthFormulario = ehealthFormulario;
	}
	
	@Column(name="tp_tipo_tecnologia")
	@Enumerated(EnumType.STRING)
	public TipoEhealthTipoTecnologiaEnum getTipoTecnologia() {
		return tipoTecnologia;
	}

	public void setTipoTecnologia(TipoEhealthTipoTecnologiaEnum tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EhealthFormularioTecnologia))
			return false;
		
		return ((EhealthFormularioTecnologia)obj).getIdEhealthFormularioTecnologia() == this.idEhealthFormularioTecnologia;
	}

}
