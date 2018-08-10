package com.utils.demo.controller;

import com.utils.demo.bean.DemoBean;
import com.utils.demo.importParam.DemoImportParam;
import com.utils.excelImport.ExcelImport;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
public class ExcelImportController {

    /**
     * 从web导入excel
     */
    @GetMapping("/import")
    public String importByWebPage(){
        return "importExcelPage";
    }

    /**
     * 从web导入excel
     * @param file
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/import")
    public List<DemoBean> importByWeb(MultipartFile file) throws Exception {
        ExcelImport excelImport = new ExcelImport(DemoBean.class, DemoImportParam.getCellParams());
        List<DemoBean> list = excelImport.importExcel(file.getInputStream());

        return list;
    }

    /**
     * 从文件导入excel
     * @throws Exception
     */
    @Test
    public void importByFile() throws Exception {
        File file = new File("F:\\导出demo.xlsx");
        FileInputStream inputStream = new FileInputStream(file);

        //导入转换
        ExcelImport excelImport = new ExcelImport(DemoBean.class, DemoImportParam.getCellParams());
        List<DemoBean> list = excelImport.importExcel(inputStream);

        //输出
        for (DemoBean bean:list){
            System.out.println(bean);
        }
    }

}
