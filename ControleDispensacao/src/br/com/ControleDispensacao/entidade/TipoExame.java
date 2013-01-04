package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tipo_exame")
public class TipoExame {
	
	private int idTipoExame;
	private String valorReferencia;
	private String nome;
	private String sigla;
	private Float valor;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_tipo_exame_id_tipo_exame_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_tipo_exame", unique = true, nullable = false)
	public int getIdTipoExame() {
		return this.idTipoExame;
	}
	
	public void setIdTipoExame(int idTipoExame){
		this.idTipoExame = idTipoExame;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_valor_referencia")
	public String getValorReferencia() {
		return valorReferencia;
	}
	public void setValorReferencia(String valorReferencia) {
		this.valorReferencia = valorReferencia;
	}
	
	@Column(name = "cv_sigla")
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(name = "db_valor")
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof TipoExame))
			return false;
		
		return ((TipoExame)obj).getIdTipoExame() == this.idTipoExame;
	}

	@Override
	public String toString() {
		return nome;
	}
	
}
