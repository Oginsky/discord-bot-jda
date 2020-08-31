package bot.command.user;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.message.Messages;
import bot.tool.parsers.CoursesParser;
import bot.tool.records.CoursesRecord;
import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.LinkedList;

public class CoursesCommand extends ListenerAdapter implements ICommand {

    private static LinkedList<CoursesRecord> filter(String type, LinkedList<CoursesRecord> events) {
        LinkedList<CoursesRecord> filtredEvent = new LinkedList<>();
        for(CoursesRecord event: events) {
            if(event.place.contains(type) || event.type.contains(type)) filtredEvent.add(event);
        }
        return filtredEvent;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(Config.prefix + "events")) {
            CoursesParser parser = new CoursesParser();
            LinkedList<CoursesRecord> events = parser.CoursesList();
            System.out.println("Events records size: " + events.size());
            event.getChannel().sendTyping().queue();
            if(events == null || events.isEmpty()) {
                EmbedBuilder msg = Messages.createExceptionMsg("Не найдено ни оного события", "");
                event.getChannel().sendMessage(msg.build()).queue();
                return;
            }
           /* while(!events.isEmpty()) {
                EmbedBuilder msg = Messages.createEventMsg("Онлайн", events);
                event.getChannel().sendMessage(msg.build()).queue();
            }*/

            LinkedList<CoursesRecord> filtredEvent = filter("Онлайн", events);
            System.out.println("Filtred events records size: " + filtredEvent.size());
            while(!filtredEvent.isEmpty()) {
                EmbedBuilder msg = Messages.createEventMsg("Онлайн", filtredEvent);
                event.getChannel().sendMessage(msg.build()).queue();
            }
            filtredEvent = filter("", events);
            System.out.println("II. Filtred events records size: " + filtredEvent.size());
            while(!filtredEvent.isEmpty()) {
                EmbedBuilder msg = Messages.createEventMsg("Офлайн", filtredEvent);
                event.getChannel().sendMessage(msg.build()).queue();
            }
            System.out.println("End events command");
        }
    }

    @Override
    public String getName() {
        return "events";
    }

    @Override
    public String getDescriptions() {
        return "Предстоящие it мероприятия";
    }

    @Override
    public String howToUse() {
        return "Обязательно указывайте значение флагов: " + getFlags();
    }

    @Override
    public String getFlags() {
        return "[-t {online, offline}], [-c <1...25>]";
    }
}
