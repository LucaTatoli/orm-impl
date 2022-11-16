package service;

import annotations.Table;
import entities.User;
import entitymanager.TestManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersService
{

    private TestManager connectionManager;
    public static final String table = "test_schema.users";

    public UsersService()
    {
        connectionManager = new TestManager();
    }

    public List<User> getAllUsers()
    {
        ArrayList<User> users = new ArrayList<User>();

        ResultSet usersSet = connectionManager.executeQuery(String.format("select * from %s;", User.class.getAnnotation(Table.class).tableName()));

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s:%s:%s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                users.add(userTransformer(userString));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getAllUserWhereAttributeEquals(String attribute, String value)
    {
        ArrayList<User> users = new ArrayList<User>();

        ResultSet usersSet = connectionManager.executeQuery(String.format("select * from %s where %s = '%s';", User.class.getAnnotation(Table.class).tableName(), attribute, value));

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s:%s:%s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                users.add(userTransformer(userString));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getAllUserWhereAttributeIn(String attribute, String... values)
    {
        ArrayList<User> users = new ArrayList<User>();

        StringBuilder query = new StringBuilder();
        query.append(String.format("select * from %s where ", User.class.getAnnotation(Table.class).tableName()));

        for(int i = 0; i < values.length; i++)
        {
            if(i > 0)
                query.append(" or ");
            query.append(String.format("%s = '%s'", attribute, values[i]));
        }
        query.append(";");
        System.out.println(query);
        ResultSet usersSet = connectionManager.executeQuery(query.toString());

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s : %s : %s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                users.add(userTransformer(userString));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getUsersWithCustomQuery(String query)
    {
        ArrayList<User> users = new ArrayList<User>();
        ResultSet usersSet = connectionManager.executeQuery(query);

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s : %s : %s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                users.add(userTransformer(userString));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserWhereIdEquals(long id)
    {
        User user = new User();

        StringBuilder query = new StringBuilder();
        query.append(String.format("select * from %s where ", table));
        query.append(String.format("id = %d", id));

        ResultSet usersSet = connectionManager.executeQuery(query.toString());

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s:%s:%s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                user = userTransformer(userString);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return user;
    }

    private User userTransformer(String input)
    {
        User user = new User();

        String[] values = input.split(":");
        user.setId(Long.parseLong(values[2].strip()));
        user.setNome(values[0]);
        user.setCognome(values[1]);

        return user;
    }

    public boolean saveUser(User user)
    {
        return connectionManager.save(user);
    }

    public boolean updateUser(User user)
    {
        return connectionManager.update(user);
    }

    public boolean removeUser(User user)
    {
        return connectionManager.remove(user);
    }

}
