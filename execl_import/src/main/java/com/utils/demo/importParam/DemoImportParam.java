package com.utils.demo.importParam;


import com.utils.demo.importParam.dataConversion.HobbyConversion;
import com.utils.execlImport.data.CellParam;
import com.utils.execlImport.dataConversion.impl.DateConversion;
import com.utils.execlImport.dataConversion.impl.MapConversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoImportParam {
    public static List<CellParam> getCellParams(){
        Map<String,Integer> sexMap = new HashMap<>();
        sexMap.put("女",0);
        sexMap.put("男",1);

        List<CellParam> cellParams = new ArrayList<>();
        cellParams.add(new CellParam("name"));
        cellParams.add(new CellParam("sex", new MapConversion(sexMap)));
        cellParams.add(new CellParam("birthday", new DateConversion("yyyy-MM-dd HH:mm:ss")));
        cellParams.add(new CellParam("hobbies", new HobbyConversion()));
        return cellParams;
    }
}
