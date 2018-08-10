package com.utils.excelImport;

import com.utils.excelImport.data.CellParam;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelImport<T> {
    private Class<T> clazz;
    private List<CellParam> cellParams;

    public ExcelImport(Class<T> clazz, List<CellParam> cellParams) {
        this.clazz = clazz;
        this.cellParams = cellParams;
    }

    public List<T> importExcel(InputStream is) throws Exception{

        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        List<T> list = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum() ; i++){
            list.add(handleRow(sheet.getRow(i)));
        }
        return list;
    }

    /**
     * 处理每一行数据
     * @param row
     * @return
     * @throws Exception
     */
    private T handleRow(Row row) throws Exception{
        T t = clazz.newInstance();
        for (int i = 0; i< row.getLastCellNum() && i < cellParams.size(); i++){
            handleCell(t, cellParams.get(i),row.getCell(i));
        }
        return t;
    }

    /**
     * 处理每一个单元格数据
     * @param t
     * @param cellParam
     * @param cell
     * @throws Exception
     */
    private void handleCell(T t, CellParam cellParam, Cell cell) throws Exception{
        Object value = getCellValueByType(cell);
        if (cellParam.getConversion() != null)
            value = cellParam.getConversion().transferData(value);
        PropertyUtils.setSimpleProperty(t,cellParam.getFieldName(),value);
    }

    /**
     * 根据单元格的数据类型返回对应的数据
     * @param cell
     * @return
     */
    private Object getCellValueByType(Cell cell) {
        int type = cell.getCellType();
        switch (type) {
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
        }
        return null;
    }
}


