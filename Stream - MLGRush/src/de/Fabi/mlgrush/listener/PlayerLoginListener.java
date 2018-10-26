package de.Fabi.mlgrush.listener;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.GameState;
import de.Fabi.mlgrush.gamestates.GameStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        if (GameStateHandler.getGameState() == GameState.ENDING)
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, MLGRush.getInstance().getPrefix() + "§cDas Spiel endet bereits...");
        else if(GameStateHandler.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() >= 2)
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, MLGRush.getInstance().getPrefix() + "§cDas Spiel ist bereits voll.");
    }

}
