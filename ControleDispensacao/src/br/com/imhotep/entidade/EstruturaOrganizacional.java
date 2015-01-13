package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_estrutura_organizacional", schema="administrativo")
public class EstruturaOrganizacional  implements Serializable {
	private static final long serialVersionUID = -8711387235024270299L;
	
	private int idEstruturaOrganizacional;
	private String nome;
	private String descricao;
	private EstruturaOrganizacional estruturaPai;
	private boolean solicitacaMaterial;
	private boolean solicitacaMedicamento;
	
	public EstruturaOrganizacional() {
		super();
	}
	
	public EstruturaOrganizacional(int idEstruturaOrganizacional, String nome, String descricao,
			EstruturaOrganizacional estruturaPai, boolean solicitacaMaterial,
			boolean solicitacaMedicamento) {
		super();
		this.idEstruturaOrganizacional = idEstruturaOrganizacional;
		this.nome = nome;
		this.descricao = descricao;
		this.estruturaPai = estruturaPai;
		this.solicitacaMaterial = solicitacaMaterial;
		this.solicitacaMedicamento = solicitacaMedicamento;
	}
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_estrutura_organizacional_id_estrutura_organizacional_seq")
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estrutura_organizacional", unique = true, nullable = false)
	public int getIdEstruturaOrganizacional() {
		return idEstruturaOrganizacional;
	}
	public void setIdEstruturaOrganizacional(int idEstruturaOrganizacional) {
		this.idEstruturaOrganizacional = idEstruturaOrganizacional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_pai")
	public EstruturaOrganizacional getEstruturaPai() {
		return estruturaPai;
	}
	public void setEstruturaPai(EstruturaOrganizacional estruturaPai) {
		this.estruturaPai = estruturaPai;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "bl_solicita_material")
	public boolean getSolicitacaMaterial() {
		return solicitacaMaterial;
	}
	public void setSolicitacaMaterial(boolean solicitacaMaterial) {
		this.solicitacaMaterial = solicitacaMaterial;
	}
	
	@Column(name = "bl_solicita_medicamento")
	public boolean getSolicitacaMedicamento() {
		return solicitacaMedicamento;
	}
	public void setSolicitacaMedicamento(boolean solicitacaMedicamento) {
		this.solicitacaMedicamento = solicitacaMedicamento;
	}
	
	@Transient
	public String getNomeComPai(){
		if(getEstruturaPai() != null){
			return getNome().concat(" (").concat(getEstruturaPai().getNome()).concat(")");
		}
		return getNome();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((estruturaPai == null) ? 0 : estruturaPai.hashCode());
		result = prime * result + idEstruturaOrganizacional;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (solicitacaMaterial ? 1231 : 1237);
		result = prime * result + (solicitacaMedicamento ? 1231 : 1237);
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
		EstruturaOrganizacional other = (EstruturaOrganizacional) obj;
		if (estruturaPai == null) {
			if (other.estruturaPai != null)
				return false;
		} else if (!estruturaPai.equals(other.estruturaPai))
			return false;
		if (idEstruturaOrganizacional != other.idEstruturaOrganizacional)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (solicitacaMaterial != other.solicitacaMaterial)
			return false;
		if (solicitacaMedicamento != other.solicitacaMedicamento)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getNome();
	}
	
}
