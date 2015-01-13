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

@Entity
@Table(name = "tb_acesso_lotacao_variavel", schema="controle")
public class AcessoLotacaoVariavel implements Serializable {
	private static final long serialVersionUID = -5305172535850887044L;
	
	private int idAcessoLotacaoVariavel;
	private AcessoLotacao acessoLotacao;
	private MenuVariavel menuVariavel;
	
	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_acesso_lotacao_variavel_id_acesso_lotacao_variavel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_acesso_lotacao_variavel", unique = true, nullable = false)
	public int getIdAcessoLotacaoVariavel() {
		return idAcessoLotacaoVariavel;
	}
	
	public void setIdAcessoLotacaoVariavel(int idAcessoLotacaoVariavel) {
		this.idAcessoLotacaoVariavel = idAcessoLotacaoVariavel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu_variavel")
	public MenuVariavel getMenuVariavel() {
		return menuVariavel;
	}
	
	public void setMenuVariavel(MenuVariavel menuVariavel) {
		this.menuVariavel = menuVariavel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_acesso_lotacao")
	public AcessoLotacao getAcessoLotacao() {
		return acessoLotacao;
	}
	
	public void setAcessoLotacao(AcessoLotacao acessoLotacao) {
		this.acessoLotacao = acessoLotacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acessoLotacao == null) ? 0 : acessoLotacao.hashCode());
		result = prime * result + idAcessoLotacaoVariavel;
		result = prime * result
				+ ((menuVariavel == null) ? 0 : menuVariavel.hashCode());
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
		AcessoLotacaoVariavel other = (AcessoLotacaoVariavel) obj;
		if (acessoLotacao == null) {
			if (other.acessoLotacao != null)
				return false;
		} else if (!acessoLotacao.equals(other.acessoLotacao))
			return false;
		if (idAcessoLotacaoVariavel != other.idAcessoLotacaoVariavel)
			return false;
		if (menuVariavel == null) {
			if (other.menuVariavel != null)
				return false;
		} else if (!menuVariavel.equals(other.menuVariavel))
			return false;
		return true;
	}

}
