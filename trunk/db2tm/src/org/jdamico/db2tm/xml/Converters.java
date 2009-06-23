package org.jdamico.db2tm.xml;

import java.util.ArrayList;

import org.jdamico.db2tm.dataobject.Server;
import org.jdamico.db2tm.exception.DB2tmException;

public interface Converters {
	public ArrayList<Server> exec()  throws DB2tmException;
}
