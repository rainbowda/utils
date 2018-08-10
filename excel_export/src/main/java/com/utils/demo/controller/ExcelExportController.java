package com.utils.demo.controller;

import com.utils.excelExport.ExportExcel;
import com.utils.demo.bean.DemoBean;
import com.utils.demo.exportParam.DemoExportParam;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ExcelExportController {

    /**
     * url访问弹出下载excel文件
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/export")
    public void exportByWeb(HttpServletResponse response) throws IOException {

        OutputStream out = new BufferedOutputStream(response.getOutputStream());

        response.reset();
        String headStr = "attachment; filename=" + URLEncoder.encode("导出demo.xlsx", "utf-8");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", headStr);

        //获得导出数据
        List<DemoBean> list = getDemoBeanList();

        DemoExportParam demoExportParam = new DemoExportParam(list);

        ExportExcel exportExcel = new ExportExcel();
        exportExcel.exportExcel("demo", demoExportParam, response.getOutputStream());

        out.flush();
        out.close();
    }

    /**
     * 导出excel到文件
     * @throws IOException
     */
    @Test
    public void exportByFile() throws IOException {

        File file = new File("F:\\导出demo.xlsx");
        FileOutputStream out = new FileOutputStream(file);

        //获得导出数据
        List<DemoBean> list = getDemoBeanList();

        DemoExportParam demoExportParam = new DemoExportParam(list);

        ExportExcel exportExcel = new ExportExcel();
        exportExcel.exportExcel("demo", demoExportParam, out);

        out.flush();
        out.close();
    }

    /**
     * @return bean数据
     */
    private List<DemoBean> getDemoBeanList(){
        DemoBean man = new DemoBean();
        DemoBean woman = new DemoBean();

        String[] manHobbys = {"舞刀", "弄枪"};
        String[] womanHobbys = {"唱歌", "跳舞"};

        man.setName("尘心").setBirthday(new Date()).setSex(0).setHobbies(Arrays.asList(manHobbys));
        woman.setName("千月").setBirthday(new Date()).setSex(1).setHobbies(Arrays.asList(womanHobbys));


        //将两个bean添加到list中
        List<DemoBean> list = new ArrayList<>();
        list.add(man);
        list.add(woman);

        return list;
    }
}
