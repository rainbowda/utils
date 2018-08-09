# 1 介绍
execl导入工具  

整个项目的代码结构如下  

```
    \---execlExport # 导出工具包
        |   AsyncExportExecl.java #多线程导出
        |   ExeclImport.java # 导出工具类
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

com
\---utils
    +---demo # 案例相关
    |   |   ExeclImportApplication.java # springboot启动类
    |   |
    |   +---bean
    |   |       DemoBean.java # 测试bean
    |   |
    |   +---controller
    |   |       ExeclImportController.java # 测试从web导入execl和从文件导入execl
    |   |
    |   \---importParam
    |       |   DemoImportParam.java # 导入参数
    |       |
    |       \---dataConversion
    |               HobbyConversion.java # 爱好属性导入转换类
    |
    \---execlImport # 导入工具包
        |   ExeclImport.java # 导入工具类
        |
        +---data
        |       CellParam.java # 导入列参数类
        |
        \---dataConversion
            |   DataImportConversion.java # 属性导入转换接口
            |
            \---impl
                    DateConversion.java # 日期属性导入转换接口
                    MapConversion.java  # 键值对属性导入转换接口              
```
简单的来说execl导入可以分为几步    
1. 上传execl文件
2. 将execl转换为数据，有需要的数据进行转换


# 2 使用
## 2.1 ExeclImport工具类
首先实例化ExeclImport工具类，我这里提供了一个构造函数  

```java
public ExeclImport(Class<T> clazz, List<CellParam> cellParams)
```

| 参数       | 含义                                                |
| ---------- | --------------------------------------------------- |
| clazz      | Class对象（需要转换为Bean的Class对象）              |
| cellParams | CellParam的list列表（每一列对应的字段及数据转换类） |



------

实例化ExeclImport工具类之后，需要调用importExecl方法,方法定义如下  

```
public List<T> importExecl(InputStream is)
```

只需要传入InputStream即可。  


## 2.2 导入列参数类CellParam
CellParam类代码如下  
```java
public class CellParam {
	private String fieldName;
    private DataImportConversion conversion;
	
	//Set Get Constructor
}
```
### 2.2.1 属性
可以看到CellParam类有两个属性  
```java
private String fieldName;
private DataImportConversion conversion;
```
| 参数       | 含义             |
| ---------- | ---------------- |
| fieldName  | 列对应Bean的属性 |
| conversion | 数据转换类       |

## 2.3 数据转换接口DataImportConversion
```java
public interface DataImportConversion<T> {
    T transferData(Object data);
}
```
我这里默认提供了两种数据转换，一个是键值对，另一个是日期  

### 2.3.1 键值对数据转换
键值对数据转换类是为了将一些通用数据转换而提供的。  
例如：男女、是否和一些不同名称对应的不同数字（正常-->0,异常-->1,其他-->2）   
> 使用者可以通过传入的map的泛型决定返回值的类型。
```java
public class MapConversion<K,V> implements DataImportConversion<V> {

    private Map<K,V> map ;

    private V defaultReturnValue;

    public MapConversion(Map<K, V> map) {
        this(map,null);
    }

    public MapConversion(Map<K, V> map,V defaultReturnValue) {
        this.map = map;
        this.defaultReturnValue = defaultReturnValue;
    }

    @Override
    public V transferData(Object data) {
        if (map == null) return null;

        //如果data为null且map的null对应的值不为null，则直接返回map中null对应的值
        if (data == null && map.get(null) != null){
            return map.get(null);
        }

        //循环查找对应的key
        for (Map.Entry<K,V> entry:map.entrySet()){
            if (entry.getKey() != null && entry.getKey().equals(data)){
                return entry.getValue();
            }
        }
        //如果map里面找不到对应的数据，则返回defaultReturnValue
        return defaultReturnValue;
    }
}
```

### 2.3.2 日期数据转换
提供日期转换功能，通过传入的日期转换格式进行转换。
```java
public class DateConversion implements DataImportConversion<Date> {
    private SimpleDateFormat format;

    public DateConversion(String pattern) {
        this.format = new SimpleDateFormat(pattern);
    }

    @Override
    public Date transferData(Object data) {
        try {
            return format.parse(data.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
```

# 3 案例
## 3.1 场景
原始数据如下  

| 姓名 | 性别 | 出生日期            | 爱好      |
| ---- | ---- | ------------------- | --------- |
| 尘心 | 女   | 2018-08-08 14:59:11 | 舞刀,弄枪 |
| 千月 | 男   | 2018-08-08 14:59:11 | 唱歌,跳舞 |

需要转换为实体bean的列表，如下
```  
DemoBean{name='尘心', sex=0, birthday=Wed Aug 08 14:13:45 CST 2018, hobbies=[舞刀, 弄枪]}
DemoBean{name='千月', sex=1, birthday=Wed Aug 08 14:13:45 CST 2018, hobbies=[唱歌, 跳舞]}
```

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

## 3.2 数据转换
可以看到有三个属性需要转换，分别是性别、日期和爱好。性别和日期的数据转换可以使用默认的数据转换。爱好需要将字符串根据`，`分割并转换为list列表数据，下面是爱好的数据转换，  
```java
public class HobbyConversion implements DataImportConversion<List<String>> {
    @Override
    public List<String> transferData(Object data) {
        if (data == null) return null;

        //根据，分割字符串
        String hobbyStr = data.toString();
        String[] hobbyArray = hobbyStr.split(",");

        //转换成list
        List<String> hobbies = Arrays.asList(hobbyArray);
        return hobbies;
    }
}
```
## 3.3 导入参数类
数据转换类写好了之后，开始编写导入参数类，代码如下
```java
public class DemoImportParam {
    public static List<CellParam> getCellParams(){
        Map<String,Integer> sexMap = new HashMap<>();
        sexMap.put("女",0);
        sexMap.put("男",1);

        List<CellParam> cellParams = new ArrayList<>();
        cellParams.add(new CellParam("name"));
        cellParams.add(new CellParam("sex", new MapConversion(sexMap)));
        cellParams.add(new CellParam("birthday", new DateConversion("yyyy-MM-dd HH:mm:ss")));
        cellParams.add(new CellParam("hobbies", new HobbyConversion()));
        return cellParams;
    }
}
```
在DemoImportParam类中可以看到一个静态方法getCellParams，返回List<CellParam>。方法内部先定义了一个mao对象，存放性别字符串对应的数字，然后就是List<CellParam>的定义。  
可以看到总共有4个列参数

- 第一列标题名称为姓名，对应的属性名称为name
- 第二列标题名称为性别，对应的属性名称为sex，数据转换对象MapConversion
- 第三列标题名称为出生日期，对应的属性名称为birthday，日期转换
- 第四列标题名称为爱好，对应的属性名称为hobbies，数据转换对象HobbyConversion

## 3.4 导出

接下来有两种数据导出方式，一种是url访问弹出下载execl文件，另外一种是导出execl到文件
### 3.4.1 从web导入execl

```java
@ResponseBody
@PostMapping("/import")
public List<DemoBean> importByWeb(MultipartFile file) throws Exception {
    ExeclImport execlImport = new ExeclImport(DemoBean.class, DemoImportParam.getCellParams());
    List<DemoBean> list = execlImport.importExecl(file.getInputStream());

    return list;
}
```
### 3.4.2 从文件导入execl
```java
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
```
