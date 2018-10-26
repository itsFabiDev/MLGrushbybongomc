package de.Fabi.mlgrush.listener;

import de.Fabi.mlgrush.enums.GameState;
import de.Fabi.mlgrush.gamestates.GameStateHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {
    @EventHandler
    public void onServerlistPing(final ServerListPingEvent event) {
        for (GameState gameState : GameState.values()) {
            if (gameState == GameStateHandler.getGameState()) {
                event.setMotd(GameStateHandler.getGameState().getMotd());
                break;
            }
        }
    }

}
