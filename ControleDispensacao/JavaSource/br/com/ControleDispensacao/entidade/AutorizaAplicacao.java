package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_autoriza_aplicacao")
public class AutorizaAplicacao {
	private int idAutorizaAplicacao;
	private Usuario usuario;
	private Aplicacao aplicacao;
	
	@Id
	@GeneratedValue
	@Column(name = "id_autoriza_aplicacao")
	public int getIdAutorizaAplicacao() {
		return this.idAutorizaAplicacao;
	}
	
	public void setIdAutorizaAplicacao(int idAutorizaAplicacao){
		this.idAutorizaAplicacao = idAutorizaAplicacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_aplicacao")
	public Aplicacao getAplicacao(){
		return aplicacao;
	}
	
	public void setAplicacao(Aplicacao aplicacao){
		this.aplicacao = aplicacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AutorizaAplicacao))
			return false;
		
		return ((AutorizaAplicacao)obj).getIdAutorizaAplicacao() == this.idAutorizaAplicacao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + usuario.hashCode();
	}

	@Override
	public String toString() {
		return usuario.getLogin().concat(" - ").concat(aplicacao.getDescricao());
	}
	
}
