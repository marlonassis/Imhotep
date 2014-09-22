package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoValorItemLaboratorioFormularioEnum;

@Entity
@Table(name = "tb_laboratorio_exame_analise_item", schema="laboratorio")
public class LaboratorioExameAnaliseItem implements Serializable {
	private static final long serialVersionUID = -1301178888167340545L;
	
	private int idLaboratorioExameAnaliseItem;
	private String nome;
	private String nomeFormulario;
	private String observacao;
	private String listaItens;
	private TipoValorItemLaboratorioFormularioEnum tipo;
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_exame_analise__id_laboratorio_exame_analise_seq2")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_exame_analise_item", unique = true, nullable = false)
	public int getIdLaboratorioExameAnaliseItem() {
		return idLaboratorioExameAnaliseItem;
	}

	public void setIdLaboratorioExameAnaliseItem(
			int idLaboratorioExameAnaliseItem) {
		this.idLaboratorioExameAnaliseItem = idLaboratorioExameAnaliseItem;
	}
	
	@Column(name = "cv_observacao")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_nome_formulario")
	public String getNomeFormulario() {
		return nomeFormulario;
	}

	public void setNomeFormulario(String nomeFormulario) {
		this.nomeFormulario = nomeFormulario;
	}
	
	@Column(name = "cv_lista_itens")
	public String getListaItens() {
		return listaItens;
	}

	public void setListaItens(String listaItens) {
		this.listaItens = listaItens;
	}
	
	@Column(name = "tp_tipo")
	@Enumerated(EnumType.STRING)
	public TipoValorItemLaboratorioFormularioEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoValorItemLaboratorioFormularioEnum tipo) {
		this.tipo = tipo;
	}

	@Transient
	public String getNomes(){
		return getNome().concat("/").concat(getNomeFormulario());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLaboratorioExameAnaliseItem;
		result = prime * result
				+ ((listaItens == null) ? 0 : listaItens.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((nomeFormulario == null) ? 0 : nomeFormulario.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		LaboratorioExameAnaliseItem other = (LaboratorioExameAnaliseItem) obj;
		if (idLaboratorioExameAnaliseItem != other.idLaboratorioExameAnaliseItem)
			return false;
		if (listaItens == null) {
			if (other.listaItens != null)
				return false;
		} else if (!listaItens.equals(other.listaItens))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeFormulario == null) {
			if (other.nomeFormulario != null)
				return false;
		} else if (!nomeFormulario.equals(other.nomeFormulario))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

}
