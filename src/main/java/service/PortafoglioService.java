package service;

import entities.Portafoglio;
import entitymanager.TestManager;

public class PortafoglioService extends BasicService<Portafoglio>
{

    public PortafoglioService()
    {
        super(Portafoglio.class);
        entityManager = new TestManager();
    }

    @Override
    protected Portafoglio transformer(String input)
    {
        Portafoglio wallet = new Portafoglio();

        String[] values = input.split(":");
        wallet.setWalletId(Long.parseLong(values[0].strip()));
        wallet.setUserId(Long.parseLong(values[1].strip()));
        wallet.setAmount(Float.parseFloat(values[2].strip()));

        return wallet;
    }

}
