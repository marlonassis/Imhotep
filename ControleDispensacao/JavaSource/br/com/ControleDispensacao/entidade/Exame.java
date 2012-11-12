package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_exame")
public class Exame {
	
	private int idExame;
	private String valorReferencia;
	private String nome;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_exame_id_exame_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_exame", unique = true, nullable = false)
	public int getIdExame() {
		return this.idExame;
	}
	
	public void setIdExame(int idExame){
		this.idExame = idExame;
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Exame))
			return false;
		
		return ((Exame)obj).getIdExame() == this.idExame;
	}

	@Override
	public String toString() {
		return nome;
	}
	
}
