package com.utils.execl.export.defaultDataHandle;


public class DataHandlerFactory {

	private static AbstractDataHandler dataHandler = new BooleanDataHandler(
			new DateDataHandler(
					new StringDataHandler(null)));

	public static String dataHandle(Object value) {
		return dataHandler.dataHandle(value);
	}
}
