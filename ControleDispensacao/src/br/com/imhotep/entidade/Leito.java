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

import br.com.imhotep.enums.TipoStatusLeitoEnum;

@Entity
@Table(name = "tb_leito")
public class Leito implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idLeito;
	private Unidade unidade;
	private String nome;
	private Unidade idUnidadeEmprestimo;
	private Profissional profissionalCadastro;
	private Date dataCadastro;
	private String patrimonio;
	private TipoStatusLeitoEnum statusLeito;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_leito_id_leito_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_leito", unique = true, nullable = false)
	public int getIdLeito() {
		return idLeito;
	}
	public void setIdLeito(int idLeito) {
		this.idLeito = idLeito;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@Column(name="cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_emprestimo")
	public Unidade getUnidadeEmprestimo() {
		return idUnidadeEmprestimo;
	}
	public void setUnidadeEmprestimo(Unidade unidadeEmprestimo) {
		this.idUnidadeEmprestimo = unidadeEmprestimo;
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
	
	@Column(name="cv_patrimonio")
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	
	@Column(name = "tp_tipo_status_leito")
	@Enumerated(EnumType.STRING)
	public TipoStatusLeitoEnum getStatusLeito(){
		return statusLeito;
	}
	
	public void setStatusLeito(TipoStatusLeitoEnum statusLeito){
		this.statusLeito = statusLeito;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + idLeito;
		result = prime
				* result
				+ ((idUnidadeEmprestimo == null) ? 0 : idUnidadeEmprestimo
						.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((patrimonio == null) ? 0 : patrimonio.hashCode());
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
						.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
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
		Leito other = (Leito) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (idLeito != other.idLeito)
			return false;
		if (idUnidadeEmprestimo == null) {
			if (other.idUnidadeEmprestimo != null)
				return false;
		} else if (!idUnidadeEmprestimo.equals(other.idUnidadeEmprestimo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (patrimonio == null) {
			if (other.patrimonio != null)
				return false;
		} else if (!patrimonio.equals(other.patrimonio))
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		return true;
	}
	
	
	
}
