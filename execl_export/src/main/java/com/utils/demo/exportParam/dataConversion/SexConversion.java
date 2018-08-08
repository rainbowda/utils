package com.utils.demo.exportParam.dataConversion;

import com.utils.execlExport.dataConversion.DataExportConversion;

public class SexConversion implements DataExportConversion<Integer> {
    @Override
    public String transferData(Integer data) {
        if (0 == data){
            return "女";
        } else if (1 == data){
            return "男";
        }
        return "不详";
    }
}
