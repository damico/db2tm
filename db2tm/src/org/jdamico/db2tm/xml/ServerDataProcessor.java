package org.jdamico.db2tm.xml;

import java.util.ArrayList;

import org.jdamico.db2tm.dataobject.Server;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;



public class ServerDataProcessor extends DefaultHandler implements DefaultDataProcessor{

	public static final String TAG_SERVER = "server";
	private StringBuffer buffer = null;
	private boolean isTagActive = false;
	private String activeTag = null;
	private Server serverTag = null;
	private ArrayList<Server> srvList = new ArrayList<Server>();
	
	public void startElement (String namespaceUri, String localName, String qualifiedName, Attributes attributes) {

		if(qualifiedName.equals(TAG_SERVER)){
			activeTag = TAG_SERVER;
			serverTag = new Server(attributes.getValue("dburl"), attributes.getValue("dbalias")); 
			srvList.add(serverTag);
			if(buffer==null) buffer = new StringBuffer();
			isTagActive = true;
		}else{
			isTagActive = false;
			buffer = null;
		}

	}

	public void characters(char[] chars,int start,int length){

		if(isTagActive && activeTag.equals(TAG_SERVER)){
			if(buffer==null) buffer = new StringBuffer();
			buffer.append(chars,start,length);
		}else{
			buffer = null;
		}

	}

	public void endElement(String uri, String name, String qualifiedName){

		if(qualifiedName.equals(TAG_SERVER)){ 
			isTagActive = true;
			if(buffer!=null){ /* get content */ }
			buffer = null;

		}

	}

	public ArrayList<Server>  getData() {
		return srvList;
	}

}
