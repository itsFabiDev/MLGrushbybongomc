package de.Fabi.mlgrush.gamestates;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.enums.GameState;
import de.Fabi.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LobbyCountdown {

    private static int taskID;
    private static int countDown;

    /**
     * Starts the lobby countdown.
     * @param resetCountdown Should the countDown be reset to
     * @param delayed Should the countdown be started with a delay of 20 ticks (= 1 second)?
     */
    public void start(boolean resetCountdown, boolean delayed) {

        long startDelay = 0L;
        long period = 20L;
        for (Player current : Bukkit.getOnlinePlayers()) {
            if(!MLGRush.getInstance().getTeamHandler().playing.contains(current))
                MLGRush.getInstance().getTeamHandler().playing.add(current);
        }

        if(resetCountdown)
            countDown = 11;
        if(delayed)
            startDelay = 20L;

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(MLGRush.getInstance(), () -> {
            countDown--;

            Bukkit.getOnlinePlayers().forEach(current -> {
                final PlayerManager currentManager = new PlayerManager(current);
                currentManager.sendActionBar(MLGRush.getInstance().getPrefix() + "§7Das Spiel startet in §a" + countDown + " §7Sekunden.");
            });

            switch (countDown) {
                case 10 : case 5 : case 3 : case 2 :
                    Bukkit.broadcastMessage(MLGRush.getInstance().getPrefix() + "§7Das Spiel startet in §a" + countDown + " §7Sekunden");
                    Bukkit.getOnlinePlayers().forEach(current -> {
                        final PlayerManager currentManager = new PlayerManager(current);
                        currentManager.sendTitle("§3§l" + countDown, "§7Sekunden bist zum Spielstart");
                        current.playSound(current.getLocation(), Sound.ORB_PICKUP, 3, 1);
                    });
                    break;
                case 1 :
                    Bukkit.broadcastMessage(MLGRush.getInstance().getPrefix() + "§7Das Spiel startet in §aeiner §7Sekunde");
                    Bukkit.getOnlinePlayers().forEach(current -> {
                        final PlayerManager currentManager = new PlayerManager(current);
                        currentManager.sendTitle("§3§l" + countDown, "§7Sekunde bis zum Spielstart");
                        current.playSound(current.getLocation(), Sound.ORB_PICKUP, 3, 1);
                    });
                    break;
                case 0:
                    MLGRush.getInstance().getTeamHandler().handleTeams();
                    GameStateHandler.setGameState(GameState.INGAME);
                    Bukkit.broadcastMessage(MLGRush.getInstance().getPrefix() + "§7Das Spiel startet §ajetzt.");

                    MLGRush.getInstance().getTeamHandler().fixTeams();
                    for (Player current : MLGRush.getInstance().getTeamHandler().playing) {
                        final PlayerManager playerManager = new PlayerManager(current);
                        playerManager.resetPlayer();
                        playerManager.teleportToTeamIsland();
                        MLGRush.getInstance().getInventoryManager().givePlayItems(current);
                        MLGRush.getInstance().getScoreboardHandler().setScoreboard(current);
                        MLGRush.getInstance().getTabListHandler().setTabList(current);
                    }

                    GameStateHandler.setAllowMove(false);
                    MLGRush.getInstance().getPlayerMoveScheduler().startListening();
                    MLGRush.getInstance().getStartCountDown().start();
                    MLGRush.getInstance().getPointsHandler().sendPointsActionBar();
                    this.stop();
                    break;
            }

        }, startDelay, period);
    }

    /**
     * Stops the lobby countdown
     */
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }



}
