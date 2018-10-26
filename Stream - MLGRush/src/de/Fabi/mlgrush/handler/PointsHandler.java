package de.Fabi.mlgrush.handler;

import de.Fabi.mlgrush.MLGRush;
import de.Fabi.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PointsHandler {

    private int taskID;

    public String getGlobalPoints() {
        int index = 0;
        StringBuilder stringBuilder = new StringBuilder("§a");
        for (Player current : MLGRush.getInstance().getTeamHandler().playing) {
            final PlayerManager playerManager = new PlayerManager(current);
            stringBuilder.append(playerManager.getRoundPoints());
            if(index == 0)
                stringBuilder.append(" §8| §a");
            index++;
        }
        return stringBuilder.toString();
    }

    public final int getMaxPoints() {
        return MLGRush.getInstance().getConfigManager().getMaxPoints();
    }

    public void sendPointsActionBar() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MLGRush.getInstance(), () -> {

            MLGRush.getInstance().getTeamHandler().playing.forEach(current -> {

                final PlayerManager playerManager = new PlayerManager(current);
                playerManager.sendActionBar(MLGRush.getInstance().getPrefix() + "§7Du musst noch §a" + (getMaxPoints() - playerManager.getRoundPoints()) + "x §7das Bett deines Gegners abbauen.");

            });

        }, 0, 5);
    }

    public void stopPointsActionBar() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
