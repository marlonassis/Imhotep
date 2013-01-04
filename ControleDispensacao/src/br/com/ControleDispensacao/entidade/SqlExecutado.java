package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_sql_executado")
public class SqlExecutado {
	private int idSqlExecutado;
	private TipoStatusEnum executadoBaseProducao;
	private String sql;
	private Date dataCriacao;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_autoriza_menu_id_autoriza_menu_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_sql_executado", unique = true, nullable = false)
	public int getIdSqlExecutado() {
		return this.idSqlExecutado;
	}
	
	public void setIdSqlExecutado(int idSqlExecutado){
		this.idSqlExecutado = idSqlExecutado;
	}
	
	@Column(name = "cv_executado_base_producao")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getExecutadoBaseProducao(){
		return executadoBaseProducao;
	}
	
	public void setExecutadoBaseProducao(TipoStatusEnum executadoBaseProducao){
		this.executadoBaseProducao = executadoBaseProducao;
	}
	
	@Column(name = "cv_sql")
	public String getSql(){
		return sql;
	}
	
	public void setSql(String sql){
		this.sql = sql;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof SqlExecutado))
			return false;
		
		return ((SqlExecutado)obj).getIdSqlExecutado() == this.idSqlExecutado;
	}
	
}
