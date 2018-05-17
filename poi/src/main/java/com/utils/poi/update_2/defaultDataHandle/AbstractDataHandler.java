package com.utils.poi.update_2.defaultDataHandle;

public abstract class AbstractDataHandler {
	private AbstractDataHandler abstractDataHandler;

	public AbstractDataHandler(AbstractDataHandler abstractDataHandler) {
		this.abstractDataHandler = abstractDataHandler;
	}

	public abstract String dataHandle(Object value);

	protected String nextHandle(Object value) {
		if (abstractDataHandler != null) {
			return abstractDataHandler.dataHandle(value);
		}
		return null;
	}
}
