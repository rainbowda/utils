重构步骤
1.将一些方法抽离成类(样式-->将设置style和设置font抽象成两个方法。然后通过构造函数传入workbook，并初始化style和font对象) 
	-->AbstractCellStyle
		-->DefaultTitleCellStyle(默认标题行样式)
		-->DefaultDataCellStyle(默认数据行样式)
2.增加ExportExcel两个属性titleCellStyle和dataCellStyle
3.增加构造函数三个参数
4.删除原先获得样式的两个方法