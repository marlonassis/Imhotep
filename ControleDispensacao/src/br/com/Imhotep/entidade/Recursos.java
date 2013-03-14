package br.com.Imhotep.entidade;

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

import br.com.Imhotep.enums.TipoCategoriaRecursoEnum;
import br.com.Imhotep.enums.TipoEstadoAtualRecursoEnum;
import br.com.Imhotep.enums.TipoLicensaRecursoEnum;
import br.com.Imhotep.enums.TipoProximoEstadoRecursoEnum;
import br.com.Imhotep.enums.TipoRecursoEnum;

@Entity
@Table(name = "tb_recursos")
public class Recursos {
	
	private int idRecurso;
	private Date dataUltimaAtualizacao;
	private Date dataUltimaAuditoria;
	private TipoCategoriaRecursoEnum tipoCategoria;
	private TipoRecursoEnum tipo;
	private String modelo;
	private Date dataValidadeGarantia;
	private String numeroVersao;
	private Unidade unidadeInstalacao;
	private Profissional profissionalResponsavel;
	private Fornecedor fornecedor;
	private TipoLicensaRecursoEnum tipoLicenca;
	private Date dataAquisicao;
	private Date dataAceite;
	private TipoEstadoAtualRecursoEnum tipoEstadoAtual;
	private TipoProximoEstadoRecursoEnum tipoProximoEstado;
	private Recursos recursoPai;
	private String componentesAgregados;
	private String componentesPertencente;
	private String relacao;
	private String comentario;
	private String codigoIdentificacao;
	private String codigoInventario;
	private String numeroSerie;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_recursos_id_recursos_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_recursos", unique = true, nullable = false)
	public int getIdRecurso() {
		return idRecurso;
	}
	public void setIdRecurso(int idRecurso) {
		this.idRecurso = idRecurso;
	}
	
	@Column(name = "cv_codigo_identificacao")
	public String getCodigoIdentificacao() {
		return codigoIdentificacao;
	}
	public void setCodigoIdentificacao(String codigoIdentificacao) {
		this.codigoIdentificacao = codigoIdentificacao;
	}
	
	@Column(name = "cv_codigo_inventario")
	public String getCodigoInventario() {
		return codigoInventario;
	}
	public void setCodigoInventario(String codigoInventario) {
		this.codigoInventario = codigoInventario;
	}
	
	@Column(name = "cv_numero_serie")
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "cv_modelo")
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	@Column(name = "cv_numero_versao")
	public String getNumeroVersao() {
		return numeroVersao;
	}
	public void setNumeroVersao(String numeroVersao) {
		this.numeroVersao = numeroVersao;
	}
	
	@Column(name = "cv_componentes_agregados")
	public String getComponentesAgregados() {
		return componentesAgregados;
	}
	public void setComponentesAgregados(String componentesAgregados) {
		this.componentesAgregados = componentesAgregados;
	}
	
	@Column(name = "cv_componentes_pertencente")
	public String getComponentesPertencente() {
		return componentesPertencente;
	}
	public void setComponentesPertencente(String componentesPertencente) {
		this.componentesPertencente = componentesPertencente;
	}
	
	@Column(name = "cv_relacao")
	public String getRelacao() {
		return relacao;
	}
	public void setRelacao(String relacao) {
		this.relacao = relacao;
	}
	
	@Column(name = "cv_comentario")
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_ultima_atualizacao")
	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_ultima_auditoria")
	public Date getDataUltimaAuditoria() {
		return dataUltimaAuditoria;
	}
	public void setDataUltimaAuditoria(Date dataUltimaAuditoria) {
		this.dataUltimaAuditoria = dataUltimaAuditoria;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_aquisicao")
	public Date getDataAquisicao() {
		return dataAquisicao;
	}
	public void setDataAquisicao(Date dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_aceite")
	public Date getDataAceite() {
		return dataAceite;
	}
	public void setDataAceite(Date dataAceite) {
		this.dataAceite = dataAceite;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_validade_garantia")
	public Date getDataValidadeGarantia() {
		return dataValidadeGarantia;
	}
	public void setDataValidadeGarantia(Date dataValidadeGarantia) {
		this.dataValidadeGarantia = dataValidadeGarantia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_instalacao")
	public Unidade getUnidadeInstalacao() {
		return unidadeInstalacao;
	}
	public void setUnidadeInstalacao(Unidade unidadeInstalacao) {
		this.unidadeInstalacao = unidadeInstalacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_responsavel")
	public Profissional getProfissionalResponsavel() {
		return profissionalResponsavel;
	}
	public void setProfissionalResponsavel(Profissional profissionalResponsavel) {
		this.profissionalResponsavel = profissionalResponsavel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fornecedor")
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_recurso_pai")
	public Recursos getRecursoPai() {
		return recursoPai;
	}
	public void setRecursoPai(Recursos recursoPai) {
		this.recursoPai = recursoPai;
	}
	
	@Column(name = "tp_categoria")
	@Enumerated(EnumType.STRING)
	public TipoCategoriaRecursoEnum getTipoCategoria() {
		return tipoCategoria;
	}
	public void setTipoCategoria(TipoCategoriaRecursoEnum tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}
	
	@Column(name = "tp_tipo")
	@Enumerated(EnumType.STRING)
	public TipoRecursoEnum getTipo() {
		return tipo;
	}
	public void setTipo(TipoRecursoEnum tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "tp_tipo_licenca")
	@Enumerated(EnumType.STRING)
	public TipoLicensaRecursoEnum getTipoLicenca() {
		return tipoLicenca;
	}
	public void setTipoLicenca(TipoLicensaRecursoEnum tipoLicenca) {
		this.tipoLicenca = tipoLicenca;
	}
	
	@Column(name = "tp_estado_atual")
	@Enumerated(EnumType.STRING)
	public TipoEstadoAtualRecursoEnum getTipoEstadoAtual() {
		return tipoEstadoAtual;
	}
	public void setTipoEstadoAtual(TipoEstadoAtualRecursoEnum tipoEstadoAtual) {
		this.tipoEstadoAtual = tipoEstadoAtual;
	}
	
	@Column(name = "tp_proximo_estado")
	@Enumerated(EnumType.STRING)
	public TipoProximoEstadoRecursoEnum getTipoProximoEstado() {
		return tipoProximoEstado;
	}
	public void setTipoProximoEstado(TipoProximoEstadoRecursoEnum tipoProximoEstado) {
		this.tipoProximoEstado = tipoProximoEstado;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Recursos))
			return false;
		
		return ((Recursos)obj).getIdRecurso() == this.idRecurso;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + unidadeInstalacao.hashCode()+dataAquisicao.hashCode();
	}

	@Override
	public String toString() {
		return modelo;
	}
}
