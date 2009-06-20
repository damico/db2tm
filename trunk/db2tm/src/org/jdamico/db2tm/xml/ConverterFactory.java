package org.jdamico.db2tm.xml;

public class ConverterFactory {
	public static Converters getConverter(int type, String xml){
		switch(type){
		case 0:
			return new XMLServerConverter(xml);
		default:
			return null;
		}
	}
}
