package com.utils.execl.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utils.execl.export.data.BaseParam;
import com.utils.execl.export.defaultDataHandle.DataHandlerFactory;
import com.utils.execl.export.style.AbstractCellStyle;
import com.utils.execl.export.style.DefaultDataCellStyle;
import com.utils.execl.export.style.DefaultTitleCellStyle;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

@SuppressWarnings("rawtypes")
public class ExportExcel {

    private SXSSFWorkbook workbook;
    private AbstractCellStyle titleCellStyle;//标题行样式
    private AbstractCellStyle dataCellStyle;//数据行样式

    private Integer dataLimit = 500000;


    public ExportExcel() {
        // 声明一个工作薄
        this(new SXSSFWorkbook(5000));
    }

    public ExportExcel(SXSSFWorkbook workbook) {
        this(workbook, new DefaultTitleCellStyle(workbook), new DefaultDataCellStyle(workbook));
    }

    public ExportExcel(SXSSFWorkbook workbook, AbstractCellStyle titleCellStyle, AbstractCellStyle dataCellStyle) {
        this.workbook = workbook;
        this.titleCellStyle = titleCellStyle;
        this.dataCellStyle = dataCellStyle;
        this.workbook.setCompressTempFiles(true);
    }

    /**
     * 导出功能
     *
     * @param sheetName
     * @param baseParam
     * @param out
     */
    public void exportExcel(String sheetName, BaseParam baseParam, OutputStream out) {
        List data = baseParam.getData();
        int sheetNum = data.size() / dataLimit + 1;
        int startIndex = 0, endIndex = dataLimit;


        for (int i = 0; i < sheetNum; i++) {
            if (endIndex > data.size()) {
                endIndex = data.size();
            }
            createSheet(sheetName + (i + 1), baseParam, startIndex, endIndex);

            startIndex += dataLimit;
            endIndex += dataLimit;
        }

        writeAndDispose(out);
    }

    /**
     * 分sheet导出
     *
     * @param sheetName
     * @param baseParam
     * @param startIndex
     * @param endIndex
     */
    public void createSheet(String sheetName, BaseParam baseParam, int startIndex, int endIndex) {
        // 生成一个表格
        Sheet sheet = null;
        synchronized (this) {
            sheet = workbook.createSheet(sheetName);
        }

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);

        // 声明一个画图的顶级管理器
        Drawing patriarch = sheet.getDrawingPatriarch();

        setExcelHeader(0, baseParam.getColumnParams(), sheet);

        setData(baseParam.getColumnParams(), baseParam.getData().subList(startIndex, endIndex), workbook, sheet, patriarch);
    }

    /**
     * 写入OutputStream，且关闭删除workbook临时文件
     * @param out
     */
    public void writeAndDispose(OutputStream out) {
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.dispose();// 删除临时文件
            }
        }
    }

    /**
     * @Description: 设置数据
     */
    private void setData(List<BaseParam.ColumnParam> columnParams, Collection dataset, Workbook workbook, Sheet sheet, Drawing patriarch) {
        // 遍历集合数据，产生数据行
        Iterator it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            Row row = sheet.createRow(index);
            Object t = it.next();
            for (int i = 0; i < columnParams.size(); i++) {
                BaseParam.ColumnParam columnParam = columnParams.get(i);
                Cell cell = row.createCell(i);
                cell.setCellStyle(dataCellStyle.getCellStyle());
                try {
                    Object value = PropertyUtils.getProperty(t, columnParam.getFieldName());
                    if (value == null) continue;

                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
                                index);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {

                        //判断columnParam的数据处理类是否为null，是的话使用默认的数据处理，否则使用columnParam的
                        if (columnParam.getConversion() == null)
                            textValue = DataHandlerFactory.dataHandle(value);
                        else
                            textValue = columnParam.getConversion().transferData(value);

                        //如果textValue为null，跳过
                        if (textValue == null) continue;

                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            cell.setCellValue(textValue);
                        }

                        //自适应宽度
                        int columnWidth = sheet.getColumnWidth(i);
                        int newColumnWidth = textValue.getBytes().length * 1 * 256 + 256 * 2;
                        if (newColumnWidth > columnWidth) {
                            sheet.setColumnWidth(i, newColumnWidth);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
    }

    private void setExcelHeader(Integer index, List<BaseParam.ColumnParam> columnParams, Sheet sheet) {
        if (index == null || index < 0) {
            index = 0;
        }
        Row row = sheet.createRow(index);
        for (int i = 0; i < columnParams.size(); i++) {
            BaseParam.ColumnParam columnParam = columnParams.get(i);

            Cell cell = row.createCell(i);
            cell.setCellStyle(titleCellStyle.getCellStyle());
            cell.setCellValue(columnParam.getHeaderName());
            sheet.setColumnWidth(i, columnParam.getHeaderName().getBytes().length * 2 * 256);
        }
    }
}
