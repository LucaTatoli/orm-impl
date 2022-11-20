package service;

import annotations.Table;
import entities.User;
import entitymanager.TestManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersService extends BasicService<User>
{

    public UsersService()
    {
        super(User.class);
        entityManager = new TestManager();
    }

    public List<User> getAllUserWhereAttributeEquals(String attribute, String value)
    {
        ArrayList<User> users = new ArrayList<User>();

        ResultSet usersSet = entityManager.executeQuery(String.format("select * from %s where %s = '%s';", type.getAnnotation(Table.class).tableName(), attribute, value));

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s:%s:%s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                users.add(transformer(userString));
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
        query.append(String.format("select * from %s where ", type.getAnnotation(Table.class).tableName()));

        for(int i = 0; i < values.length; i++)
        {
            if(i > 0)
                query.append(" or ");
            query.append(String.format("%s = '%s'", attribute, values[i]));
        }
        query.append(";");
        System.out.println(query);
        ResultSet usersSet = entityManager.executeQuery(query.toString());

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s : %s : %s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                users.add(transformer(userString));
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
        ResultSet usersSet = entityManager.executeQuery(query);

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s : %s : %s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                users.add(transformer(userString));
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
        query.append(String.format("select * from %s where ", type.getAnnotation(Table.class).tableName()));
        query.append(String.format("id = %d", id));

        ResultSet usersSet = entityManager.executeQuery(query.toString());

        try {
            while (usersSet.next())
            {
                String userString = String.format("%s:%s:%s", usersSet.getString(1), usersSet.getString(2), usersSet.getString(3));
                user = transformer(userString);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return user;
    }

    protected User transformer(String input)
    {
        User user = new User();

        String[] values = input.split(":");
        user.setId(Long.parseLong(values[2].strip()));
        user.setNome(values[0]);
        user.setCognome(values[1]);

        return user;
    }

}
