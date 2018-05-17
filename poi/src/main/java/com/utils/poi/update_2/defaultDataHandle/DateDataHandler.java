package com.utils.poi.update_2.defaultDataHandle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDataHandler extends AbstractDataHandler {
	
	public DateDataHandler(AbstractDataHandler abstractDataHandler) {
		super(abstractDataHandler);
	}

	@Override
	public String dataHandle(Object value) {
		if (value instanceof Date) {
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		} else {
			return nextHandle(value);
		}
	}

}
