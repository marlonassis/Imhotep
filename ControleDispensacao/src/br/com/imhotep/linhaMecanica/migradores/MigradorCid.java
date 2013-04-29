package br.com.imhotep.linhaMecanica.migradores;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Classe para migrar o XML do CID para o banco
 * @author marlonassis
 *
 */
public class MigradorCid {
	
	private static Map<String, Set<String>> tags = new HashMap<String,Set<String>>();
	private static String nivelAnterior = "";
	
	public static void main(String[] args) {
		 
	    try {
	 
	    	String cid = "/home/desenvolvimento/Desktop/CID/CID10.xml";
		File file = new File(cid);
	 
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	 
		Document doc = dBuilder.parse(file);
	 
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		if (doc.hasChildNodes()) {
			printNote(doc.getChildNodes());
		}
		for(String key : tags.keySet()){
			System.out.println("\n-> "+key);
			for(String sub : tags.get(key)){
				System.out.println("   |-> "+sub);
			}
		}
	 
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	 
	  }
	 
	  private static void printNote(NodeList nodeList) {
	    for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				mapeamento(tempNode);
//				capitulo(tempNode);
//				grupo(tempNode);
//				categoria(tempNode);
//				subCategoria(tempNode);
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					nivelAnterior = tempNode.getNodeName();
					printNote(tempNode.getChildNodes());
				}
//				System.out.println("Node Name =" + nodeName + " [CLOSE]");
			}
	 
	    }
	 
	  }

	  private static void mapeamento(Node tempNode) {
		String nodeName = tempNode.getNodeName();
		NamedNodeMap nodeMap = tempNode.getAttributes();
		Set<String> atributos = new HashSet<String>();
		if (tempNode.hasAttributes()) {
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Node node = nodeMap.item(i);
				atributos.add(node.getNodeName());
			}
		}
			
		String nomeRastreado = tempNode.getParentNode().getNodeName()+" -> "+nodeName;
		if(tags.containsKey(nomeRastreado)){
			Set<String> att = tags.get(nomeRastreado);
			att.addAll(atributos);
			tags.remove(nomeRastreado);
			tags.put(nomeRastreado, att);
		}else{
			tags.put(nomeRastreado, atributos);
		}
	}
	  
	private static void capitulo(Node tempNode) {
		String nodeName = tempNode.getNodeName();
		if(nodeName.equals("capitulo")){
			System.out.println("\ntag: " + nodeName);
			if (tempNode.hasAttributes()) {
 
				// get attributes names and values
				NamedNodeMap nodeMap = tempNode.getAttributes();
 
				for (int i = 0; i < nodeMap.getLength(); i++) {
 
					Node node = nodeMap.item(i);
					System.out.println(node.getNodeName()+": " + node.getNodeValue());
 
				}
			}
 
			Element eElement = (Element) tempNode;
			System.out.println("Nome: " + eElement.getElementsByTagName("nome").item(0).getTextContent());
		}
	}
	
	private static void grupo(Node tempNode) {
		String nodeName = tempNode.getNodeName();
		if(nodeName.equals("grupo")){
			System.out.println("\ntag: " + nodeName);
			if (tempNode.hasAttributes()) {
 
				// get attributes names and values
				NamedNodeMap nodeMap = tempNode.getAttributes();
 
				for (int i = 0; i < nodeMap.getLength(); i++) {
 
					Node node = nodeMap.item(i);
					System.out.println(node.getNodeName()+": " + node.getNodeValue());
 
				}
			}
 
			Element eElement = (Element) tempNode;
			System.out.println("Nome: " + eElement.getElementsByTagName("nome").item(0).getTextContent());
			System.out.println("Nome50: " + eElement.getElementsByTagName("nome50").item(0).getTextContent());
		}
	}
	
	private static void categoria(Node tempNode) {
		String nodeName = tempNode.getNodeName();
		if(nodeName.equals("categoria")){
			System.out.println("\ntag: " + nodeName);
			if (tempNode.hasAttributes()) {
 
				// get attributes names and values
				NamedNodeMap nodeMap = tempNode.getAttributes();
 
				for (int i = 0; i < nodeMap.getLength(); i++) {
 
					Node node = nodeMap.item(i);
					System.out.println(node.getNodeName()+": " + node.getNodeValue());
 
				}
			}
 
			Element eElement = (Element) tempNode;
			System.out.println("Nome: " + eElement.getElementsByTagName("nome").item(0).getTextContent());
			System.out.println("Nome50: " + eElement.getElementsByTagName("nome50").item(0).getTextContent());
		}
	}
	
	private static void subCategoria(Node tempNode) {
		String nodeName = tempNode.getNodeName();
		if(nodeName.equals("subcategoria")){
			System.out.println("\ntag: " + nodeName);
			if (tempNode.hasAttributes()) {
 
				// get attributes names and values
				NamedNodeMap nodeMap = tempNode.getAttributes();
 
				for (int i = 0; i < nodeMap.getLength(); i++) {
 
					Node node = nodeMap.item(i);
					System.out.println(node.getNodeName()+": " + node.getNodeValue());
 
				}
			}
 
			Element eElement = (Element) tempNode;
			System.out.println("Nome: " + eElement.getElementsByTagName("nome").item(0).getTextContent());
			System.out.println("Nome50: " + eElement.getElementsByTagName("nome50").item(0).getTextContent());
		}
	}
}
