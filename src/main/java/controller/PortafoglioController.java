package controller;

import entities.Portafoglio;
import service.PortafoglioService;

import java.sql.ResultSet;
import java.util.List;

public class PortafoglioController
{
    private PortafoglioService service;

    public PortafoglioController()
    {
        service = new PortafoglioService();
    }

    public boolean save(Portafoglio portafoglio)
    {
        return service.save(portafoglio);
    }

    public boolean update(Portafoglio portafoglio)
    {
        return service.update(portafoglio);
    }

    public boolean remove(Portafoglio portafoglio)
    {
        return service.remove(portafoglio);
    }

    public List<Portafoglio> getAllWallets()
    {
        return service.getAllWallets();
    }

    public ResultSet executeQuery(String query)
    {
        return service.executeQuery(query);
    }

}
