package bot.command.user;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.message.Messages;
import bot.tool.parsers.FilmsParser;
import bot.tool.records.FilmRecord;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class FilmsCommand extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String content = event.getMessage().getContentRaw();

        if(content.equalsIgnoreCase(Config.prefix + "films")) {
            FilmsParser parser = new FilmsParser();

            event.getChannel().sendTyping().queue();
            LinkedList<FilmRecord> films = parser.filmsList();

            while(!films.isEmpty()) {
                EmbedBuilder msg = Messages.createFilmsMsg(films);
                event.getChannel().sendMessage(msg.build()).queue();
            }
        }

    }

    @Override
    public String getName() {
        return "films";
    }

    @Override
    public String getDescriptions() {
        return "Расписание сеансов в кинотеатрах на сегодня";
    }

    @Override
    public String howToUse() {
        return "Обязательно указывайте значение флагов: " + getFlags();
    }

    @Override
    public String getFlags() {
        return "[-p <цена>]";
    }
}
