package bot.command;

import bot.util.Config;
import bot.util.GuildConfig;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CommandHandler extends ListenerAdapter {

    private static boolean hasChecked = false;
    private static boolean valid = false;
    private static boolean isDenied = false;
    public static boolean ignoreDenied = false;

    private static final String deniedURLPicture = "https://i.imgur.com/7tmwIWj.jpg";

    // stupid joke
    private static void isDenied(long guildID) {
        GuildConfig guildConfig = ServiceCommand.getGuildConfig(guildID);
        if(guildConfig.hasDenied && new Random().nextInt(100) < guildConfig.deniedCoeff) {
            isDenied = true;
            hasChecked = true;
            valid = false;
        }
    }

    public static boolean isValid(GuildMessageReceivedEvent event) {
        if(hasChecked) return valid;
        if(!event.getMessage().getContentRaw().startsWith(Config.prefix)) {
            hasChecked = true;
            valid = false;
            return valid;
        }
        isDenied(event.getGuild().getIdLong());
        if(isDenied) return false;
        hasChecked = true;
        valid = !event.getMember().getUser().isBot();
        return valid;
    }

    public static boolean hasPermission(Member member) {
        return member.hasPermission(Permission.ADMINISTRATOR);
    }

    public static void reset() {
        hasChecked = valid = isDenied = ignoreDenied = false;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(!ignoreDenied && isDenied && !event.getMember().getUser().isBot()) {
            String[] not = new String[]{"nope", "Ты спрашиваешь меня, но делаешь это без уважения", "Try to ask more politely"};

            event.getChannel().sendMessage(not[new Random().nextInt(not.length)] + "\n").queue();
            event.getChannel().sendMessage(deniedURLPicture).queue();
        }
        reset();
    }
}
