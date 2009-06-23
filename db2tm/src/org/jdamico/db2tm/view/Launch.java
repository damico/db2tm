package org.jdamico.db2tm.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jcurses.event.ActionListener;
import jcurses.event.WindowListener;
import jcurses.system.CharColor;
import jcurses.system.InputChar;
import jcurses.widgets.Button;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Widget;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

import org.jdamico.db2tm.component.MultipleTester;
import org.jdamico.db2tm.config.Constants;
import org.jdamico.db2tm.dataobject.Server;
import org.jdamico.db2tm.exception.DB2tmException;
import org.jdamico.db2tm.util.PasswordField;



public class Launch {
	
	private static Map<String, ArrayList<Long>> sumMap = new HashMap<String, ArrayList<Long>>();
	
	public static void main(String[] args) { 

		
		ArrayList<Server> srvList = null;;
		try {
			srvList = MultipleTester.getInstance().readConfiguration(Constants.SERVERS_XML);
		} catch (DB2tmException e) {
			exitWithMessage("\n\nInvalid '/etc/db2tm/servers.xml' file!\n" +
							"Make sure that you have at least one server configured:\n" +
							"<server dburl=\"db2://user@host:port/dbname\" dbalias=\"alias\" />\n\n");
		}
		char password[] = null;
		
		System.out.println(	"\n\n####### "+Constants.APPNAME+" #######");
		System.out.println(	"#");
		System.out.println(	"# Servers to be tested: "+srvList.size());
		System.out.println(	"#");
		
		for(int k=0; k < srvList.size(); k++){
			String passwd = null;
			
			try {
				password = PasswordField.getPassword(System.in, "# DB("+k+": "+srvList.get(k).getDbAlias()+") password: ");
				passwd = String.valueOf(password);
			} catch (Exception e) {
				exitWithMessage("Invalid password char[]");
			}
			
			srvList.get(k).setPasswd(passwd);
			
			sumMap.put(srvList.get(k).getDbAlias(), new ArrayList<Long>());
			
			
		}

		Window w = new Window(40, 20, true, "| db2tm: DB2 Test Multiple |");

		WindowListener keyL = new keyL();
		w.addListener(keyL);

		DefaultLayoutManager mgr = new DefaultLayoutManager();
		mgr.bindToContainer(w.getRootPanel());

		Widget wd = new Label("Iteration: --", new CharColor(CharColor.WHITE, CharColor.GREEN));
		mgr.addWidget(wd, 0, 0, 35, 10, WidgetsConstants.ALIGNMENT_LEFT, WidgetsConstants.ALIGNMENT_CENTER);


		Button btn = new Button("Exit");
		ActionListener lbtn = new ListenerButton();
		btn.addListener(lbtn);


		mgr.addWidget(btn, 0, 17, 38, 1, WidgetsConstants.ALIGNMENT_CENTER, WidgetsConstants.ALIGNMENT_CENTER);

		boolean key = true;

		w.show();

		int i = 0;
		InputChar c = null;
		while (key) {

			mgr.removeWidget(wd);
			wd = new Label("Iteration: "+i, new CharColor(CharColor.WHITE, CharColor.GREEN));
			mgr.addWidget(wd, 0, 0, 38, 5, WidgetsConstants.ALIGNMENT_LEFT, WidgetsConstants.ALIGNMENT_CENTER);

			for(int j=0; j < srvList.size(); j++){
				Widget l = getLabel(srvList.get(j), i);

				mgr.addWidget(l, 0, 1+j, 38, 5, WidgetsConstants.ALIGNMENT_LEFT, WidgetsConstants.ALIGNMENT_CENTER);
			}

			w.show();

			try {
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				exitWithMessage("Unable to sleep main thread.");
			}
			i++;


		}

	}

	private static Widget getLabel(Server srv, int iteration) {

		ArrayList<Long> sum = sumMap.get(srv.getDbAlias());

		long diff = MultipleTester.getInstance().getTestedServer(srv);
		short color = CharColor.RED;
		String msDiff = null;
		if(diff < 0 ){
			sum.add(0L);
			msDiff = "---";
		}
		else{
			color = CharColor.GREEN;
			msDiff = String.valueOf(diff);
			sum.add(diff);
		}


		
		
		String sAvg = "";
		if(sum.size()>0){
			long total = 0L;

			for(int p =0; p < sum.size(); p++){
				total = total + sum.get(p);
			}

			double avg = total/sum.size();
			NumberFormat formatter = new DecimalFormat("");
			sAvg = formatter.format(avg);
		}

		int tString = (srv.getDbAlias().length() + 2) + msDiff.length() + (sAvg.length() + 2);
		int pts = 35 - tString;

		String mpts = "";

		for(int i = 0; i < pts; i++){
			mpts = mpts + ".";
		}

		Label label = new Label("["+srv.getDbAlias()+"] "+mpts+" ("+sAvg+") "+msDiff, new CharColor(CharColor.WHITE, color));
		return label;
	}


	private static void exitWithMessage(String message){
		System.out.println(message);
		System.exit(1);
	}
}
