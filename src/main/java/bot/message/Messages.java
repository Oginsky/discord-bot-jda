package bot.message;

import bot.command.CommandType;
import bot.command.RegisteredCommands;
import bot.command.memes.MemesCommand;
import bot.tool.records.CoursesRecord;
import bot.tool.records.FilmRecord;
import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.util.LinkedList;

public class Messages {

    public static EmbedBuilder createFilmsMsg(LinkedList<FilmRecord> films) {
        final int fieldMaxNumber = 5;
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Фильмы в прокате");
        msg.setColor(Colors.filmsMsg);

        for(int i = 0; i < fieldMaxNumber; i++) {
            if(films.isEmpty()) break;

            FilmRecord film = films.getFirst();
            msg.addField(film.cinema, "", false);

            for(FilmRecord.Theatre th: film.theatres) {
                StringBuilder value = new StringBuilder();
                for(int j = 0; j < th.sessions.length; j++) {
                    value.append(th.sessions[j] + " ");
                    //value.append((th.prices.length > j) ? ("("+th.prices[j]+")") : "" + " ");
                }
                msg.addField(th.theatre, value.toString(), true);
            }
            films.remove(0);
        }
        return msg;
    }

    //TODO: type of events
    public static EmbedBuilder createEventMsg(String type, LinkedList<CoursesRecord> events) {
        final int size = 6;
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(type + " мероприятия");
        msg.setColor(Colors.eventType(type));

        for(int i = 0; i < size; ++i) {
            if(events.isEmpty()) break;

            CoursesRecord event = events.getFirst();
            msg.addField(event.title, event.type, false);
            msg.addField("Дата", event.date, true);
            msg.addField("Ссылка", event.ref, true);
            msg.addField("Место", event.place, true);

            events.remove(0);
        }

        return msg;
    }

    public static EmbedBuilder createExceptionMsg(String title, String description) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(title);
        msg.setDescription(description);
        msg.setColor(Colors.exceptionMsg);
        return msg;
    }

    public static EmbedBuilder createLogMsg(String event, String descriptions, User author) {
        EmbedBuilder msg = new EmbedBuilder();

        return msg;
    }

    public static EmbedBuilder createHelpMsg(CommandType type, RegisteredCommands.Command[] commands, String description) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(type.getName());
        msg.setColor(Colors.helpType(type));
        msg.setDescription(description);

        if(type == CommandType.MEM) {
            msg.setDescription(new MemesCommand().getName());
            return msg;
        }

        for(RegisteredCommands.Command comm: commands) {
            if(comm.type == type) {
                msg.addField(Config.prefix + comm.command.getName(), comm.command.getDescriptions(), false);
                msg.addField("Модификации", comm.command.getFlags(), true);
            }
        }
        return msg;
    }

}
