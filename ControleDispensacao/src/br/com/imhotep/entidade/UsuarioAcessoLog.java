package br.com.imhotep.entidade;

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

import br.com.imhotep.enums.TipoUsuarioLogEnum;

@Entity
@Table(name = "tb_usuario_acesso_log")
public class UsuarioAcessoLog {
	private int idUsuarioLog;
	private Usuario usuario;
	private Date dataLog;
	private Date dataUltimoMovimentoSessao;
	private String sessao;
	private TipoUsuarioLogEnum tipoLog;
	private int tempoSessao;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_usuario_acesso_log_id_usuario_acesso_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_usuario_acesso_log", unique = true, nullable = false)
	public int getIdUsuarioLog() {
		return this.idUsuarioLog;
	}
	
	public void setIdUsuarioLog(int idUsuarioLog){
		this.idUsuarioLog = idUsuarioLog;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_log")
	public Date getDataLog() {
		return this.dataLog;
	}

	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_ultimo_movimento_sessao")
	public Date getDataUltimoMovimentoSessao() {
		return this.dataUltimoMovimentoSessao;
	}
	
	public void setDataUltimoMovimentoSessao(Date dataUltimoMovimentoSessao) {
		this.dataUltimoMovimentoSessao = dataUltimoMovimentoSessao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Column(name = "cv_sessao")
	public String getSessao() {
		return this.sessao;
	}

	public void setSessao(String sessao) {
		this.sessao = sessao;
	}
	
	@Column(name = "in_tempo_sessao")
	public int getTempoSessao() {
		return this.tempoSessao;
	}

	public void setTempoSessao(int tempoSessao) {
		this.tempoSessao = tempoSessao;
	}
	
	@Column(name = "tp_tipo_log")
	@Enumerated(EnumType.STRING)
	public TipoUsuarioLogEnum getTipoLog() {
		return this.tipoLog;
	}

	public void setTipoLog(TipoUsuarioLogEnum tipoLog) {
		this.tipoLog = tipoLog;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof UsuarioAcessoLog))
			return false;
		
		return ((UsuarioAcessoLog)obj).getIdUsuarioLog() == this.idUsuarioLog;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + sessao.hashCode() + dataLog.hashCode();
	}

	@Override
	public String toString() {
		return sessao;
	}
	
}
