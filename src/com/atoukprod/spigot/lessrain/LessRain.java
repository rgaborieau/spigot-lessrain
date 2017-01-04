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
 * Created by rgaborieau on 28/12/2016.
 */
public class LessRain extends JavaPlugin implements Listener{

    /**
     * Where we save the settings. 
     */
    private Objective settings;

    @Override
    public void onDisable() {
        super.onDisable();
    }


    /**
     * At start, we initialize the settings if they does not exist and register to events
     */
    @Override
    public void onEnable() {
        super.onEnable();

        // Register to events in order to catch the Rain process
        getServer().getPluginManager().registerEvents(this, this);

        // Scoreboard in which we save the settings
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

        // Try to get the settings
        settings = board.getObjective("pluginlessrain");

        // If the settings does not exist, create them
        if(settings==null)
        {
            // Create the objective 
            settings = board.registerNewObjective("pluginlessrain","dummy");

            // Verbose indicate 0 if the plugin is quiet in game chat, 1 if not
            // Should be 0 or 1
            settings.getScore("verbose").setScore(0);

            // Numerator indicates, divided by Denominator, the risk the rain to abort
            // Must be positive and less than Denominator
            // Can be equal to 0 or Denominator
            settings.getScore("numerator").setScore(0);

            // Denominator indicates, dividing Numerator, the risk the rain to abort
            // Must be positive, not null and greater than or equal to Numerator
            settings.getScore("denominator").setScore(1);
        }

    }

    /**
     * Catch the rain event in order to abort this process according to a random value
     *
     * @param      event  The Rain event
     */
    @EventHandler
    public void onRain(WeatherChangeEvent event)
    {

        /**
         * @see onEnable
         */
        int verbose = settings.getScore("verbose").getScore();
        /**
         * @see onEnable
         */
        int numerator = settings.getScore("numerator").getScore();
        /**
         * @see onEnable
         */
        int denominator = settings.getScore("denominator").getScore();


        // Alert the players that the rain is coming
        if(verbose==1)Bukkit.broadcastMessage("Oh, it's raining today !");
        
        // Pick a random number between 0 and 1
        float r = (new Random()).nextFloat();

        // If this random number is under the risk ratio, abort the rain
        if(r<numerator/denominator)
        {
            // Tell the players that the rain is finally not coming
            if(verbose==1)Bukkit.broadcastMessage("Chance, this time the rain is gone !");
            event.setCancelled(true);
        }
        else
        {
            // Tell the players that the rain is ot aborted
            if(verbose==1)Bukkit.broadcastMessage("Oh shit, luck isn't with me this time");
        }

    }


}
