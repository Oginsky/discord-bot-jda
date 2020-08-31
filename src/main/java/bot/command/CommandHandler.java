package bot.command;

import bot.util.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CommandHandler extends ListenerAdapter {

    private static boolean hasChecked = false;
    private static boolean valid = false;
    private static boolean isDenied = false;

    private static final String deniedURLPicture = "https://i.imgur.com/7tmwIWj.jpg";

    // stupid joke
    private static void isDenied() {
        if(Config.isHasDenied() && new Random().nextInt(100) < Config.getDeniedCoeff()) {
            isDenied = true;
            hasChecked = true;
            valid = false;
        }
    }

    public static boolean isValid(GuildMessageReceivedEvent event) {
        isDenied();
        if(hasChecked) return valid;
        hasChecked = true;
        if(event.getMember().getUser().isBot() && Config.isBotAnswer()) {
            valid = false;
        }
        else if(!event.getMessage().getContentRaw().startsWith(Config.prefix)) {
            valid = false;
        }
        else valid = true;
        return valid;
    }

    public static void reset() {
        hasChecked = valid = isDenied = false;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(isDenied && !event.getMember().getUser().isBot()) {
            event.getChannel().sendMessage("nope\n").queue();
            event.getChannel().sendMessage(deniedURLPicture).queue();
        }
        reset();
    }
}
