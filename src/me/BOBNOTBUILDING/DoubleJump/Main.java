package me.BOBNOTBUILDING.DoubleJump;

import java.util.logging.Logger;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Minecraft");

	@Override
	public void onDisable(){

		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName()  + "] "+ " Has been DISABLED!");

	}

	@Override
	public void onEnable() {

		getConfig().options().copyDefaults(true);
		saveConfig();

		getServer().getPluginManager().registerEvents(this,this);
		PluginDescriptionFile pdfFile = this.getDescription();

		this.logger.info("[" + pdfFile.getName()  + "] "+ " Has been ENABLED!");

	}

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE)
			return;
		event.setCancelled(true);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setVelocity(player.getLocation().getDirection().multiply(this.getConfig().getInt("Multiplier")).setY(this.getConfig().getInt("SetY")));
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if ((player.getGameMode() != GameMode.CREATIVE)
				&& (player.getLocation().subtract(0, this.getConfig().getInt("Subtract"), 0).getBlock().getType() != Material.AIR)
				&& (!player.isFlying()))
			player.setAllowFlight(true);
	}

}
