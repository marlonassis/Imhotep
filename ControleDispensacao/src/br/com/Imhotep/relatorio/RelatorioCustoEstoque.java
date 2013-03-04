package br.com.Imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.consulta.relatorio.ConsultaRelatorioCustoEstoque;
import br.com.imhotep.entidade.relatorio.CustoEstoque;

@ManagedBean(name="relatorioCustoEstoque")
@ViewScoped
public class RelatorioCustoEstoque extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataIni;
	private Date dataFim;
	

	
}
