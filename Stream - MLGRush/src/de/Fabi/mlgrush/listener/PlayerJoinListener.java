package de.Fabi.mlgrush.listener;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.GameState;
import de.Fabi.mlgrush.enums.LocationType;
import de.Fabi.mlgrush.enums.TeamType;
import de.Fabi.mlgrush.gamestates.GameStateHandler;
import de.Fabi.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {

        final PlayerManager playerManager = new PlayerManager(event.getPlayer());
        final Player player = playerManager.getPlayer();
        final MLGRush instance = MLGRush.getInstance();

        if (GameStateHandler.getGameState() == GameState.LOBBY) {

            if (!MLGRush.getInstance().getTeamHandler().playing.contains(player))
                MLGRush.getInstance().getTeamHandler().playing.add(player);
            event.setJoinMessage(MLGRush.getInstance().getPrefix() + "§a" + player.getName() + " §7hat das Spiel betreten.");
            player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
            instance.getScoreboardHandler().setScoreboard(player);
            playerManager.resetPlayer();
            instance.getInventoryManager().givePlayerLobbyItems(player);
            instance.getTeamHandler().joinTeam(player, TeamType.NONE);
            instance.getTabListHandler().setTabList(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.setFoodLevel(20);
            player.setHealth(20.0D);
            player.setHealthScale(20.0D);

            if (Bukkit.getOnlinePlayers().size() == 1) {
                instance.getIdleCountdown().start();
            } else if (Bukkit.getOnlinePlayers().size() == 2) {
                instance.getIdleCountdown().stop();
                instance.getLobbyCountdown().start(true, false);
            } else {
                player.kickPlayer(MLGRush.getInstance().getPrefix() + "§cDas Spiel ist voll!");
            }

            Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), ()
                    -> player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY)), 4);

        } else if (GameStateHandler.getGameState() == GameState.INGAME) {

            event.setJoinMessage(instance.getPrefix() + "§a" + player.getName() + " §7hat das Spiel als §aZuschauer §7betreten.");
            player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPECTATOR));
            playerManager.resetPlayer();
            instance.getTabListHandler().setTabList(player);
            player.setGameMode(GameMode.SPECTATOR);

            Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), ()
                    -> player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPECTATOR)), 4);

        }

    }

}
