package br.com.imhotep.entidade.extra;

public class FinanceiroGrupoNotaFiscalAlmoxarifado {
	private String identificacao;
	private String razaoSocial;
	private String cadastroPessoaFisicaJuridica;
	private double valorDescontado;
	
	public FinanceiroGrupoNotaFiscalAlmoxarifado() {
		super();
	}
	public FinanceiroGrupoNotaFiscalAlmoxarifado(String identificacao,
			String razaoSocial, String cadastroPessoaFisicaJuridica,
			double valorDescontado) {
		super();
		this.identificacao = identificacao;
		this.razaoSocial = razaoSocial;
		this.cadastroPessoaFisicaJuridica = cadastroPessoaFisicaJuridica;
		this.valorDescontado = valorDescontado;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCadastroPessoaFisicaJuridica() {
		return cadastroPessoaFisicaJuridica;
	}
	public void setCadastroPessoaFisicaJuridica(
			String cadastroPessoaFisicaJuridica) {
		this.cadastroPessoaFisicaJuridica = cadastroPessoaFisicaJuridica;
	}
	public double getValorDescontado() {
		return valorDescontado;
	}
	public void setValorDescontado(double valorDescontado) {
		this.valorDescontado = valorDescontado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cadastroPessoaFisicaJuridica == null) ? 0
						: cadastroPessoaFisicaJuridica.hashCode());
		result = prime * result
				+ ((identificacao == null) ? 0 : identificacao.hashCode());
		result = prime * result
				+ ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorDescontado);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		FinanceiroGrupoNotaFiscalAlmoxarifado other = (FinanceiroGrupoNotaFiscalAlmoxarifado) obj;
		if (cadastroPessoaFisicaJuridica == null) {
			if (other.cadastroPessoaFisicaJuridica != null)
				return false;
		} else if (!cadastroPessoaFisicaJuridica
				.equals(other.cadastroPessoaFisicaJuridica))
			return false;
		if (identificacao == null) {
			if (other.identificacao != null)
				return false;
		} else if (!identificacao.equals(other.identificacao))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (Double.doubleToLongBits(valorDescontado) != Double
				.doubleToLongBits(other.valorDescontado))
			return false;
		return true;
	}
	
}
