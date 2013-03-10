package br.com.Imhotep.seguranca;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

@WebListener
public class GerenciadorSessao implements HttpSessionListener {
		
	    public void sessionCreated(HttpSessionEvent event) {
	    }

	    public void sessionDestroyed(HttpSessionEvent event) {
	        try {
				if(Autenticador.getInstancia() == null)
					new LinhaMecanica().salvarLogSessaoDestruida(new Date(), event.getSession().getId(), 
							new Date(event.getSession().getLastAccessedTime()), event.getSession().getMaxInactiveInterval());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

}