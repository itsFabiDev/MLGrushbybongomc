package de.Fabi.mlgrush.gamestates;

import de.pxav.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.GameState;
import org.bukkit.Bukkit;

public class IdleCountdown {

    private static int taskID;

    public void start() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MLGRush.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(current -> {
            if(GameStateHandler.getGameState() == GameState.LOBBY) {
                Bukkit.broadcastMessage(MLGRush.getInstance().getPrefix() + "§cWarte auf §7einen §cweiteren Spieler...");
            }
        }), 0, 500);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
