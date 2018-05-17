package com.utils.poi.update_2;

import com.utils.poi.bean.Book;
import com.utils.poi.bean.Student;
import com.utils.poi.update_2.bean.CellEntity;
import com.utils.poi.update_2.bean.SheetEntity;

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
        ExportExcel<Book> ex2 = new ExportExcel<Book>();

        List<Book> bookList =  getBookList();;
        List<Student> studentList = getStudentList();

        try {


            OutputStream out = new FileOutputStream("E://a.xls");
            OutputStream out2 = new FileOutputStream("E://b.xls");

            ex.exportExcel(getStudentSheetEntity("学生",studentList), out);
            ex2.exportExcel(getBookSheetEntity("书本",bookList), out2);

            out.close();
            out2.close();
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
        cellEntitys.add(new CellEntity("性别","sex"));
        cellEntitys.add(new CellEntity("出生日期","birthday"));

        SheetEntity entity = new SheetEntity(sheetName,cellEntitys , studentList);

        return entity;
    }

    private static SheetEntity getBookSheetEntity(String sheetName,List<Book> bookList){

        List<CellEntity> cellEntitys = new ArrayList<CellEntity>();
        cellEntitys.add(new CellEntity("图书编号","bookId"));
        cellEntitys.add(new CellEntity("图书名称","name"));
        cellEntitys.add(new CellEntity("图书作者","author"));
        cellEntitys.add(new CellEntity("图书价格","price"));
        cellEntitys.add(new CellEntity("图书ISBN","isbn"));
        cellEntitys.add(new CellEntity("图书出版社","pubName"));

        SheetEntity entity = new SheetEntity(sheetName, cellEntitys, bookList);

        return entity;
    }

    private static List<Student> getStudentList() {
        List<Student> datas = new ArrayList<Student>();
        datas.add(new Student(10000001, "张三", 20, true, new Date()));
        datas.add(new Student(20000002, "李四", 24, false, new Date()));
        datas.add(new Student(30000003, "王五", 22, true, new Date()));
        return datas;
    }

    private static List<Book> getBookList() {
        List<Book> datas = new ArrayList<>();
        datas.add(new Book(1, "jsp", "leno", 300.33f, "1234567", "清华出版社"));
        datas.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567", "阳光出版社"));
        datas.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567", "清华出版社"));
        datas.add(new Book(4, "c++经典", "leno", 400.33f, "1234567", "清华出版社"));
        datas.add(new Book(5, "c#入门", "leno", 300.33f, "1234567", "汤春秀出版社"));
        return datas;
    }


}
