package com.utils.demo.exportParam.dataConversion;

import com.utils.execlExport.dataConversion.DataExportConversion;

import java.util.List;

public class HobbyConversion implements DataExportConversion<List<String>> {
    @Override
    public String transferData(List<String> data) {
        StringBuilder hobby = new StringBuilder();

        for (String s:data){
            hobby.append(s).append(",");
        }

        //移除最后一个逗号
        if (hobby.length() >= 1){
            hobby.deleteCharAt(hobby.length()-1);
        }

        return hobby.toString();
    }
}
