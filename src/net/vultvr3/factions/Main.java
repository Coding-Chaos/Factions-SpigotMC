package net.vultvr3.factions;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	
    @Override
    public void onEnable() {
    	instance = this;
    	this.getCommand("f").setExecutor(new FactionCommand());
    }	
    
    public static Main getInstance() {
        return instance;
    }
}
