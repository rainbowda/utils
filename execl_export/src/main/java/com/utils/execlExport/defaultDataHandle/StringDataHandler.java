package com.utils.execlExport.defaultDataHandle;

public class StringDataHandler extends AbstractDataHandler {
	
	public StringDataHandler(AbstractDataHandler abstractDataHandler) {
		super(abstractDataHandler);
	}

	@Override
	public String dataHandle(Object value) {
		return value.toString();
	}

}
