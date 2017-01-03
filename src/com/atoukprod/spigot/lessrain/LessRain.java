package com.atoukprod.spigot.lessrain;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * Created by Romain on 28/12/2016.
 */
public class LessRain extends JavaPlugin implements Listener{
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onRain(WeatherChangeEvent event)
    {
        Bukkit.broadcastMessage("Oh, it's raining today !");
        int r = (new Random()).nextInt(2);
        if(r==0)
        {
            Bukkit.broadcastMessage("Chance, this time the rain is gone !");
            event.setCancelled(true);
        }
        else
        {
            Bukkit.broadcastMessage("Oh shit, luck isn't with me this time");
        }

    }


}
