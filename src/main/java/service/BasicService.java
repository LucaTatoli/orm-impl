package service;

import annotations.Table;
import entities.Entity;
import entitymanager.EntityManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class BasicService<T extends Entity> {

    protected Class<T> type;

    public BasicService(Class<T> type)
    {
        this.type = type;
    }

    protected EntityManager entityManager;

    public ArrayList<T> selectAll()
    {
        ArrayList<T> all = new ArrayList<>();

        ResultSet set = entityManager.executeQuery(String.format("select * from %s;", type.getAnnotation(Table.class).tableName()));
        try
        {
            while (set.next())
            {
                String entityString = String.format("%s:%s:%s:%s:%s", set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5));
                all.add(transformer(entityString));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return all;
    }

    public boolean save(T entity)
    {
        return entityManager.save(entity);
    }

    public boolean update(T entity)
    {
        return entityManager.update(entity);
    }

    public boolean remove(T entity)
    {
        return entityManager.remove(entity);
    }

    public ResultSet executeQuery(String query)
    {
        return entityManager.executeQuery(query);
    }

    protected abstract T transformer(String entityString);

}
