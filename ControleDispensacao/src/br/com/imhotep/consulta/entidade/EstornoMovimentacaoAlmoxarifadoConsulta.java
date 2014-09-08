package br.com.imhotep.consulta.entidade;

	import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

	import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

	import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.raiz.EstornoMovimentacaoAlmoxarifadoRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;
import br.com.remendo.gerenciador.ControleInstancia;

	@ManagedBean
	@SessionScoped
	public class EstornoMovimentacaoAlmoxarifadoConsulta extends PadraoConsulta<MovimentoLivroAlmoxarifado> {
		
		private Date dataReferencia;
		private EstoqueAlmoxarifado estoqueAlmoxarifado = new EstoqueAlmoxarifado();
		
		public EstornoMovimentacaoAlmoxarifadoConsulta(){
			super();
		}
		
		@Override
		public void novaInstancia() {
			setEstoqueAlmoxarifado(new EstoqueAlmoxarifado());
			super.novaInstancia();
			setLista(new ArrayList<MovimentoLivroAlmoxarifado>());
			try {
				EstornoMovimentacaoAlmoxarifadoRaiz a = (EstornoMovimentacaoAlmoxarifadoRaiz) new ControleInstancia().procuraInstancia(EstornoMovimentacaoAlmoxarifadoRaiz.class);
				a.setEstoquesAlmoxarifado(new ArrayList<EstoqueAlmoxarifado>());
				a.setMaterialAlmoxarifado(new MaterialAlmoxarifado());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void carregarResultado() {
			String sql = "";
			setConsultaGeral(new ConsultaGeral<MovimentoLivroAlmoxarifado>());
			
			String da = "";
			if(getDataReferencia() != null)
				da = new SimpleDateFormat("yyyy-MM").format(getDataReferencia());
			else
				da = new SimpleDateFormat("yyyy-MM").format(new Date());
			
			sql = "select o from MovimentoLivroAlmoxarifado o where to_char(o.dataMovimento, 'yyyy-MM') = '"+
					da+"' ";
			
			if(getEstoqueAlmoxarifado() != null && getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado() != 0){
				int id = getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado();
				sql += " and o.estoqueAlmoxarifado.idEstoqueAlmoxarifado = "+id+" ";
			}
			
			getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
			super.carregarResultado();
		}

		public EstoqueAlmoxarifado getEstoqueAlmoxarifado() {
			return estoqueAlmoxarifado;
		}

		public void setEstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
			this.estoqueAlmoxarifado = estoqueAlmoxarifado;
		}

		public Date getDataReferencia() {
			return dataReferencia;
		}

		public void setDataReferencia(Date dataReferencia) {
			this.dataReferencia = dataReferencia;
		}
		
	}
	
