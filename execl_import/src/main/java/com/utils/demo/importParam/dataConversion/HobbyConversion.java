package com.utils.demo.importParam.dataConversion;

import com.utils.execlImport.dataConversion.DataImportConversion;

import java.util.Arrays;
import java.util.List;

public class HobbyConversion implements DataImportConversion<List<String>> {
    @Override
    public List<String> transferData(Object data) {
        if (data == null) return null;

        //根据，分割字符串
        String hobbyStr = data.toString();
        String[] hobbyArray = hobbyStr.split(",");

        //转换成list
        List<String> hobbies = Arrays.asList(hobbyArray);
        return hobbies;
    }
}
