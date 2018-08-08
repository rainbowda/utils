## 介绍
execl导出工具

整个项目的代码结构如下

```
com
\---utils
    +---demo # 案例相关
    |   |   ExeclExportApplication.java # springboot启动类
    |   |
    |   +---bean
    |   |       DemoBean.java # 测试bean
    |   |
    |   +---controller
    |   |       ExeclExportController.java # 测试url访问弹出下载execl文件和导出execl到文件
    |   |
    |   \---exportParam
    |       |   DemoExportParam.java # 导出参数
    |       |
    |       \---dataConversion
    |               HobbyConversion.java # 爱好属性导出转换类
    |               SexConversion.java # 性别属性导出转换类
    |
    \---execlExport # 导出工具包
        |   AsyncExportExecl.java #多线程导出
        |   ExportExcel.java # 导出工具类
        |
        +---data
        |       BaseParam.java # 基础导出参数类
        |
        +---dataConversion
        |       DataExportConversion.java # 属性导出转换接口
        |
        +---defaultDataHandle # 默认的数据处理
        |       AbstractDataHandler.java
        |       BooleanDataHandler.java
        |       DataHandlerFactory.java
        |       DateDataHandler.java
        |       StringDataHandler.java
        |
        \---style # 默认的样式
                AbstractCellStyle.java
                DefaultDataCellStyle.java
                DefaultTitleCellStyle.java
```
简单的来说execl导出可以分为几步  
1. 获得需要导出的数据
2. 设置execl的工作表(sheet)名称
3. 设置当前工作表的第一行，也就是标题行
4. 将数据逐行填充，有需要的数据进行转换


## 使用
### ExportExcel工具类
首先实例化ExportExcel工具类，我这里提供了三个构造函数
```java
public ExportExcel() 

public ExportExcel(SXSSFWorkbook workbook) 

public ExportExcel(SXSSFWorkbook workbook, AbstractCellStyle titleCellStyle, AbstractCellStyle dataCellStyle) 
```
通常使用的是无参构造函数。另外两个都需要自己实例化workbook对象，有三个参数的构造函数，需要传入workbook、标题行样式对象、数据行样式对象。关于样式对象下方有说明。

------

实例化ExportExcel工具类之后，需要调用exportExcel方法,方法定义如下

```
public void exportExcel(String sheetName, BaseParam baseParam, OutputStream out)
```



| 参数名称  | 参数内容                                  |
| --------- | ----------------------------------------- |
| sheetName | 工作表(sheet)的名称                       |
| baseParam | 继承BaseParam的导出参数对象（后面会说明） |
| out       | OutputStream对象、例如FileOutputStream    |



### 样式抽象类AbstractCellStyle

```java
public abstract class AbstractCellStyle {
    Workbook workbook;
    CellStyle style;
    Font font;

    public AbstractCellStyle(Workbook workbook) {
        this.workbook = workbook;
        style = workbook.createCellStyle();
        font = workbook.createFont();
    }

    protected abstract void setStyle();

    protected abstract void setFont();


    public CellStyle getCellStyle() {
        style.setFont(font);
        return style;
    }
}
```
通过继承样式抽象类AbstractCellStyle，可以实现下方两个方法
```java
// 设置样式
protected abstract void setStyle();
// 设置字体
protected abstract void setFont();
```
通过这两个方法可以修改单元格的样式和字体。

### 基础导出参数类BaseParam
BaseParam类代码如下
```java
public class BaseParam {
	public List data = new ArrayList<>();
	public List<ColumnParam> columnParams = new ArrayList<>();
	
	//Set Get Constructor

    /**
     * 数据行参数
     */
	public class ColumnParam{```}
}
```
#### 属性
可以看到BaseParam类有两个属性  
```java
public List data = new ArrayList<>();
public List<ColumnParam> columnParams = new ArrayList<>();
```
data毫无疑问是存放需要导出的数据，而columnParams是存放每一列的数据，现在来看看内部类ColumnParam  

#### 内部类ColumnParam
```java
public class ColumnParam{
		private String headerName;
		private String fieldName;
		private DataExportConversion conversion;//数据转换
		
		//Set Get Constructor
    }
```
##### 属性
| 属性名称  | 属性内容                                  |
| --------- | ----------------------------------------- |
| headerName | 标题名称                   |
| fieldName | 实体类对应的属性名 |
| conversion       | 数据转换对象 |

##### 数据转换接口DataExportConversion
```java
public interface DataExportConversion<T> {
    String transferData(T data);
}
```
比如说，获取出来的数据是0、1，然后你需要将数据转换成女、男，那么就可以实现数据转换接口DataExportConversion，自定义转换输出的格式，代码如下  
```java
public class SexConversion implements DataExportConversion<Integer> {
    @Override
    public String transferData(Integer data) {
        if (0 == data){
            return "女";
        } else if (1 == data){
            return "男";
        }
        return "不详";
    }
}
```

## 案例
### 场景
原始数据如下  

| 姓名 | 性别 | 出生日期            | 爱好(List对象) |
| ---- | ---- | ------------------- | -------------- |
| 尘心 | 0    | 2018-08-08 14:59:11 | [舞刀,弄枪]    |
| 千月 | 1    | 2018-08-08 14:59:11 | [唱歌,跳舞]    |

需要转换为下方内容

| 姓名 | 性别 | 出生日期            | 爱好      |
| ---- | ---- | ------------------- | --------- |
| 尘心 | 女   | 2018-08-08 14:59:11 | 舞刀,弄枪 |
| 千月 | 男   | 2018-08-08 14:59:11 | 唱歌,跳舞 |

实体类如下   
```java
public class DemoBean {

    //姓名
    private String name;

    //性别，0->女,1->男
    private Integer sex;

    //出生日期
    private Date birthday;

    //爱好
    private List<String> hobbies;

    //Set Get
    
}
```

### 数据转换
可以看到有两个属性需要转换，分别是性别和爱好。性别的数据转换上面已经有了，就不贴出来了，下面是爱好的数据转换  
```java
public class HobbyConversion implements DataExportConversion<List<String>> {
    @Override
    public String transferData(List<String> data) {
        StringBuilder hobby = new StringBuilder();

        for (String s:data){
            hobby.append(s).append(",");
        }

        //移除最后一个逗号
        if (hobby.length() >= 1){
            hobby.deleteCharAt(hobby.length()-1);
        }

        return hobby.toString();
    }
}
```
### 导出参数类
数据转换类写好了之后，开始编写导出参数类，代码如下
```java
public class DemoExportParam extends BaseParam {
    public DemoExportParam(List<DemoBean> list) {
        setData(list);
        setColumnParam();
    }

    private void setColumnParam() {
        columnParams.add(new ColumnParam("姓名","name"));
        columnParams.add(new ColumnParam("性别","sex", new SexConversion()));
        columnParams.add(new ColumnParam("出生日期","birthday"));
        columnParams.add(new ColumnParam("爱好","hobbies", new HobbyConversion()));
    }
}
```
在实例化DemoExportParam时，需要传入导出的数据，同时设置每一列对应的列参数ColumnParam。  
可以看到总共有4个列参数

- 第一列标题名称为姓名，对应的属性名称为name
- 第二列标题名称为性别，对应的属性名称为sex，还有数据转换对象SexConversion
- 第三列标题名称为出生日期，对应的属性名称为birthday
- 第四列标题名称为爱好，对应的属性名称为hobbies，数据转换对象HobbyConversion

### 导出
先写一个生成数据的方法，如下
```java
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
```
接下来有两种数据导出方式，一种是url访问弹出下载execl文件，另外一种是导出execl到文件
#### url访问弹出下载execl文件

```java
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
```
#### 导出execl到文件
```java
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
```
