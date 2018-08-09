package com.utils.execlImport.dataConversion.impl;

import com.utils.execlImport.dataConversion.DataImportConversion;

import java.util.Map;

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
