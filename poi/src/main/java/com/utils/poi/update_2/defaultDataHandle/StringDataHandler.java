package com.utils.poi.update_2.defaultDataHandle;

public class StringDataHandler extends AbstractDataHandler {

	public StringDataHandler(AbstractDataHandler abstractDataHandler) {
		super(abstractDataHandler);
	}

	@Override
	public String dataHandle(Object value) {
		return value.toString();
	}

}
