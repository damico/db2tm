package org.jdamico.db2tm.xml;

import java.io.IOException;
import java.util.ArrayList;

import org.jdamico.db2tm.dataobject.Server;
import org.jdamico.db2tm.exception.DB2tmException;
import org.xml.sax.SAXException;

public class XMLServerConverter implements Converters {

	private String xml;
	
	public XMLServerConverter(String xml) {
		this.xml = xml;
	}

	public ArrayList<Server> exec() throws DB2tmException   {
		Parser parser = null;
		ServerDataProcessor sdp = null;
		try {
			parser = new Parser(xml);
			sdp = parser.getRawServerData();
		} catch (SAXException e) {
			throw new DB2tmException(e.getStackTrace());
		} catch (IOException e) {
			throw new DB2tmException(e.getStackTrace());
		}
		
		ArrayList<Server> srvList = sdp.getData();
		return srvList;
	}

}
