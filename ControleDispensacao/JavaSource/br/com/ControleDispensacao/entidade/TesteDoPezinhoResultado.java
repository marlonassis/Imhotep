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

import br.com.ControleDispensacao.enums.TipoMetodoExameEnum;

@Entity
@Table(name = "tb_teste_do_pezinho_resultado")
public class TesteDoPezinhoResultado {
	
	private int idTesteDoPezinhoResultado;
	private TipoMetodoExameEnum metodo;
	private TesteDoPezinho testeDoPezinho;
	private TipoExame tipoExame;
	private Date dataCadastro;
	private String resultado;
	private Profissional profissionalCadastro;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_teste_do_pezinho_resultado_id_teste_do_pezinho_resultado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_teste_do_pezinho_resultado", unique = true, nullable = false)
	public int getIdTesteDoPezinhoResultado() {
		return idTesteDoPezinhoResultado;
	}
	public void setIdTesteDoPezinhoResultado(int idTesteDoPezinhoResultado) {
		this.idTesteDoPezinhoResultado = idTesteDoPezinhoResultado;
	}
	
	@Column(name = "cv_metodo")
	@Enumerated(EnumType.STRING)
	public TipoMetodoExameEnum getMetodo() {
		return metodo;
	}
	public void setMetodo(TipoMetodoExameEnum metodo) {
		this.metodo = metodo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_teste_do_pezinho")
	public TesteDoPezinho getTesteDoPezinho() {
		return testeDoPezinho;
	}
	public void setTesteDoPezinho(TesteDoPezinho testeDoPezinho) {
		this.testeDoPezinho = testeDoPezinho;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_exame")
	public TipoExame getTipoExame() {
		return tipoExame;
	}
	public void setTipoExame(TipoExame tipoExame) {
		this.tipoExame = tipoExame;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@Column(name = "cv_resultado")
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof TesteDoPezinhoResultado))
			return false;
		
		return ((TesteDoPezinhoResultado)obj).getIdTesteDoPezinhoResultado() == this.idTesteDoPezinhoResultado;
	}

	@Override
	public String toString() {
		return resultado;
	}
}
