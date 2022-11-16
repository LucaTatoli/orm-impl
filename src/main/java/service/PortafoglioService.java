package service;

import annotations.Table;
import entities.Portafoglio;
import entities.User;
import entitymanager.TestManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PortafoglioService {

    private TestManager entityManager;

    public PortafoglioService()
    {
        entityManager = new TestManager();
    }

    public ArrayList<Portafoglio> getAllWallets()
    {
        ArrayList<Portafoglio> wallets = new ArrayList<>();

        ResultSet walletsSet = entityManager.executeQuery(String.format("select * from %s;", Portafoglio.class.getAnnotation(Table.class).tableName()));

        try
        {
            while (walletsSet.next())
            {
                String walletString = String.format("%s:%s:%s", walletsSet.getString(1), walletsSet.getString(2), walletsSet.getString(3));
                wallets.add(walletTransformer(walletString));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return wallets;
    }

    public boolean save(Portafoglio portafoglio)
    {
        return entityManager.save(portafoglio);
    }

    public boolean update(Portafoglio portafoglio)
    {
        return entityManager.update(portafoglio);
    }

    public boolean remove(Portafoglio portafoglio)
    {
        return entityManager.remove(portafoglio);
    }

    public ResultSet executeQuery(String query)
    {
        return entityManager.executeQuery(query);
    }

    private Portafoglio walletTransformer(String input)
    {
        Portafoglio wallet = new Portafoglio();

        String[] values = input.split(":");
        wallet.setWalletId(Long.parseLong(values[0].strip()));
        wallet.setUserId(Long.parseLong(values[1].strip()));
        wallet.setAmount(Float.parseFloat(values[2].strip()));

        return wallet;
    }

}
