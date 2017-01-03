package com.atoukprod.spigot.lessrain;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Random;

/**
 * Created by Romain on 28/12/2016.
 */
public class LessRain extends JavaPlugin implements Listener{

    private Scoreboard board;
    private Objective settings;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(this, this);

        board = Bukkit.getScoreboardManager().getMainScoreboard();

        settings = board.getObjective("pluginlessrain");

        if(settings==null)
        {
            settings = board.registerNewObjective("pluginlessrain","dummy");
            settings.getScore("verbose").setScore(0);
            settings.getScore("numerator").setScore(0);
            settings.getScore("denominator").setScore(1);
        }

    }

    @EventHandler
    public void onRain(WeatherChangeEvent event)
    {

        int verbose = settings.getScore("verbose").getScore();
        int numerator = settings.getScore("numerator").getScore();
        int denominator = settings.getScore("denominator").getScore();



        if(verbose==1)Bukkit.broadcastMessage("Oh, it's raining today !");
        
        float r = (new Random()).nextFloat();
        if(r<numerator/denominator)
        {
            if(verbose==1)Bukkit.broadcastMessage("Chance, this time the rain is gone !");
            event.setCancelled(true);
        }
        else
        {
            if(verbose==1)Bukkit.broadcastMessage("Oh shit, luck isn't with me this time");
        }

    }


}
