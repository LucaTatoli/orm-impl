package controller;

import entities.Spese;
import service.SpeseService;

import java.util.List;

public class SpeseController
{

    private SpeseService service;

    public SpeseController()
    {
        service = new SpeseService();
    }

    public boolean save(Spese spesa)
    {
        return service.save(spesa);
    }

    public boolean update(Spese spesa)
    {
        return service.update(spesa);
    }

    public boolean remove(Spese spesa)
    {
        return service.remove(spesa);
    }

    public List<Spese> getAllSpese()
    {
        return service.selectAll();
    }

    public void loadSpeseFromTSV(String tsv)
    {
        service.loadSpeseFromTsv(tsv);
    }

    public float getTotalIn(String month)
    {
        return service.getTotalIn(month);
    }

    public float getTotalByCategory(String category)
    {
        return service.getTotalByCategory(category);
    }
}
