package org.jdamico.db2tm.xml;

import java.util.ArrayList;

import org.jdamico.db2tm.dataobject.Server;

public class XMLServerConverter implements Converters {

	private String xml;
	
	public XMLServerConverter(String xml) {
		this.xml = xml;
	}

	public ArrayList<Server> exec() throws Exception {
		Parser parser = new Parser(xml);
		ServerDataProcessor sdp = parser.getRawServerData();
		ArrayList<Server> srvList = sdp.getData();
		return srvList;
	}

}
