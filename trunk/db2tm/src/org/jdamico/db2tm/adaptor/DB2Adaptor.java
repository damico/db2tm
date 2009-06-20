package org.jdamico.db2tm.adaptor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import org.jdamico.db2tm.dataobject.Server;

public class DB2Adaptor {
	
	private static final DB2Adaptor INSTANCE = new DB2Adaptor();
	public static DB2Adaptor getInstance(){
		return INSTANCE;
	}
	
	
	
	public long testDb(Server srv){
		
		Calendar ca1 = Calendar.getInstance();       
        ca1.setTime(new Date());
        long milisecond1 = ca1.getTimeInMillis();
        long milisecond2 = 0;
        long diff = 0;
        
		String classfn = "com.ibm.db2.jcc.DB2Driver";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String dburl = "jdbc:db2://" + srv.getHost() + ":" + srv.getPort() + "/" + srv.getDbName() + "";
		
		try{
			Class.forName(classfn);
			con = DriverManager.getConnection(dburl, srv.getUser(), srv.getPasswd());
			stmt = con.createStatement();
			rs = stmt.executeQuery("select NAME from sysibm.sysschemata");
			while (rs.next()) {}
			Calendar ca2 = Calendar.getInstance();
			ca2.setTime(new Date());
			milisecond2 = ca2.getTimeInMillis();
		}catch (SQLException sqle) {
			diff = -1;
		} catch (ClassNotFoundException e) {
			diff = -1;
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(con!=null) con.close();
			} catch (SQLException e) {
				diff = -1;
			}
			
		}
		
		diff = milisecond2 - milisecond1;
		return diff;
		
	}

}
