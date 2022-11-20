package service;

import annotations.Column;
import annotations.Table;
import entities.Spese;
import entitymanager.TestManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpeseService extends BasicService<Spese> {

    public SpeseService()
    {
        super(Spese.class);
        entityManager = new TestManager();
    }

    @Override
    protected Spese transformer(String entityString)
    {
        String[] entity = entityString.split(":");
        Spese spesa = new Spese();
        spesa.setId(Long.parseLong(entity[0]));
        spesa.setCategoria(entity[1]);
        spesa.setImporto(Float.parseFloat(entity[2]));
        spesa.setDescrizione(entity[3]);
        spesa.setDataSpesa(entity[4]);
        return spesa;
    }

    public void loadSpeseFromTsv(String tsv)
    {
        List<Spese> spese = new ArrayList<>();
        Spese s;
        try
        {
            FileReader fr = new FileReader(tsv);
            BufferedReader br = new BufferedReader(fr);

            String[] line;
            br.readLine();

            while(br.ready())
            {
                line = br.readLine().strip().split("\t");
                s = new Spese();
                s.setCategoria(line[0].toLowerCase());
                s.setImporto(Float.parseFloat(line[2].replace(",", ".")));
                s.setDescrizione(line[1]);
                s.setDataSpesa(line[3]);
                spese.add(s);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            for(int i = 0; i < spese.size(); i++)
            {
                spese.get(i).setId((long)spese.size() - i);
                save(spese.get(i));
            }
        }

    }

    public float getTotalIn(String month)
    {
        float totale = 0;
        month = month.toLowerCase();
        char firstLetter = month.charAt(0);
        month = month.replace(firstLetter, (char)(firstLetter - 32));
        String query = String.format("select sum(%s) from %s where %s = '%s';", "importo", Spese.class.getAnnotation(Table.class).tableName(), "data_spesa", month);
        ResultSet set = entityManager.executeQuery(query);
        try
        {
            set.next();
            totale = Float.parseFloat(set.getString(1));
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            return totale;
        }
    }

    public float getTotalByCategory(String categoria)
    {
        float totale = 0;
        categoria = categoria.toLowerCase();
        String query = String.format("select sum(%s) from %s where %s = '%s';", "importo", Spese.class.getAnnotation(Table.class).tableName(), "categoria", categoria);
        ResultSet set = entityManager.executeQuery(query);
        try
        {
            set.next();
            totale = Float.parseFloat(set.getString(1));
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            return totale;
        }
    }
}
