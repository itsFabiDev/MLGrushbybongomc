package de.Fabi.mlgrush.listener;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.GameState;
import de.Fabi.mlgrush.enums.LocationType;
import de.Fabi.mlgrush.enums.TeamType;
import de.Fabi.mlgrush.gamestates.GameStateHandler;
import de.Fabi.mlgrush.utils.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            final Player damager = (Player) event.getDamager();
            final Player player = (Player) event.getEntity();
            final PlayerManager damagerManager = new PlayerManager(damager);
            final PlayerManager playerManager = new PlayerManager(player);

            if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
                event.setCancelled(true);
            } else if (GameStateHandler.getGameState() == GameState.INGAME) {
                if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(damager) == TeamType.TEAM_1) {
                    if(player.getLocation().getY() >= MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_2).getY()) {
                        event.setCancelled(true);
                        damager.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu kannst §7" + player.getName() + " §cnicht am Spawn schlagen.");
                    }
                } else if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(damager) == TeamType.TEAM_2) {
                    if(player.getLocation().getY() >= MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_1).getY()) {
                        event.setCancelled(true);
                        damager.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu kannst §7" + player.getName() + " §cnicht am Spawn schlagen.");
                    }
                }
            }

        } else
            event.setCancelled(true);
    }

}
