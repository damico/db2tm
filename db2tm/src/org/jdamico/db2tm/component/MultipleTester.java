package org.jdamico.db2tm.component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jdamico.db2tm.adaptor.DB2Adaptor;
import org.jdamico.db2tm.dataobject.Server;
import org.jdamico.db2tm.util.DataValidation;
import org.jdamico.db2tm.xml.ConverterFactory;

public class MultipleTester {


	private static MultipleTester INSTANCE =  new MultipleTester();
	public static MultipleTester getInstance(){
		return INSTANCE;
	}

	public ArrayList<Server> readConfiguration(String fileNameLocation) throws Exception{
		return ConverterFactory.getConverter(0, getFileContent(fileNameLocation)).exec();
	}


	private String getFileContent(String fileNameLocation){
		StringBuffer sb = new StringBuffer();
		FileReader fr = null;
		try {
			fr = new FileReader(fileNameLocation);
			BufferedReader in = new BufferedReader(fr);
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}


	public long getTestedServer(Server rawSrv){

		long ret =0;

		String db2Url = rawSrv.getDbUrl();
		if(db2Url != null){


			String[] input = DataValidation.getInstnace().parseDbUrl(db2Url);

			boolean badUrl = false;

			for (int i = 0; i < input.length; i++) {
				if(input[i]==null) badUrl = true; 
			}

			if(!badUrl){
				rawSrv.setHost(input[2]);
				rawSrv.setPort(input[3]);
				rawSrv.setDbName(input[4]);
				rawSrv.setUser(input[1]);
			}

			ret = DB2Adaptor.getInstance().testDb(rawSrv);
		}


		return ret;
	}
}
