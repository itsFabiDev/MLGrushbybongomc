package de.Fabi.mlgrush.listener;

import de.Fabi.mlgrush.gamestates.GameStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (!GameStateHandler.isAllowMove()) {
            player.teleport(player.getLocation());
        }

    }

}
