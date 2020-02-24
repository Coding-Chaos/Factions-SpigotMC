package net.vultvr3.factions;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class FactionCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length > 0) {
			if(args[0].equals("create")){
				if(args.length > 1) {
					if(!(alreadyHasFaction(args[1], player))) {
						player.sendMessage("§7[!]§8:§c You are already in a faction!");
						return false;
					}if(!(args[1].length() < 20)) {
						player.sendMessage("§7[!]§8: §cThe name you specified is too long! (TWENTY CHARACTERS MAXIMUM)");
						return false;
					}if(createFaction(args[1], player)) {
						player.sendMessage("§7[!]§8: §aSuccessfully created the faction " + args[1] + "!");
						return false;
					}else{
						player.sendMessage("§7[!]§8:§c Oops! The faction name " + args[1] + " already belongs to someone else!");
						return false;
					}
				}else {
					player.sendMessage("§7[!]§8:§cOops! We need you to give us a faction name!");
					return false;
				}
			}if(args[0].equals("leave")){
				if(args.length > 1) {
					if(leave(args[1], player)) {
						player.sendMessage("§7[!]§8: §aSuccessfully abandoned your faction!");
					}else {
						player.sendMessage("§7[!]§8: §cUhh... you aren't in that faction!");
					}
				}else {
					player.sendMessage("§7[!]§8:§c Oops! We need you to give the name of the faction you want to leave!");
					return false;
				}
			}
		}
		else{
			player.sendMessage("§7[!]§8: §r§cFACTIONS MAIN MENU");
			new JsonMessage().append("§7[!]§8: §r§4/f create <name>§8 -> §fCreates a Faction!").setHoverAsTooltip("§7Please, keep all names appropriate. :)").setClickAsSuggestCmd("/f create ").save().send(player);
		}
		return false;
	}

	private boolean createFaction(String string, Player player) {
		String lower = string.toLowerCase();
		FileConfiguration config = Main.getInstance().getConfig();
		if(config.contains("factions." + lower)){
			return false;
		}else {

			//Creates tab with the faction's ID.
			config.createSection("factions." + lower);

			//Creates tab with the IGN of its owner!
			config.createSection("factions." + lower + ".owner");
			config.set("factions." + lower + ".owner", player.getName());

			//Creates tab with the faction's name (including caps)!
			config.createSection("factions." + lower + ".name");
			config.set("factions." + lower + ".name", string);

			//creates tab with the faction's members!
			config.createSection("factions." + lower + ".members");
			Main.getInstance().saveConfig();
			return true;
    		}
	}
	
	private boolean alreadyHasFaction(String string, Player player) {
		FileConfiguration config = Main.getInstance().getConfig();
		String lower = string.toLowerCase();
	   	if((config.contains("factions." + lower + ".owner." + player.getName())) || (config.contains("factions." + lower + ".members." + player.getName()))){
	   		return true;
	   	}else {
	   		return false;
	   	}
	}
	
	private boolean leave(String string, Player player) {
		FileConfiguration config = Main.getInstance().getConfig();
		String lower = string.toLowerCase();
	   	if((config.contains("factions." + lower + ".owner." + player.getName()))){
	   		config.set("factions." + lower + ".members." + player.getName(), null);
	   		Main.getInstance().saveConfig();
	   		return true;
	   	}else if((config.contains("factions." + lower + ".members." + player.getName()))){
	   		config.set("factions." + lower + ".members." + player.getName(), null);
	   		Main.getInstance().saveConfig();
	   		return true;
	   	}else {
	   		return false;
	   	}
	}
}
