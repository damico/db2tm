package org.jdamico.db2tm.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;



import org.jdamico.db2tm.config.Constants;
import org.jdamico.db2tm.exception.DB2tmException;
import org.xml.sax.SAXException;

public class DataValidation {
	
	private static final DataValidation INSTANCE = new DataValidation();
	public static DataValidation getInstnace(){
		return INSTANCE;
	}
	
	public String[] parseDbUrl(String url) {
		/*
		 * example of url: db2://username@localhost:50000/mydb
		 * */
		
		String[] ret = new String[5];
		
		
		String userServer = null;
		String portDb = null;
		
		
		int firstBreak =0, secondBreak = 0;
		
		for(int i = 0; i < url.length(); i++){
			if(url.charAt(i) == ':' && firstBreak == 0){
				firstBreak = i;
				ret[0] = url.substring(0,firstBreak);
			}else if(url.charAt(i) == ':' && firstBreak > 0){
				secondBreak = i;
				userServer = url.substring(firstBreak+3, secondBreak);
				
				for(int j=0; j < userServer.length(); j++){
					if(userServer.charAt(j) == '@'){
						ret[1] = userServer.substring(0,j);
						ret[2] = userServer.substring(j+1,userServer.length());
					}
				}
				
				portDb = url.substring(secondBreak+1, url.length());
				
				for(int j=0; j < portDb.length(); j++){
					if(portDb.charAt(j) == '/'){
						ret[3] = portDb.substring(0,j);
						ret[4] = portDb.substring(j+1,portDb.length());
					}
				}
				
			}
		}	
		return ret;
	}
	
	public boolean isDocValid(String xml) throws DB2tmException {

		SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		File schemaLocation = new File(Constants.SERVERS_XSD);

		if(schemaLocation.exists()){
			Schema schema = null;
			try {
				schema = factory.newSchema(schemaLocation);
			} catch (SAXException e) {
				throw new DB2tmException("Invalid xsd or xsd not found: "+e.getStackTrace());
			}
			Validator validator = schema.newValidator();
			Source source = new StreamSource(new StringReader(xml));
			try {
				validator.validate(source);
			} catch (SAXException e) {
				throw new DB2tmException(e.getStackTrace());
			} catch (IOException e) {
				throw new DB2tmException(e.getStackTrace());
			}
		}else{
			throw new DB2tmException("xsd not found!");
		}
		
			
	
		
		

		return true;

	}
}
