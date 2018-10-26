package de.Fabi.mlgrush.listener;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.GameState;
import de.Fabi.mlgrush.enums.LocationType;
import de.Fabi.mlgrush.gamestates.GameStateHandler;
import de.Fabi.mlgrush.utils.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final PlayerManager playerManager = new PlayerManager(player);

        if (GameStateHandler.getGameState() == GameState.INGAME) {

            if(event.getBlock().getLocation().getBlockY() >= MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_1).getBlockY()
                    || event.getBlock().getLocation().getBlockY() >= MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_2).getBlockY()) {
                event.setCancelled(true);
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu darfst über dem Spawn nicht bauen.");
            }

            if (MLGRush.getInstance().getRegionManager().isInRegion(event.getBlock().getLocation()))
                MLGRush.getInstance().getMapResetHandler().addPlacedBlock(event.getBlock().getLocation());
            else {
                event.setCancelled(true);
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu darfst außerhalb des Spielbereiches nicht bauen!");
            }
        } else if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            if(playerManager.getCurrentSetupBed() == null) {
                event.setCancelled(!playerManager.isInBuildMode());
            }
        }
    }

}
