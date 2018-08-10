package com.utils.excelExport.data;

import com.utils.excelExport.dataConversion.DataExportConversion;

import java.util.ArrayList;
import java.util.List;

public class BaseParam {
	public List data = new ArrayList<>();
	public List<ColumnParam> columnParams = new ArrayList<>();
	
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	
	
	public List<ColumnParam> getColumnParams() {
		return columnParams;
	}

    /**
     * 数据行参数
     */
	public class ColumnParam{
		private String headerName;
		private String fieldName;
		private DataExportConversion conversion;//数据转换

		
		public ColumnParam(String headerName, String fieldName) {
			this(headerName, fieldName,null);
		}

		public ColumnParam(String headerName, String fieldName, DataExportConversion conversion) {
			this.headerName = headerName;
			this.fieldName = fieldName;
			this.conversion = conversion;
		}

		public String getHeaderName() {
			return headerName;
		}
		public String getFieldName() {
			return fieldName;
		}

        public DataExportConversion getConversion() {
            return conversion;
        }
    }
}
