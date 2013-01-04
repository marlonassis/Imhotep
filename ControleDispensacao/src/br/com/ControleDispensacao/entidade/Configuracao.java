package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_configuracao")
public class Configuracao {
	private int idConfiguracao;
	private String nome;
	private String valor;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_configuracao_id_configuracao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_configuracao", unique = true, nullable = false)
	public int getIdConfiguracao() {
		return idConfiguracao;
	}
	
	public void setIdConfiguracao(int idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}
	
	@Column(name = "cv_valor")
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Configuracao))
			return false;
		
		return ((Configuracao)obj).getIdConfiguracao() == this.idConfiguracao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}

}
