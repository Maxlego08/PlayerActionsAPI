package fr.maxlego08.playeractions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin {

    @Override
    public void onEnable() {
        ActionsAPI.loadActions("[console] give %player% diamond", "[message] <delay:10> &bAction &f&l>> &fExample message");
        ActionsAPI.registerAction(this, new ActionType() {
            @Override
            public String getIdentifier() {
                return "[custom]";
            }

            @Override
            public Class<? extends Action> getDefaultAction() {
                return CustomAction.class;
            }
        });
        ActionsAPI.loadActions("[custom] <delay:20>");
        this.getLogger().info("Actions loaded!");
    }

    public static class CustomAction extends Action {
        public CustomAction(int delay) {
            super(delay);
        }

        @Override
        protected Runnable getRunnableAction(Plugin plugin, Player player) {
            return () -> player.sendMessage("Hello, " + player.getName() + "!");
        }
    }
}
