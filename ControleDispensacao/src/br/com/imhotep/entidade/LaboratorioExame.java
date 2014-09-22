package br.com.imhotep.entidade;

import java.io.Serializable;
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

import br.com.imhotep.enums.TipoValorLaboratorioExameEnum;

@Entity
@Table(name = "tb_laboratorio_exame", schema="laboratorio")
public class LaboratorioExame implements Serializable {
	private static final long serialVersionUID = 4779696997116896151L;
	
	private int idLaboratorioExame;
	private String descricao;
	private String metodo;
	private String menemonico;
	private TipoValorLaboratorioExameEnum tipoValor;
	private Profissional profissionalCadastro;
	private Date dataCadastro;
	private Boolean bloqueado;
	private Boolean justificativaObrigatoria;
	private String referencia;
	private Integer codigo;
	private String sigla;
	private LaboratorioExameAnaliseFormulario formulario;
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_exame_id_laboratorio_exame_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_exame", unique = true, nullable = false)
	public int getIdLaboratorioExame() {
		return idLaboratorioExame;
	}
	public void setIdLaboratorioExame(int idLaboratorioExame) {
		this.idLaboratorioExame = idLaboratorioExame;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "cv_metodo")
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	
	@Column(name = "cv_menemonico")
	public String getMenemonico() {
		return menemonico;
	}
	public void setMenemonico(String menemonico) {
		this.menemonico = menemonico;
	}
	
	@Column(name = "tp_valor")
	@Enumerated(EnumType.STRING)
	public TipoValorLaboratorioExameEnum getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(TipoValorLaboratorioExameEnum tipoValor) {
		this.tipoValor = tipoValor;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Column(name = "bl_bloqueado")
	public Boolean getBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	@Column(name = "cv_referencia")
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	@Column(name = "in_codigo")
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "cv_sigla")
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(name = "bl_justificativa_obrigatorio")
	public Boolean getJustificativaObrigatoria() {
		return justificativaObrigatoria;
	}
	public void setJustificativaObrigatoria(Boolean justificativaObrigatoria) {
		this.justificativaObrigatoria = justificativaObrigatoria;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_exame_analise_formulario")
	public LaboratorioExameAnaliseFormulario getFormulario() {
		return formulario;
	}
	public void setFormulario(LaboratorioExameAnaliseFormulario formulario) {
		this.formulario = formulario;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bloqueado == null) ? 0 : bloqueado.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((formulario == null) ? 0 : formulario.hashCode());
		result = prime * result + idLaboratorioExame;
		result = prime
				* result
				+ ((justificativaObrigatoria == null) ? 0
						: justificativaObrigatoria.hashCode());
		result = prime * result
				+ ((menemonico == null) ? 0 : menemonico.hashCode());
		result = prime * result + ((metodo == null) ? 0 : metodo.hashCode());
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
						.hashCode());
		result = prime * result
				+ ((referencia == null) ? 0 : referencia.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		result = prime * result
				+ ((tipoValor == null) ? 0 : tipoValor.hashCode());
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
		LaboratorioExame other = (LaboratorioExame) obj;
		if (bloqueado == null) {
			if (other.bloqueado != null)
				return false;
		} else if (!bloqueado.equals(other.bloqueado))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (formulario == null) {
			if (other.formulario != null)
				return false;
		} else if (!formulario.equals(other.formulario))
			return false;
		if (idLaboratorioExame != other.idLaboratorioExame)
			return false;
		if (justificativaObrigatoria == null) {
			if (other.justificativaObrigatoria != null)
				return false;
		} else if (!justificativaObrigatoria
				.equals(other.justificativaObrigatoria))
			return false;
		if (menemonico == null) {
			if (other.menemonico != null)
				return false;
		} else if (!menemonico.equals(other.menemonico))
			return false;
		if (metodo == null) {
			if (other.metodo != null)
				return false;
		} else if (!metodo.equals(other.metodo))
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		if (tipoValor != other.tipoValor)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getCodigo() + " - " + getDescricao();
	}
	
}
