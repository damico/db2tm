package org.jdamico.db2tm.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Parser {
	private SAXParserFactory factory = null;
	private XMLReader parser = null;
	private InputSource iso = null;
	
	public Parser(String xmlSource) throws SAXException{
		factory = SAXParserFactory.newInstance();
		parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		parser.setFeature("http://xml.org/sax/features/namespaces",true);
		byte[] bis = xmlSource.getBytes();
        InputStream is = new ByteArrayInputStream(bis);
        iso =  new InputSource(is);
	}
	
	public ServerDataProcessor getRawServerData() throws IOException, SAXException{
		ServerDataProcessor dp = new ServerDataProcessor();
		parser.setContentHandler(dp);
		parser.parse(iso);
		return dp;
	}

}
