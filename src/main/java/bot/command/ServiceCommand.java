package bot.command;

import bot.util.GuildConfig;

import java.util.HashMap;

public class ServiceCommand {
    private static HashMap<Long, GuildConfig> guildManagers = new HashMap<>();

    public static GuildConfig getGuildConfig(long guildLongID) {
        if(guildManagers.containsKey(guildLongID)) return guildManagers.get(guildLongID);
        GuildConfig guildConfig = new GuildConfig();
        guildManagers.put(guildLongID, guildConfig);
        return guildConfig;
    }
}
