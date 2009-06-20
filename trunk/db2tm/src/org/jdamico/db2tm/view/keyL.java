package org.jdamico.db2tm.view;

import jcurses.event.WindowEvent;
import jcurses.event.WindowListener;
import jcurses.system.CharColor;
import jcurses.widgets.DefaultLayoutManager;
import jcurses.widgets.Label;
import jcurses.widgets.Widget;
import jcurses.widgets.WidgetsConstants;
import jcurses.widgets.Window;

public class keyL implements WindowListener {

	
	public void windowChanged(WindowEvent event) {
		Widget e = event.getSource();
		
		
		
		Window w = new Window(80, 40, true, "Window");
        DefaultLayoutManager mgr = new DefaultLayoutManager();
        mgr.bindToContainer(w.getRootPanel());
        mgr.addWidget(
            new Label("Server        1", new CharColor(CharColor.WHITE, CharColor.GREEN)),
            0, 5, 8, 1,
            WidgetsConstants.ALIGNMENT_LEFT,
            WidgetsConstants.ALIGNMENT_LEFT);
        

	}

}
