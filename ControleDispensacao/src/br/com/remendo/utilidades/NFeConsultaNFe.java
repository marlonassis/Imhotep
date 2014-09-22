package br.com.remendo.utilidades;

import br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeConsulta2Stub;  
import java.net.URL;  
import java.security.Security;  
import org.apache.axiom.om.OMElement;  
import org.apache.axiom.om.util.AXIOMUtil;  

/**
*
* @author JavaC - Java Community
*/
public class NFeConsultaNFe {

   public static void main(String[] args) {
       try {
           /**
            * 1) codigoDoEstado = C�digo do Estado conforme tabela IBGE.
            *
            * 2) url = Endere�o do WebService para cada Estado.
            *       Ver rela��o dos endere�os em:
            *       Para Homologa��o: http://hom.nfe.fazenda.gov.br/PORTAL/WebServices.aspx
            *       Para Produ��o: http://www.nfe.fazenda.gov.br/portal/WebServices.aspx
            *
            * 3) caminhoDoCertificadoDoCliente = Caminho do Certificado do Cliente (A1).
            *
            * 4) senhaDoCertificadoDoCliente = Senha do Certificado A1 do Cliente.
            *
            * 5) arquivoCacertsGeradoParaCadaEstado = Arquivo com os Certificados necessarios para
            * acessar o WebService. Pode ser gerado com a Classe NFeBuildCacerts.
            *
            * 6) Chave de Acesso da NFe;
            */
           String codigoDoEstado = "42";
           /**
            * Enderecos de Homolo��o do Sefaz Virtual RS
            * para cada WebService existe um endereco Diferente.
            */
           //URL url = new URL("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx");
           //URL url = new URL("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/nferecepcao/NfeRecepcao2.asmx");
           //URL url = new URL("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/nferetrecepcao/NfeRetRecepcao2.asmx");
           //URL url = new URL("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/nfecancelamento/NfeCancelamento2.asmx");
           //URL url = new URL("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/nfeinutilizacao/NfeInutilizacao2.asmx");
           URL url = new URL("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/nfeconsulta/NfeConsulta2.asmx");
           //URL url = new URL("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/nfestatusservico/NfeStatusServico2.asmx");

           String caminhoDoCertificadoDoCliente = "C:/JavaC/NF-e/certificadoDoCliente.pfx";
           String senhaDoCertificadoDoCliente = "1234";
           String arquivoCacertsGeradoParaCadaEstado = "C:/JavaC/NF-e/nfe-cacerts";

           /**
            * Colocar a Chave de Acesso da NF-e Aqui.
            * Usado ?????????????????????????????????????????? para ocultar o CNPJ.
            */
           String chaveDaNFe = "28130902350721000157550010000330571000330574";

           /**
            * Informa��es do Certificado Digital.
            */
           System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
           Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

           System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");

           System.clearProperty("javax.net.ssl.keyStore");
           System.clearProperty("javax.net.ssl.keyStorePassword");
           System.clearProperty("javax.net.ssl.trustStore");

           System.setProperty("javax.net.ssl.keyStore", caminhoDoCertificadoDoCliente);
           System.setProperty("javax.net.ssl.keyStorePassword", senhaDoCertificadoDoCliente);

           System.setProperty("javax.net.ssl.trustStoreType", "JKS");
           System.setProperty("javax.net.ssl.trustStore", arquivoCacertsGeradoParaCadaEstado);

           /**
            * Xml de Consulta.
            */
           StringBuilder xml = new StringBuilder();
           xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
               .append("<consSitNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
               .append("<tpAmb>2</tpAmb>")
               .append("<xServ>CONSULTAR</xServ>")
               .append("<chNFe>")
               .append(chaveDaNFe)
               .append("</chNFe>")
               .append("</consSitNFe>");

           OMElement ome = AXIOMUtil.stringToOM(xml.toString());

           NfeConsulta2Stub.NfeDadosMsg dadosMsg = new NfeConsulta2Stub.NfeDadosMsg();
           dadosMsg.setExtraElement(ome);

           NfeConsulta2Stub.NfeCabecMsg nfeCabecMsg = new NfeConsulta2Stub.NfeCabecMsg();
           /**
            * C�digo do Estado.
            */
           nfeCabecMsg.setCUF(codigoDoEstado);

           /**
            * Versao do XML
            */
           nfeCabecMsg.setVersaoDados("2.00");
           NfeConsulta2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeConsulta2Stub.NfeCabecMsgE();
           nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

           NfeConsulta2Stub stub = new NfeConsulta2Stub(url.toString());
           NfeConsulta2Stub.NfeConsultaNF2Result result = stub.nfeConsultaNF2(dadosMsg, nfeCabecMsgE);

           System.out.println(result.getExtraElement().toString());
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}