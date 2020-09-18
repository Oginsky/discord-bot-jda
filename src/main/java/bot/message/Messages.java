package bot.message;

import bot.command.CommandType;
import bot.command.RegisteredCommands;
import bot.command.ServiceCommand;
import bot.command.memes.MemesCommand;
import bot.command.memes.VoiceMemesCommand;
import bot.tool.records.CoursesRecord;
import bot.tool.records.FilmRecord;
import bot.tool.records.Record;
import bot.util.Colors;
import bot.util.Config;
import bot.util.GuildConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.LinkedList;

public class Messages {

    public static EmbedBuilder createFilmsMsg(LinkedList<FilmRecord> films, int maxPrice, boolean showPrice) {
        final int fieldMaxNumber = 6;
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Фильмы в прокате");
        msg.setColor(Colors.filmsMsg);

        for(int i = 0; i < fieldMaxNumber; i++) {
            if(films.isEmpty()) break;

            FilmRecord film = films.getFirst();
            msg.addField("__"+film.cinema+"__", "", false); // add film

            if(film.theatres.length == 0) continue;
            for(FilmRecord.Theatre th: film.theatres) {

                StringBuilder value = new StringBuilder();
                for(int j = 0; j < th.sessions.length; j++) {
                    if (th.prices[j] <= maxPrice)  {
                        value.append("**" + th.sessions[j] + "**" + " ");
                        if (showPrice) value.append( (th.prices[j] != -1) ? ("("+th.prices[j]+") ") : ("") );
                    }
                }

                if(value.length() != 0) msg.addField(th.theatre, value.toString(), true); // add thetre with seans
            }
            films.remove(0);
        }
        return msg;
    }

    public static EmbedBuilder createEventMsg(String type, LinkedList<CoursesRecord> events) {
        final int size = 6;
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(type + " мероприятия");
        msg.setColor(Colors.eventType(type));

        for(int i = 0; i < size; ++i) {
            if(events.isEmpty()) break;

            CoursesRecord event = events.getFirst();
            msg.addField("__"+event.title+"__", event.type, false);
            msg.addField("Дата", event.date, true);
            msg.addField("Место", event.place, true);
            msg.addField("Подробнее", "[Ссылка]("+event.ref+")", true);

            events.remove(0);
        }

        return msg;
    }

    public static EmbedBuilder createGamesMsg(LinkedList<Record> games) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Раздачи игры");
        msg.setColor(Colors.gamesMsg);
        StringBuilder description = new StringBuilder();
        description.ensureCapacity(500);
        for(int i = 0; i < 6; i++) {
            if(games.isEmpty()) break;
            Record game = games.getFirst();
            msg.addField("[" + game.type + "] " + game.title, "[Подробнее]("+game.description+")", true);
            games.remove(0);
        }
            msg.addField("", description.toString(), false);
        return msg;
    }

    public static EmbedBuilder createExceptionMsg(String title, String description, boolean setFooter, String commandName) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(title);
        msg.setDescription(description);
        msg.setColor(Colors.exceptionMsg);
        if(setFooter) msg.setFooter("Используйте " + Config.prefix + "help " + commandName);
        return msg;
    }

    public static EmbedBuilder createErrorMsg(String title, String description, boolean setFooter, String commandName) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(title);
        msg.setDescription(description);
        msg.setColor(Colors.errorMsg);
        if(setFooter) msg.setFooter("Используйте " + Config.prefix + "help " + commandName + " для дополнительной помощи");
        return msg;
    }

    public static EmbedBuilder createEnableMsg(String title) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("\u2705 Изменения сохранены: " + title); // ✅
        msg.setColor(Colors.enableMsg);
        return msg;
    }

    public static EmbedBuilder createLogMsg(String event, String descriptions, User author, Color color) {
        EmbedBuilder msg = new EmbedBuilder().setTitle(event).setDescription(descriptions).
                setColor(color);
        if(author != null) msg.setFooter(author.getName(), author.getAvatarUrl());
        return msg;
    }

    public static EmbedBuilder createHelpMsg(CommandType type, RegisteredCommands.Command[] commands, String description) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(type.getName());
        msg.setColor(Colors.helpType(type));
        msg.setDescription(description);

        if(type == CommandType.MEM) {
            msg.setDescription(new MemesCommand().getName());
            msg.addField("Voice Memes (Используйте " + Config.prefix + ")", new VoiceMemesCommand().getName(), false);
            return msg;
        }

        for(RegisteredCommands.Command comm: commands) {
            if(comm.type == type) {
                msg.addField(Config.prefix + comm.command.getName(), comm.command.getDescriptions(), false);
                if(!comm.command.getFlags().isEmpty()) msg.addField("Модификации:", comm.command.getFlags(), true);
                if(!comm.command.getRequiredArgs().isEmpty()) msg.addField("Аргументы:", comm.command.getRequiredArgs(), true);
            }
        }
        return msg;
    }

    public static EmbedBuilder createForbiddenWordMsg(User author) {
        EmbedBuilder msg = new EmbedBuilder();

        msg.setTitle("Отдел нравов");
        msg.setDescription("**Использование ненормативной лексики неприемлимо!!!**");
        msg.setFooter(author.getAsTag() + " не следил за своими выражениями", author.getAvatarUrl());
        msg.setColor(Colors.forbiddenWordMsg);

        return msg;
    }

    public static EmbedBuilder createBotParametersMsg(Guild guild) {
        EmbedBuilder msg = new EmbedBuilder();
        GuildConfig config = ServiceCommand.getGuildConfig(guild.getIdLong());
        msg.setTitle("Установленные настройки");
        msg.setColor(Colors.simpleMsg);
        msg.addField("__Log__", "Ведение лога на сервере", false);
            msg.addField("Состояние", (config.hasLog ? "Ведется" : "Отключено"), true);
            msg.addField("Канал лога", (config.hasLog ? guild.getTextChannelById(config.logChannelID).getName() : "-"), true);
        msg.addField("__Filter__", "Фильтрация сообщений", false);
            msg.addField("Состояние", (config.filter? "Ведется" : "Отключен"), true);
        msg.addField("__Denied__", "Неизвестно", false);
            msg.addField("Состояние", (config.hasDenied ? "+" : "-"), true);
            msg.addField("Значение", (config.hasDenied? "" + config.deniedCoeff : "-"), true);
        msg.addField("__Sound Greeting__", "Звуковые приветствия", false);
            msg.addField("Состояние", (config.soundGreeting ? "Включены" : "Отключены"), true);
        return msg;
    }

}
