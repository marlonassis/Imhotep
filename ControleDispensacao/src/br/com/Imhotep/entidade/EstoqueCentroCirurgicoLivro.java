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
import javax.persistence.Transient;

import br.com.Imhotep.enums.TipoMovimentacaoEnum;

@Entity
@Table(name = "tb_estoque_centro_cirurgico_livro")
public class EstoqueCentroCirurgicoLivro {
	private int idEstoqueCentroCirurgicoLivro;
	private EstoqueCentroCirurgico estoqueCentroCirurgico;
	private Integer quantidadeMovimentacao;
	private Integer saldoAtual;
	private TipoMovimentacaoEnum tipoMovimentacao;
	private Integer saldoAnterior;
	private Date dataMovimento;
	private Profissional profissionalMovimentacao;
	private String nomePaciente;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estoque_centro_cirurgico_l_id_estoque_centro_cirurgico_l_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estoque_centro_cirurgico_livro", unique = true, nullable = false)
	public int getIdEstoqueCentroCirurgicoLivro() {
		return idEstoqueCentroCirurgicoLivro;
	}
	public void setIdEstoqueCentroCirurgicoLivro(int idEstoqueCentroCirurgicoLivro) {
		this.idEstoqueCentroCirurgicoLivro = idEstoqueCentroCirurgicoLivro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estoque_centro_cirurgico")
	public EstoqueCentroCirurgico getEstoqueCentroCirurgico() {
		return estoqueCentroCirurgico;
	}
	public void setEstoqueCentroCirurgico(
			EstoqueCentroCirurgico estoqueCentroCirurgico) {
		this.estoqueCentroCirurgico = estoqueCentroCirurgico;
	}
	
	@Transient
	public Integer getQuantidadeMovimentacao() {
		return quantidadeMovimentacao;
	}
	public void setQuantidadeMovimentacao(Integer quantidadeMovimentacao) {
		this.quantidadeMovimentacao = quantidadeMovimentacao;
	}
	
	@Column(name = "in_saldo_atual")
	public Integer getSaldoAtual() {
		return saldoAtual;
	}
	public void setSaldoAtual(Integer saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
	
	@Column(name = "tp_movimentacao")
	@Enumerated(EnumType.STRING)
	public TipoMovimentacaoEnum getTipoMovimentacao() {
		return tipoMovimentacao;
	}
	public void setTipoMovimentacao(TipoMovimentacaoEnum tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}
	
	@Column(name = "in_saldo_anterior")
	public Integer getSaldoAnterior() {
		return saldoAnterior;
	}
	public void setSaldoAnterior(Integer saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_movimento")
	public Date getDataMovimento() {
		return dataMovimento;
	}
	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_movimentaca")
	public Profissional getProfissionalMovimentacao() {
		return profissionalMovimentacao;
	}
	public void setProfissionalMovimentacao(Profissional profissionalMovimentacao) {
		this.profissionalMovimentacao = profissionalMovimentacao;
	}
	
	@Column(name = "cv_nome_paciente")
	public String getNomePaciente() {
		return nomePaciente;
	}
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EstoqueCentroCirurgicoLivro))
			return false;
		
		return ((EstoqueCentroCirurgicoLivro)obj).getIdEstoqueCentroCirurgicoLivro() == this.idEstoqueCentroCirurgicoLivro;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataMovimento.hashCode();
	}

}
