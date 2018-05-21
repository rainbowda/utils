package com.utils.poi.update_2;

import com.utils.poi.bean.Student;
import com.utils.poi.update_2.bean.CellEntity;
import com.utils.poi.update_2.bean.SheetEntity;
import com.utils.poi.update_2.dataConversion.DataConversion;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        // 测试学生
        ExportExcel<Student> ex = new ExportExcel<Student>();
        // 测试图书

        List<Student> studentList = getStudentList();

        try {


            OutputStream out = new FileOutputStream("E://a.xls");

            ex.exportExcel(getStudentSheetEntity("学生",studentList), out);

            out.close();
            System.out.println("excel导出成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SheetEntity getStudentSheetEntity(String sheetName,List<Student> studentList){

        List<CellEntity> cellEntitys = new ArrayList<CellEntity>();
        cellEntitys.add(new CellEntity("学号","id"));
        cellEntitys.add(new CellEntity("姓名","name"));
        cellEntitys.add(new CellEntity("年龄","age"));
        cellEntitys.add(new CellEntity("性别","sex", new SexDataConversion()));
        cellEntitys.add(new CellEntity("出生日期","birthday"));

        SheetEntity entity = new SheetEntity(sheetName,cellEntitys , studentList);

        return entity;
    }

    private static List<Student> getStudentList() {
        List<Student> datas = new ArrayList<Student>();
        datas.add(new Student(10000001, "张三", 20, true, new Date()));
        datas.add(new Student(20000002, "李四", 24, false, new Date()));
        datas.add(new Student(30000003, "王五", 22, true, new Date()));
        return datas;
    }

}

class SexDataConversion implements DataConversion{

    @Override
    public String transferData(Object data) {
        if (data instanceof Boolean) {
            boolean bValue = (Boolean) data;
            String textValue = "男";
            if (!bValue) {
                textValue = "女";
            }
            return textValue;
        }
        return null;
    }
}
