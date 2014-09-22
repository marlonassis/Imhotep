package br.com.imhotep.entidade.extra;

public class FinanceiroNotaFiscalAlmoxarifadoDesconto {
	private String identificacao;
	private double desconto;
	
	public FinanceiroNotaFiscalAlmoxarifadoDesconto() {
		super();
	}
	public FinanceiroNotaFiscalAlmoxarifadoDesconto(String identificacao, double desconto) {
		super();
		this.identificacao = identificacao;
		this.desconto = desconto;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(desconto);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((identificacao == null) ? 0 : identificacao.hashCode());
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
		FinanceiroNotaFiscalAlmoxarifadoDesconto other = (FinanceiroNotaFiscalAlmoxarifadoDesconto) obj;
		if (Double.doubleToLongBits(desconto) != Double
				.doubleToLongBits(other.desconto))
			return false;
		if (identificacao == null) {
			if (other.identificacao != null)
				return false;
		} else if (!identificacao.equals(other.identificacao))
			return false;
		return true;
	}
	
}
