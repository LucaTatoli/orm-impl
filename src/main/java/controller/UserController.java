package controller;

import entities.User;
import service.UsersService;

import java.sql.ResultSet;
import java.util.List;

public class UserController {

    private UsersService service;

    public UserController()
    {
        service = new UsersService();
    }

    public List<User> getAllUsers()
    {
        return service.selectAll();
    }

    public List<User> getAllUserWhereAttributeEquals(String attribute, String value)
    {
        return service.getAllUserWhereAttributeEquals(attribute, value);
    }

    public List<User> getAllUserWhereAttributeIn(String attribute, String... values)
    {
        return service.getAllUserWhereAttributeIn(attribute, values);
    }

    public List<User> getUsersWithCustomQuery(String query)
    {
        return service.getUsersWithCustomQuery(query);
    }

    public User getUserWhereIdEquals(long id)
    {
        return service.getUserWhereIdEquals(id);
    }

    public boolean saveUser(User user)
    {
        return service.save(user);
    }
    public boolean updateUser(User user) { return service.update(user); }

    public boolean removeUser(User user)
    {
        return service.remove(user);
    }

    public static void main(String... args)
    {
        UserController controller = new UserController();
//        System.out.println("\nInsert new user");
//        user = new User();
//        user.setNome("Peter");
//        user.setCognome("Parker");
//        boolean result = controller.saveUser(user);
//        System.out.println(result);
        System.out.println("\nUpdate user where nome = peter");
        List<User> users = controller.getAllUserWhereAttributeEquals("nome", "Peter");
        users.forEach(
                u ->
                {
                    boolean res = controller.removeUser(u);
                    System.out.println(res);
                }
        );

        users = controller.getAllUsers();
        System.out.println("All users");
        users.forEach(System.out::println);
    }

}
