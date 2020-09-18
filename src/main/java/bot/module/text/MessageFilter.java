package bot.module.text;

import bot.command.ServiceCommand;
import bot.message.Messages;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.HashSet;

public class MessageFilter extends ListenerAdapter {

    private static final HashSet<String> badwords;

    static {
        badwords = new HashSet<>(Arrays.asList("аниме", "чекни", "ебать", "пиздец",
                "говно", "хуй", "ахуеть", "ебануться", "ебал", "пидор", "нигер", "нигеры", "нига",
                "мразь", "жопа", "феминизм", "феминистка", "феминист", "толерантность", "толерантная", "толерантно", "толерантный", "толерантным", "толерантной",
                "лгбт", "геи", "гей", "гомосексуал", "лесбиянки", "транс", "трап", "трансгендер", "рашка", "расия", "ватник",
                "би", "кста", "спс", "сяб", "пжл", "инет", "инете", "деградация", "деградирую", "лол", "изи", "поч", "кек",
                "го", "пж", "епт", "епта", "рашке", "ахереть", "хер", "херня", "еблан", "экспириенс"));
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!ServiceCommand.getGuildConfig(event.getGuild().getIdLong()).filter) return;
        if(event.getMember().getUser().isBot()) return;
        //if(!Config.isFilter()) return; old

        String[] words = event.getMessage().getContentRaw().split("[\\s,.!?:;|]+");

        for(String word: words)
            if(badwords.contains(word.replaceAll("[\'\"`/*]","").toLowerCase())) {
                EmbedBuilder warningMsg = Messages.createForbiddenWordMsg(event.getAuthor());
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(warningMsg.build()).queue();
                return;
            }
        }

}
