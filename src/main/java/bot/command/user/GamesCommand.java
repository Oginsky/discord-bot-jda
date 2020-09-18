package bot.command.user;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.message.Messages;
import bot.tool.parsers.CoursesParser;
import bot.tool.parsers.GamesParser;
import bot.tool.records.CoursesRecord;
import bot.tool.records.Record;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.LinkedList;

public class GamesCommand extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;
        String content = event.getMessage().getContentRaw();
        if(!content.equalsIgnoreCase(Config.prefix + getName())) return;

        GamesParser parser = new GamesParser();
        LinkedList<Record> games = parser.gamesList();

        if(games == null || games.isEmpty()) {
            EmbedBuilder msg = Messages.createExceptionMsg("Ничего не нашлось", "Возможно ошибка не сервере", false, getName());
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        }
        event.getChannel().sendTyping().queue();

        while(!games.isEmpty()) {
            EmbedBuilder msg = Messages.createGamesMsg(games);
            event.getChannel().sendMessage(msg.build()).queue();
        }


    }

    @Override
    public String getName() {
        return "games";
    }

    @Override
    public String getDescriptions() {
        return "Халявные раздачи игр";
    }

    @Override
    public String howToUse() {
        return "Напишите " + Config.prefix + getName();
    }
}
