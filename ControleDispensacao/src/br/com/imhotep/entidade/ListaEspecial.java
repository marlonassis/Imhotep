package br.com.imhotep.entidade;

import java.io.Serializable;

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

import br.com.imhotep.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_lista_especial")
public class ListaEspecial implements Serializable {
	private static final long serialVersionUID = -6189344216323087406L;
	
	private int idListaEspecial;
	private Livro livro;
	private String lista;
	private String descricao;
	private TipoStatusEnum receitaControlada;
	private TipoStatusEnum medicamentoControlado;

	@SequenceGenerator(name = "generator", sequenceName = "public.tb_lista_especial_id_lista_especial_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_lista_especial", unique = true, nullable = false)
	public int getIdListaEspecial() {
		return this.idListaEspecial;
	}
	
	public void setIdListaEspecial(int idListaEspecial){
		this.idListaEspecial = idListaEspecial;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_livro")
	public Livro getLivro() {
		return this.livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	@Column(name = "cv_lista", length = 10)
	public String getLista() {
		return this.lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}
	
	@Column(name = "cv_descricao", length = 60)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "tp_receita_controlada")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getReceitaControlada() {
		return this.receitaControlada;
	}

	public void setReceitaControlada(TipoStatusEnum receitaControlada) {
		this.receitaControlada = receitaControlada;
	}
	
	@Column(name = "tp_medicamento_controlado")
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
