# 灰度控制工具



## 灰度参数实体

GrayControlBean

```java
public class GrayControlBean {
	//是否开启
    private Boolean open;
	//模
    private Long targetMod;
	//白名单
    private List<Long> whiteList;

}
```



定义一个配置类专门获取灰度配置

## 灰度配置

```java
@Component
public class GrayControlConfig {

    private Map<String, GrayControlBean> grayControlBeanMap = new HashMap<>();

    public Map<String, GrayControlBean> getGrayControlBeanMap() {
        return grayControlBeanMap;
    }

    public void setGrayControlBeanMap(Map<String, GrayControlBean> grayControlBeanMap) {
        this.grayControlBeanMap = grayControlBeanMap;
    }

    /**
     * 模拟数据来源
     * 初始化数据
     */
    @PostConstruct
    public void init(){
//        String json = "{\"gray1\":{\"targetMod\":3,\"whiteList\":[100232],\"open\":true},\"gray2\":{\"targetMod\":3,\"whiteList\":[100232],\"open\":true}}";
//        Gson gson = new Gson();
//        Map<String, GrayControlBean> map = gson.fromJson(json, Map.class);


        GrayControlBean grayControlBean1 = new GrayControlBean();
        GrayControlBean grayControlBean2 = new GrayControlBean();

        grayControlBean1.setOpen(true);
        grayControlBean2.setOpen(true);

        grayControlBean1.setTargetMod(30000L);
        grayControlBean2.setTargetMod(40000L);

        grayControlBean1.setWhiteList(Arrays.asList(100232L));
        grayControlBean2.setWhiteList(Arrays.asList());

        Map<String, GrayControlBean> grayControlBeanMap = new HashMap<>();
        grayControlBeanMap.put("gray1",grayControlBean1);
        grayControlBeanMap.put("gray2",grayControlBean2);

        setGrayControlBeanMap(grayControlBeanMap);


        System.out.println(new Gson().toJson(grayControlBeanMap));
    }
}
```



## 灰度工具类



```java
@Component
public class GrayControlUtils {

    @Autowired
    private GrayControlConfig grayControlConfig;


    /**
     * true表示拦截
     * false表示不拦截
     * @param grayControlKey
     * @param id
     * @return
     */
    public boolean isControl(String grayControlKey, Long id){

        GrayControlBean grayControlBean = grayControlConfig.getGrayControlBeanMap().get(grayControlKey);


        if (grayControlBean == null){
            return true;
        }

        if (!isOpen(grayControlBean.getOpen())){
            return false;
        }

        if (isInWhiteList(id,grayControlBean.getWhiteList())){
            return false;
        }

        if(isInMod(id, grayControlBean.getTargetMod())){
            return false;
        }


        return true;
    }
}
```



### 是否开启灰度

```java
private boolean isOpen(Boolean open){
    if(open != null && open){
        return true;
    }

    return false;
}
```



### 是否白名单

```java
private boolean isInWhiteList(Long targetId, List<Long> whiteList){
    if (whiteList == null){
        return false;
    }

    if (!whiteList.contains(targetId)){
        return false;
    }

    return true;
}
```



### 取模

```java
private boolean isInMod(Long targetId, Long targetMod){
    if((targetId == null) || (targetMod == null) || (targetId % targetMod != 0)){
        return false;
    }

    return true;
}
```





## 测试



```java
@Test
public void contextLoads() {
    boolean gray = grayControlUtils.isControl("gray1", 100232L);
    System.out.println("是否拦截:"+gray);


    gray = grayControlUtils.isControl("gray2", 100232L);
    System.out.println("是否拦截:"+gray);
}
```

