package com.utils.execl.export.defaultDataHandle;

public class BooleanDataHandler extends AbstractDataHandler {
	
	public BooleanDataHandler(AbstractDataHandler abstractDataHandler) {
		super(abstractDataHandler);
	}

	@Override
	public String dataHandle(Object value) {
		if (value instanceof Boolean) {
			boolean bValue = (Boolean) value;
			String textValue = "是";
			if (!bValue) {
				textValue = "否";
			}
			return textValue;
		} else {
			return nextHandle(value);
		}
	}

}
