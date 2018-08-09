package com.utils.demo.controller;

import com.utils.demo.bean.DemoBean;
import com.utils.demo.importParam.DemoImportParam;
import com.utils.execlImport.ExeclImport;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
public class ExeclImportController {

    /**
     * 从web导入execl
     */
    @GetMapping("/import")
    public String importByWebPage(){
        return "importExeclPage";
    }

    /**
     * 从web导入execl
     * @param file
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/import")
    public List<DemoBean> importByWeb(MultipartFile file) throws Exception {
        ExeclImport execlImport = new ExeclImport(DemoBean.class, DemoImportParam.getCellParams());
        List<DemoBean> list = execlImport.importExecl(file.getInputStream());

        return list;
    }

    /**
     * 从文件导入execl
     * @throws Exception
     */
    @Test
    public void importByFile() throws Exception {
        File file = new File("F:\\导出demo.xlsx");
        FileInputStream inputStream = new FileInputStream(file);

        //导入转换
        ExeclImport execlImport = new ExeclImport(DemoBean.class, DemoImportParam.getCellParams());
        List<DemoBean> list = execlImport.importExecl(inputStream);

        //输出
        for (DemoBean bean:list){
            System.out.println(bean);
        }
    }

}
