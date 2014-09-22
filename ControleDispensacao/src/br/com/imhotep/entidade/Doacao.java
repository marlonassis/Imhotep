package br.com.imhotep.entidade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_doacao")
public class Doacao  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idDoacao;
	private Hospital hospital;
	private Date dataDoacao;
	private MovimentoLivro movimentoLivro;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_doacao_id_doacao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_doacao", unique = true, nullable = false)
	public int getIdDoacao() {
		return idDoacao;
	}
	public void setIdDoacao(int idDoacao) {
		this.idDoacao = idDoacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hospital")
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_doacao")
	public Date getDataDoacao() {
		return dataDoacao;
	}
	public void setDataDoacao(Date dataDoacao) {
		this.dataDoacao = dataDoacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movimento_livro")
	public MovimentoLivro getMovimentoLivro() {
		return movimentoLivro;
	}
	public void setMovimentoLivro(MovimentoLivro movimentoLivro) {
		this.movimentoLivro = movimentoLivro;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Doacao))
			return false;
		
		return ((Doacao)obj).getIdDoacao() == this.idDoacao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataDoacao.hashCode() + hospital.hashCode() + movimentoLivro.hashCode();
	}

	@Override
	public String toString() {
		return "Hospital: " + hospital.getNome() + ", Data: " + new SimpleDateFormat("dd/MM/yyyy").format(dataDoacao);
	}
}
