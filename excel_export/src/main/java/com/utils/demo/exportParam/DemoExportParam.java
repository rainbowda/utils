package com.utils.demo.exportParam;

import com.utils.demo.bean.DemoBean;
import com.utils.excelExport.data.BaseParam;
import com.utils.demo.exportParam.dataConversion.HobbyConversion;
import com.utils.demo.exportParam.dataConversion.SexConversion;

import java.util.List;


public class DemoExportParam extends BaseParam {
    public DemoExportParam(List<DemoBean> list) {
        setData(list);
        setColumnParam();
    }

    private void setColumnParam() {
        columnParams.add(new ColumnParam("姓名","name"));
        columnParams.add(new ColumnParam("性别","sex", new SexConversion()));
        columnParams.add(new ColumnParam("出生日期","birthday"));
        columnParams.add(new ColumnParam("爱好","hobbies", new HobbyConversion()));
    }
}
