package org.jdamico.db2tm.util;

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
}
