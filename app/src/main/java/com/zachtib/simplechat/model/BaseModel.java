package com.zachtib.simplechat.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseModel {
    public Map<String, Object> getMapping() {
        Map<String, Object> result = new HashMap<>();
        for (Field field : getClass().getDeclaredFields()) {
            try {
                Object value = field.get(this);
                if (value != null) {
                    result.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
