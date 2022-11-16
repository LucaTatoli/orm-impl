import controller.PortafoglioController;
import controller.UserController;
import entities.Portafoglio;
import entities.User;

import java.util.List;

public class Main
{
    public static void main(String... args)
    {
        UserController userController = new UserController();
        User user;
        user = userController.getAllUserWhereAttributeEquals("cognome", "Masiero").get(0);

        PortafoglioController walletController = new PortafoglioController();
        Portafoglio portafoglio = new Portafoglio();
        portafoglio.setUserId(user.getId());
        portafoglio.setAmount(50000);
        walletController.save(portafoglio);
        List<Portafoglio> wallet = walletController.getAllWallets();
        wallet.forEach(System.out::println);
    }
}
