package com.utils.execlExport.defaultDataHandle;


public class DataHandlerFactory {

	private static AbstractDataHandler dataHandler = new BooleanDataHandler(
			new DateDataHandler(
					new StringDataHandler(null)));

	public static String dataHandle(Object value) {
		return dataHandler.dataHandle(value);
	}
}
