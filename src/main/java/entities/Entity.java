package entities;

import annotations.Column;
import annotations.Key;
import annotations.Table;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Entity {

    public final static String KEYMARKER = "KeyColumnKeyColumn202211111506";

    public Map<String,Object> getKeys()
    {
        Map<String,Object> keys = new HashMap<>();
        Object fieldValue;
        String key;
        try
        {
            Field[] fields = getClass().getDeclaredFields();
            for(int i = 0; i < fields.length; i++)
            {
                if(fields[i].isAnnotationPresent(Column.class) && fields[i].isAnnotationPresent(Key.class))
                {
                    fieldValue = fields[i].get(this);
                    if(fieldValue != null)
                    {
                        key = fields[i].getAnnotation(Column.class).columnName();
                        keys.put(key, fieldValue.getClass() == String.class ? "'" + fieldValue + "'" : fieldValue);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return keys;
    }

    public Map<String,Object> getAllValues()
    {
        Map<String,Object> values = new HashMap<>();
        String keyMap;
        Object fieldValue;
        try
        {
            Field[] fields = getClass().getDeclaredFields();
            for(int i = 0; i < fields.length; i++)
            {
                if(fields[i].isAnnotationPresent(Column.class))
                {
                    fieldValue = fields[i].get(this);
                    if(fieldValue != null)
                    {
                        keyMap = fields[i].getAnnotation(Column.class).columnName();
                        if (fields[i].isAnnotationPresent(Key.class))
                            keyMap = KEYMARKER + keyMap;
                        values.put(keyMap, fieldValue.getClass() == String.class ? "'" + fieldValue + "'" : fieldValue);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return values;
    }

    public String getTable()
    {
        return this.getClass().getAnnotation(Table.class).tableName();
    }



}
