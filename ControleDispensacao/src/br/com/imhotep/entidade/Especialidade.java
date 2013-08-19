package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_especialidade")
public class Especialidade implements Serializable{
	private static final long serialVersionUID = 3040528721667731570L;
	
	private int idEspecialidade;
	private TipoConselho tipoConselho;
	private String descricao;
	private Especialidade especialidadePai;
	private Set<AutorizaMenu> menus;
	private Set<AutorizaPainel> paineis;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_especialidade_id_especialidade_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_especialidade", unique = true, nullable = false)
	public int getIdEspecialidade() {
		return idEspecialidade;
	}
	public void setIdEspecialidade(int idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_conselho")
	public TipoConselho getTipoConselho() {
		return tipoConselho;
	}
	public void setTipoConselho(TipoConselho tipoConselho) {
		this.tipoConselho = tipoConselho;
	}

	@Column(name = "cv_descricao", length = 50)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_especialidade_pai")
	public Especialidade getEspecialidadePai() {
		return especialidadePai;
	}
	public void setEspecialidadePai(Especialidade especialidadePai) {
		this.especialidadePai = especialidadePai;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "especialidade")
	public Set<AutorizaMenu> getMenus() {
		return menus;
	}
	public void setMenus(Set<AutorizaMenu> menus) {
		this.menus = menus;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "especialidade")
	public Set<AutorizaPainel> getPaineis() {
		return paineis;
	}
	public void setPaineis(Set<AutorizaPainel> paineis) {
		this.paineis = paineis;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Especialidade))
			return false;
		
		return ((Especialidade)obj).getIdEspecialidade() == this.idEspecialidade;
	}
	
	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + ((descricao == null) ? 0 : descricao.hashCode());
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
