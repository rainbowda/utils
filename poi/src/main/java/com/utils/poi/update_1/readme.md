重构手法01：Extract Method （提炼函数）

1.创建一系列的对象（workbook、sheet...）
2.创建样式、字体
4.产生表格标题行
5.遍历集合数据，产生数据行

可以将exportExcel方法拆分成以上五个部分
首先可以将workbook变成类的属性
然后将其他部分拆成方法