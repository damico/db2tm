package org.jdamico.db2tm.view;

import java.io.IOException;
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
import org.jdamico.db2tm.dataobject.Server;
import org.jdamico.db2tm.util.PasswordField;



public class Launch {
	
	private static Map<String, ArrayList<Long>> sumMap = new HashMap<String, ArrayList<Long>>();
	
	
	
	public static void main(String[] args) throws Exception { 


		ArrayList<Server> srvList =  MultipleTester.getInstance().readConfiguration("/home/jdamico/workspace/db2tm/servers.xml");
		char password[] = null;
		for(int k=0; k < srvList.size(); k++){
			try {
				password = PasswordField.getPassword(System.in, "DB("+k+") password: ");
			} catch (IOException ioe) {
				exitWithMessage("Invalid password char[]");
			}
			srvList.get(k).setPasswd(String.valueOf(password));
			
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



			Thread.currentThread().sleep(5000);
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
