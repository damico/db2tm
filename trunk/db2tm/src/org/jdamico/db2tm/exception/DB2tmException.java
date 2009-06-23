package org.jdamico.db2tm.exception;

public class DB2tmException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -705870607854305027L;

	private StackTraceElement[] stackTraceElements;

	private String message;

	public DB2tmException(String message){

		super(message);
		this.message = message;
	}

	public DB2tmException(){
		super();
	}

	public DB2tmException(StackTraceElement[] stackTraceElements) {
		this.stackTraceElements = stackTraceElements;
	}

	public String getStackTraceElements(){
		StringBuffer sb = new StringBuffer();

		if(stackTraceElements == null){
			sb.append(message);
		}else{
			for(int i = 0; i < stackTraceElements.length; i++){
				sb.append(stackTraceElements[i].getFileName()+"("+stackTraceElements[i].getLineNumber()+")\n");
			}
		}


		return sb.toString();
	}
}
