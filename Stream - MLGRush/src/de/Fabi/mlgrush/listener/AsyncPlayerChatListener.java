package de.Fabi.mlgrush.listener;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.GameState;
import de.Fabi.mlgrush.gamestates.GameStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        final String message = event.getMessage().replace("%", "%%");

        if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            if(MLGRush.getInstance().getTeamHandler().hasTeam(player)) {
                event.setFormat("§a§lTEAM #" + MLGRush.getInstance().getTeamHandler().getPlayerTeam(player).toString().replace("TEAM_", "") + " §8● §7" + player.getName() + " §8➜ §7" + message);
            } else {
                event.setFormat("§7" + player.getName() + " §8➜ §7" + message);
            }
        } else {
            if (MLGRush.getInstance().getTeamHandler().hasTeam(player)) {
                event.setFormat("§a§lTEAM #" + MLGRush.getInstance().getTeamHandler().getPlayerTeam(player).toString().replace("TEAM_", "") + " §8● §7" + player.getName() + " §8➜ §7" + message);
            } else if (MLGRush.getInstance().getTeamHandler().isSpectator(player)) {
                event.setCancelled(true);
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cAls Zuschauer kannst du nicht schreiben.");
            }
        }
    }

}
