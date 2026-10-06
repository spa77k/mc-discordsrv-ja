package io.github.spa77k.discordsrvja;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AchievementMessagePreProcessEvent;
import github.scarsz.discordsrv.api.events.DeathMessagePreProcessEvent;
import io.papermc.paper.advancement.AdvancementDisplay;
import java.util.Map;
import java.util.logging.Level;
import net.kyori.adventure.text.Component;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordSRVJaPlugin extends JavaPlugin {
    private volatile ComponentTranslator translator;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String language = getConfig().getString("language", "ja_jp");
        String version = getServer().getMinecraftVersion();
        LanguageFiles files = new LanguageFiles(getDataFolder().toPath().resolve("lang"));
        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            try {
                Map<String, String> translations = files.load(version, language);
                translator = new ComponentTranslator(translations);
                getLogger().info("Loaded " + language + " for Minecraft " + version
                        + " (" + translations.size() + " entries)");
            } catch (Exception e) {
                getLogger().log(Level.WARNING, "Could not load " + language + " for Minecraft " + version
                        + "; messages are sent untranslated", e);
            }
        });
        DiscordSRV.api.subscribe(this);
    }

    @Override
    public void onDisable() {
        DiscordSRV.api.unsubscribe(this);
    }

    @Subscribe
    public void onDeath(DeathMessagePreProcessEvent event) {
        ComponentTranslator current = translator;
        if (current == null || !getConfig().getBoolean("death-messages", true)
                || !(event.getTriggeringBukkitEvent() instanceof PlayerDeathEvent death)) {
            return;
        }
        Component message = death.deathMessage();
        if (message != null) {
            event.setDeathMessage(current.translate(message));
        }
    }

    @Subscribe
    public void onAdvancement(AchievementMessagePreProcessEvent event) {
        ComponentTranslator current = translator;
        if (current == null || !getConfig().getBoolean("advancements", true)
                || !(event.getTriggeringBukkitEvent() instanceof PlayerAdvancementDoneEvent done)) {
            return;
        }
        AdvancementDisplay display = done.getAdvancement().getDisplay();
        if (display != null) {
            event.setAchievementName(current.translate(display.title()));
        }
    }
}
