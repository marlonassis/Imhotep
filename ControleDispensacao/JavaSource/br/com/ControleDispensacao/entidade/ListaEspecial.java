package br.com.ControleDispensacao.entidade;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "lista_especial")
public class ListaEspecial {
	private int idListaEspecial;
	private Livro livro;
	private String lista;
	private String descricao;
	private TipoSituacaoEnum status;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataAlteracao;
	private Usuario usuarioAlteracao;
	private TipoStatusEnum receitaControlada;
	private TipoStatusEnum medicamentoControlado;

	@Id
	@GeneratedValue
	@Column(name = "id_lista_especial")
	public int getIdListaEspecial() {
		return this.idListaEspecial;
	}
	
	public void setIdListaEspecial(int idListaEspecial){
		this.idListaEspecial = idListaEspecial;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "livro_id_livro")
	public Livro getLivro() {
		return this.livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	@Column(name = "lista", length = 10)
	public String getLista() {
		return this.lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}
	
	@Column(name = "descricao", length = 60)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "status_2")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return this.status;
	}

	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_incl", length = 13)
	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_incl")
	public Usuario getUsuarioInclusao() {
		return this.usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_alt", length = 13)
	public Date getDataAlteracao() {
		return this.dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usua_alt")
	public Usuario getUsuarioAlteracao() {
		return this.usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
	
	@Column(name = "flg_receita_controlada")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getReceitaControlada() {
		return this.receitaControlada;
	}

	public void setReceitaControlada(TipoStatusEnum receitaControlada) {
		this.receitaControlada = receitaControlada;
	}
	
	@Column(name = "flg_medicamento_controlado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getMedicamentoControlado() {
		return this.medicamentoControlado;
	}

	public void setMedicamentoControlado(TipoStatusEnum medicamentoControlado) {
		this.medicamentoControlado = medicamentoControlado;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ListaEspecial))
			return false;
		
		return ((ListaEspecial)obj).getIdListaEspecial() == this.idListaEspecial;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
}
