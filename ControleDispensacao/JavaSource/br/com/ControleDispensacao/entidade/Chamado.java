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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoBooleanEnum;

@Entity
@Table(name = "tb_chamado")
public class Chamado {
	
	private int idChamado;
	private String descricao;
	private String assunto;
	private Usuario usuario;
	private Date dataChamado;
	private TipoBooleanEnum atendido;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_chamado_id_chamado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_chamado", unique = true, nullable = false)
	public int getIdChamado() {
		return idChamado;
	}
	public void setIdChamado(int idChamado) {
		this.idChamado = idChamado;
	}
	
	@Column(name = "ds_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "ds_assunto")
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_data_chamado")
	public Date getDataChamado() {
		return dataChamado;
	}
	public void setDataChamado(Date dataChamado) {
		this.dataChamado = dataChamado;
	}
	
	@Column(name = "tp_atendido")
	@Enumerated(EnumType.STRING)
	public TipoBooleanEnum getAtendido() {
		return atendido;
	}
	public void setAtendido(TipoBooleanEnum atendido) {
		this.atendido = atendido;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Chamado))
			return false;
		
		return ((Chamado)obj).getIdChamado() == this.idChamado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return "Assunto: ".concat(assunto);
	}
}
