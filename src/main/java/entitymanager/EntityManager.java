package entitymanager;

import annotations.Table;
import entities.Entity;

import java.sql.*;
import java.util.Map;

public abstract class EntityManager {

    protected Connection connection = null;
    protected int connectionCount = 0;

    protected abstract Connection getConnection();

    private void closeConnection()
    {
        connectionCount--;
        if(connectionCount == 0)
        {
            try
            {
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                connection = null;
            }
        }
    }

    public ResultSet executeQuery(String query)
    {
        ResultSet result = null;
        Connection conn = getConnection();
        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection();
            return result;
        }
    }

    public boolean save(Entity entity)
    {
        Connection conn = getConnection();
        ResultSet result = null;

        Class<?> type  = entity.getClass();

        StringBuilder query = new StringBuilder();
        query.append("insert into ");
        if(!type.isAnnotationPresent(Table.class))
        {
            System.out.println("Specificare la tabella con l'annotazione @table");
            return false;
        }

        query.append(type.getDeclaredAnnotation(Table.class).tableName());

        Map<String, Object> columnToValuesMap = entity.getAllValues();

        StringBuilder columnBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        valuesBuilder.append(" values(");
        columnBuilder.append(" (");

        columnToValuesMap.forEach(
                (key, value) ->
                {
                    columnBuilder.append(key.replace(Entity.KEYMARKER, "") + ",");
                    valuesBuilder.append(String.format("%s,", value));
                }
        );

        columnBuilder.replace(columnBuilder.length()-1, columnBuilder.length(), "");
        valuesBuilder.replace(valuesBuilder.length()-1, valuesBuilder.length(), "");
        columnBuilder.append(")");
        valuesBuilder.append(");");

        query.append(columnBuilder);
        query.append(valuesBuilder);

        System.out.println(query);
        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection();
            return result != null;
        }
    }

    public boolean update(Entity entity)
    {
        Connection conn = getConnection();
        ResultSet result = null;

        Class<?> type  = entity.getClass();

        StringBuilder query = new StringBuilder();
        query.append("update ");
        if(!type.isAnnotationPresent(Table.class))
        {
            System.out.println("Specificare la tabella con l'annotazione @table");
            return false;
        }

        query.append(type.getDeclaredAnnotation(Table.class).tableName());
        query.append(" set ");

        Map<String, Object> columnToValuesMap = entity.getAllValues();

        StringBuilder where = new StringBuilder();
        where.append("where ");

        columnToValuesMap.forEach(
                (key, value) ->
                {
                    if(!key.startsWith(Entity.KEYMARKER))
                    {
                        query.append(key);
                        query.append(String.format("=%s,", value));
                    }
                    else
                    {
                        where.append(key.replace(Entity.KEYMARKER, ""));
                        where.append(String.format("=%s,", value));
                    }
                }
        );

        where.replace(where.length()-1, where.length(), "");
        where.append(";");
        query.replace(query.length()-1, query.length(), "");
        query.append(where);
        System.out.println(query);
        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
        }
        catch (Exception e)
        {
            e.printStackTrace();;
        }
        finally
        {
            closeConnection();
            return result != null;
        }
    }

    public boolean remove(Entity entity)
    {
        Connection conn = getConnection();

        StringBuilder query = new StringBuilder();
        query.append("delete from ");
        query.append(entity.getClass().getAnnotation(Table.class).tableName());
        query.append(" where ");

        Map<String,Object> map = entity.getKeys();

        map.forEach((key, value) -> query.append(String.format("%s=%s,",key,value)));
        query.replace(query.length()-1, query.length(), "");
        query.append(";");

        System.out.println(query);

        int deleted = 0;
        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            deleted = preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection();
            return deleted > 0;
        }
    }
}
