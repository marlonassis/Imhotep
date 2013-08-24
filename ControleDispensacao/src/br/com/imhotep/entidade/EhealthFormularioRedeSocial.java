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

import br.com.imhotep.enums.TipoEhealthRedeSocialEnum;

@Entity
@Table(name = "tb_ehealth_formulario_rede_social")
public class EhealthFormularioRedeSocial {
	
	private int idEhealthFormularioRedeSocial;
	private EhealthFormulario ehealthFormulario;
	private TipoEhealthRedeSocialEnum tipoRedeSocial;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ehealth_formulario_rede_so_id_ehealth_formulario_rede_so_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_formulario_rede_social", unique = true, nullable = false)
	public int getIdEhealthFormularioRedeSocial() {
		return idEhealthFormularioRedeSocial;
	}

	public void setIdEhealthFormularioRedeSocial(int idEhealthFormularioRedeSocial) {
		this.idEhealthFormularioRedeSocial = idEhealthFormularioRedeSocial;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ehealth_formulario")
	public EhealthFormulario getEhealthFormulario() {
		return ehealthFormulario;
	}
	public void setEhealthFormulario(EhealthFormulario ehealthFormulario) {
		this.ehealthFormulario = ehealthFormulario;
	}
	
	@Column(name="tp_tipo_rede_social")
	@Enumerated(EnumType.STRING)
	public TipoEhealthRedeSocialEnum getTipoRedeSocial() {
		return tipoRedeSocial;
	}

	public void setTipoRedeSocial(TipoEhealthRedeSocialEnum tipoRedeSocial) {
		this.tipoRedeSocial = tipoRedeSocial;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EhealthFormularioRedeSocial))
			return false;
		
		return ((EhealthFormularioRedeSocial)obj).getIdEhealthFormularioRedeSocial() == this.idEhealthFormularioRedeSocial;
	}

}
