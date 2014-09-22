package br.com.imhotep.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoEhealthPresencaWebEnum;

@Entity
@Table(name = "tb_ehealth_formulario", schema="ehealth")
public class EhealthFormulario {
	
	private int idEhealthFormulario;
	private boolean possuiSiteProprio;
	private TipoEhealthPresencaWebEnum tipoPresencaWeb;
	private boolean informacaoInstitucionalHospital;
	private boolean informacaoServicoPrestado;
	private boolean informacaoPrevencaoCuidadosSaude;
	private boolean procedimentosEmergenciaMedica;
	private boolean enderecoEletronicoRecepcao;
	private boolean marcacaoConsulta;
	private boolean tabelaCustoServico;
	private boolean localizacaoMeiosAcesso;
	private boolean corpoClinico;
	private boolean rastreioMedicoOnline;
	private boolean consultaOnline;
	private boolean disponibilizacaoFormularioDownload;
	private boolean disponibilizacaoFormulariosOnline;
	private boolean acessibilidade;
	private String observacao;
	private Set<EhealthFormularioRedeSocial> redesSociais;
	private Set<EhealthFormularioTecnologia> tecnologias;
	private EhealthEstabelecimento ehealthEstabelecimento;
	private Profissional pesquisador;
	private Date dataCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "ehealth.tb_ehealth_formulario_id_ehealth_formulario_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_formulario", unique = true, nullable = false)
	public int getIdEhealthFormulario() {
		return idEhealthFormulario;
	}

	public void setIdEhealthFormulario(int idEhealthFormulario) {
		this.idEhealthFormulario = idEhealthFormulario;
	}

	@Column(name="bl_possui_site_proprio")
	public boolean getPossuiSiteProprio() {
		return possuiSiteProprio;
	}

	public void setPossuiSiteProprio(boolean possuiSiteProprio) {
		this.possuiSiteProprio = possuiSiteProprio;
	}

	@Column(name="tp_tipo_presenca_web")
	@Enumerated(EnumType.STRING)
	public TipoEhealthPresencaWebEnum getTipoPresencaWeb() {
		return tipoPresencaWeb;
	}

	public void setTipoPresencaWeb(TipoEhealthPresencaWebEnum tipoPresencaWeb) {
		this.tipoPresencaWeb = tipoPresencaWeb;
	}

	@Column(name="bl_informacao_institucional_hospital")
	public boolean getInformacaoInstitucionalHospital() {
		return informacaoInstitucionalHospital;
	}

	public void setInformacaoInstitucionalHospital(boolean informacaoInstitucionalHospital) {
		this.informacaoInstitucionalHospital = informacaoInstitucionalHospital;
	}

	@Column(name="bl_informacao_servico_prestado")
	public boolean getInformacaoServicoPrestado() {
		return informacaoServicoPrestado;
	}

	public void setInformacaoServicoPrestado(boolean informacaoServicoPrestado) {
		this.informacaoServicoPrestado = informacaoServicoPrestado;
	}

	@Column(name="bl_informacao_prevencao_cuidados_saude")
	public boolean getInformacaoPrevencaoCuidadosSaude() {
		return informacaoPrevencaoCuidadosSaude;
	}

	public void setInformacaoPrevencaoCuidadosSaude(boolean informacaoPrevencaoCuidadosSaude) {
		this.informacaoPrevencaoCuidadosSaude = informacaoPrevencaoCuidadosSaude;
	}

	@Column(name="bl_procedimentos_emergencia_medica")
	public boolean getProcedimentosEmergenciaMedica() {
		return procedimentosEmergenciaMedica;
	}

	public void setProcedimentosEmergenciaMedica(boolean procedimentosEmergenciaMedica) {
		this.procedimentosEmergenciaMedica = procedimentosEmergenciaMedica;
	}

	@Column(name="bl_endereco_eletronico_recepcao")
	public boolean getEnderecoEletronicoRecepcao() {
		return enderecoEletronicoRecepcao;
	}

	public void setEnderecoEletronicoRecepcao(boolean enderecoEletronicoRecepcao) {
		this.enderecoEletronicoRecepcao = enderecoEletronicoRecepcao;
	}

	@Column(name="bl_marcacao_consulta")
	public boolean getMarcacaoConsulta() {
		return marcacaoConsulta;
	}

	public void setMarcacaoConsulta(boolean marcacaoConsulta) {
		this.marcacaoConsulta = marcacaoConsulta;
	}

	@Column(name="bl_tabela_custo_servico")
	public boolean getTabelaCustoServico() {
		return tabelaCustoServico;
	}

	public void setTabelaCustoServico(boolean tabelaCustoServico) {
		this.tabelaCustoServico = tabelaCustoServico;
	}

	@Column(name="bl_localizacao_meios_acesso")
	public boolean getLocalizacaoMeiosAcesso() {
		return localizacaoMeiosAcesso;
	}

	public void setLocalizacaoMeiosAcesso(boolean localizacaoMeiosAcesso) {
		this.localizacaoMeiosAcesso = localizacaoMeiosAcesso;
	}

	@Column(name="bl_corpo_clinico")
	public boolean getCorpoClinico() {
		return corpoClinico;
	}

	public void setCorpoClinico(boolean corpoClinico) {
		this.corpoClinico = corpoClinico;
	}

	@Column(name="bl_rastreio_medico_online")
	public boolean getRastreioMedicoOnline() {
		return rastreioMedicoOnline;
	}

	public void setRastreioMedicoOnline(boolean rastreioMedicoOnline) {
		this.rastreioMedicoOnline = rastreioMedicoOnline;
	}

	@Column(name="bl_consulta_online")
	public boolean getConsultaOnline() {
		return consultaOnline;
	}

	public void setConsultaOnline(boolean consultaOnline) {
		this.consultaOnline = consultaOnline;
	}

	@Column(name="bl_disponibilizacao_formulario_download")
	public boolean getDisponibilizacaoFormularioDownload() {
		return disponibilizacaoFormularioDownload;
	}

	public void setDisponibilizacaoFormularioDownload(boolean disponibilizacaoFormularioDownload) {
		this.disponibilizacaoFormularioDownload = disponibilizacaoFormularioDownload;
	}

	@Column(name="bl_disponibilizacao_formulario_online")
	public boolean getDisponibilizacaoFormulariosOnline() {
		return disponibilizacaoFormulariosOnline;
	}

	public void setDisponibilizacaoFormulariosOnline(boolean disponibilizacaoFormulariosOnline) {
		this.disponibilizacaoFormulariosOnline = disponibilizacaoFormulariosOnline;
	}
	
	@Column(name="bl_acessibilidade")
	public boolean getAcessibilidade() {
		return acessibilidade;
	}

	public void setAcessibilidade(boolean acessibilidade) {
		this.acessibilidade = acessibilidade;
	}

	@Column(name="cv_observacao")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ehealthFormulario")
	public Set<EhealthFormularioRedeSocial> getRedesSociais() {
		return redesSociais;
	}

	public void setRedesSociais(Set<EhealthFormularioRedeSocial> redesSociais) {
		this.redesSociais = redesSociais;
	}
	
	@Transient
	public List<EhealthFormularioRedeSocial> getRedesSociaisList() {
		if(redesSociais != null)
			return new ArrayList<EhealthFormularioRedeSocial>(redesSociais);
		return new ArrayList<EhealthFormularioRedeSocial>();
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ehealthFormulario")
	public Set<EhealthFormularioTecnologia> getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(Set<EhealthFormularioTecnologia> tecnologias) {
		this.tecnologias = tecnologias;
	}
	
	@Transient
	public List<EhealthFormularioTecnologia> getTecnologiasList() {
		if(tecnologias != null)
			return new ArrayList<EhealthFormularioTecnologia>(tecnologias);
		return new ArrayList<EhealthFormularioTecnologia>();
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ehealth_estabelecimento")
	public EhealthEstabelecimento getEhealthEstabelecimento() {
		return ehealthEstabelecimento;
	}
	public void setEhealthEstabelecimento(EhealthEstabelecimento ehealthEstabelecimento) {
		this.ehealthEstabelecimento = ehealthEstabelecimento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pesquisador")
	public Profissional getPesquisador() {
		return pesquisador;
	}
	public void setPesquisador(Profissional pesquisador) {
		this.pesquisador = pesquisador;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (acessibilidade ? 1231 : 1237);
		result = prime * result + (consultaOnline ? 1231 : 1237);
		result = prime * result + (corpoClinico ? 1231 : 1237);
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result
				+ (disponibilizacaoFormularioDownload ? 1231 : 1237);
		result = prime * result
				+ (disponibilizacaoFormulariosOnline ? 1231 : 1237);
		result = prime
				* result
				+ ((ehealthEstabelecimento == null) ? 0
						: ehealthEstabelecimento.hashCode());
		result = prime * result + (enderecoEletronicoRecepcao ? 1231 : 1237);
		result = prime * result + idEhealthFormulario;
		result = prime * result
				+ (informacaoInstitucionalHospital ? 1231 : 1237);
		result = prime * result
				+ (informacaoPrevencaoCuidadosSaude ? 1231 : 1237);
		result = prime * result + (informacaoServicoPrestado ? 1231 : 1237);
		result = prime * result + (localizacaoMeiosAcesso ? 1231 : 1237);
		result = prime * result + (marcacaoConsulta ? 1231 : 1237);
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result
				+ ((pesquisador == null) ? 0 : pesquisador.hashCode());
		result = prime * result + (possuiSiteProprio ? 1231 : 1237);
		result = prime * result + (procedimentosEmergenciaMedica ? 1231 : 1237);
		result = prime * result + (rastreioMedicoOnline ? 1231 : 1237);
		result = prime * result + (tabelaCustoServico ? 1231 : 1237);
		result = prime * result
				+ ((tipoPresencaWeb == null) ? 0 : tipoPresencaWeb.hashCode());
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
		EhealthFormulario other = (EhealthFormulario) obj;
		if (acessibilidade != other.acessibilidade)
			return false;
		if (consultaOnline != other.consultaOnline)
			return false;
		if (corpoClinico != other.corpoClinico)
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (disponibilizacaoFormularioDownload != other.disponibilizacaoFormularioDownload)
			return false;
		if (disponibilizacaoFormulariosOnline != other.disponibilizacaoFormulariosOnline)
			return false;
		if (ehealthEstabelecimento == null) {
			if (other.ehealthEstabelecimento != null)
				return false;
		} else if (!ehealthEstabelecimento.equals(other.ehealthEstabelecimento))
			return false;
		if (enderecoEletronicoRecepcao != other.enderecoEletronicoRecepcao)
			return false;
		if (idEhealthFormulario != other.idEhealthFormulario)
			return false;
		if (informacaoInstitucionalHospital != other.informacaoInstitucionalHospital)
			return false;
		if (informacaoPrevencaoCuidadosSaude != other.informacaoPrevencaoCuidadosSaude)
			return false;
		if (informacaoServicoPrestado != other.informacaoServicoPrestado)
			return false;
		if (localizacaoMeiosAcesso != other.localizacaoMeiosAcesso)
			return false;
		if (marcacaoConsulta != other.marcacaoConsulta)
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (pesquisador == null) {
			if (other.pesquisador != null)
				return false;
		} else if (!pesquisador.equals(other.pesquisador))
			return false;
		if (possuiSiteProprio != other.possuiSiteProprio)
			return false;
		if (procedimentosEmergenciaMedica != other.procedimentosEmergenciaMedica)
			return false;
		if (rastreioMedicoOnline != other.rastreioMedicoOnline)
			return false;
		if (tabelaCustoServico != other.tabelaCustoServico)
			return false;
		if (tipoPresencaWeb != other.tipoPresencaWeb)
			return false;
		return true;
	}
	
}
