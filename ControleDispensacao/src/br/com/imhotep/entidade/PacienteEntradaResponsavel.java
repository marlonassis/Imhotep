package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_paciente_entrada_responsavel")
public class PacienteEntradaResponsavel {
	
	private int idPacienteEntradaResponsavel;
	private String nome;
	private String parentesco;
	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private Cidade cidade;
	private String cpf;
	private String telefone1;
	private String telefone2;
	private PacienteEntrada pacienteEntrada;
	
	public PacienteEntradaResponsavel(){
		super();
	}
	
	public PacienteEntradaResponsavel(PacienteEntradaResponsavel pacienteEntradaResponsavel){
		this.nome = pacienteEntradaResponsavel.getNome();
		this.parentesco = pacienteEntradaResponsavel.getParentesco();
		this.logradouro = pacienteEntradaResponsavel.getLogradouro();
		this.numero = pacienteEntradaResponsavel.getNumero();
		this.bairro = pacienteEntradaResponsavel.getBairro();
		this.cep = pacienteEntradaResponsavel.getCep();
		this.cidade = pacienteEntradaResponsavel.getCidade();
		this.cpf = pacienteEntradaResponsavel.getCpf();
		this.telefone1 = pacienteEntradaResponsavel.getTelefone1();
		this.telefone2 = pacienteEntradaResponsavel.getTelefone2();
	}
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_paciente_entrada_responsav_id_paciente_entrada_responsav_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_paciente_entrada_responsavel", unique = true, nullable = false)
	public int getIdPacienteEntradaResponsavel() {
		return idPacienteEntradaResponsavel;
	}
	public void setIdPacienteEntradaResponsavel(int idPacienteEntradaResponsavel) {
		this.idPacienteEntradaResponsavel = idPacienteEntradaResponsavel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_parentesco")
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	
	@Column(name = "cv_logradouro")
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@Column(name = "cv_numero")
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name = "cv_bairro")
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name = "cv_cep")
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Column(name = "cv_cpf")
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@OneToOne(mappedBy="pacienteEntradaResponsavel")  
	public PacienteEntrada getPacienteEntrada() {
		return pacienteEntrada;
	}
	public void setPacienteEntrada(PacienteEntrada pacienteEntrada) {
		this.pacienteEntrada = pacienteEntrada;
	}
	
	@Column(name = "cv_telefone_1")
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	
	@Column(name = "cv_telefone_2")
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	
	@Transient
	public String getEndereco(){
		if(logradouro != null)
			return logradouro.concat(", ").concat(numero == null ? "" : numero).concat(", ").concat(bairro == null ? "" : bairro).concat(", ").concat(cidade == null ? "" : cidade.getNomeCidadeEstado());
		return null;	
	}
	
	@Transient
	public String getTelefone1Formatado(){
		return br.com.imhotep.auxiliar.Utilities.formatarValorMascara(telefone1, "(##)####-####");
	}
	
	@Transient
	public String getTelefone2Formatado(){
		return br.com.imhotep.auxiliar.Utilities.formatarValorMascara(telefone2, "(##)####-####");
	}
	
	@Transient
	public String getCpfFormatado(){
		return br.com.imhotep.auxiliar.Utilities.formatarValorMascara(cpf, "###.###.###-##");
	}
	
	@Transient
	public String getCepFormatado(){
		return br.com.imhotep.auxiliar.Utilities.formatarValorMascara(cep, "##.###-###");
	}
	
	public PacienteEntradaResponsavel clone(){
		return new PacienteEntradaResponsavel(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof PacienteEntradaResponsavel))
			return false;
		
		return ((PacienteEntradaResponsavel)obj).getIdPacienteEntradaResponsavel() == this.idPacienteEntradaResponsavel;
	}

	@Override
	public String toString() {
		return nome;
	}
}
