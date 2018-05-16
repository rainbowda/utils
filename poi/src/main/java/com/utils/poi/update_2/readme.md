重构步骤<br>
1.将一些方法抽离成类(样式-->将设置style和设置font抽象成两个方法。然后通过构造函数传入workbook，并初始化style和font对象) <br>
	-->AbstractCellStyle<br>
		-->DefaultTitleCellStyle(默认标题行样式)<br>
		-->DefaultDataCellStyle(默认数据行样式)<br>
2.增加ExportExcel两个属性titleCellStyle和dataCellStyle<br>
3.增加构造函数三个参数<br>
4.删除原先获得样式的两个方法<br>

5.将传入的String title, String[] headers, Collection<T> dataset可以用一个类来表示<br>
一个execl分为多个sheet，一个sheet可以分为标题和数据行，数据行又可以分为数据和数据对应的字段<br>

比如说把sheet里面的数据行抽象成CellEntity，里面包含标题，标题对应的属性名<br>
然后在设计一个sheet类，sheet类里面包含多个ExcelFiled和一个sheet名称
