package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.ControleDispensacao.entidade.Chamado;
import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="chamadoHome")
@SessionScoped
public class ChamadoHome extends PadraoHome<Chamado>{
	@Override
	public boolean enviar() {
		try {
			getInstancia().setUsuario(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usuário atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ChamadoHome");
		}
		getInstancia().setDataChamado(new Date());
		getInstancia().setAtendido(TipoBooleanEnum.F);
		return super.enviar();
	}
	
	@Override
	public void aposEnviar() {
//		enviarEmail();  
		super.aposEnviar();
	}

	private void enviarEmail() {
		try {
			HtmlEmail email = new HtmlEmail();  
	        
	        // adiciona uma imagem ao corpo da mensagem e retorna seu id  
	        
	        // configura a mensagem para o formato HTML  
	        email.setHtmlMsg("<html>Logo do Apache - <img ></html>");  
	  
	        // configure uma mensagem alternativa caso o servidor não suporte HTML  
	        email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");  
	          
	        email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail  
	        email.addTo("marlonmca@gmail.com", "Márlon Assis"); //destinatário  
	        email.setFrom("farmaciahusergipe@gmail.com", "Eu"); // remetente  
	        email.setSubject("Teste -> Html Email"); // assunto do e-mail  
	        email.setMsg("Teste de Email HTML utilizando commons-email"); //conteudo do e-mail  
	        email.setAuthentication("farmaciahusergipe@gmail.com", "farmaciahusergipe2012");  
	        email.setSmtpPort(465);  
	        email.setSSL(true);  
	        email.setTLS(true);  
	        // envia email  
	        email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}  
	}
}
