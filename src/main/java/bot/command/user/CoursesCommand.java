package bot.command.user;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.message.Messages;
import bot.tool.parsers.CoursesParser;
import bot.tool.records.CoursesRecord;
import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.LinkedList;

public class CoursesCommand extends ListenerAdapter implements ICommand {

    private String type;

    private static LinkedList<CoursesRecord> filter(String type, LinkedList<CoursesRecord> events) {
        LinkedList<CoursesRecord> onlineEvent = new LinkedList<>();
        LinkedList<CoursesRecord> offlineEvent = new LinkedList<>();
        for(CoursesRecord event: events) {
            if(event.place.contains("онлайн") || event.type.contains("онлайн")) onlineEvent.add(event);
            else if(event.title.contains("Онлайн") || event.title.contains("онлайн")) onlineEvent.add(event);
            else offlineEvent.add(event);
        }
        return (type.equalsIgnoreCase("online")) ? onlineEvent : offlineEvent;
    }

    private boolean modifications(String[] args) {
        type = "";
        if(args.length == 1) return true;
        if(args.length != 3) return false;
        if(!args[2].equalsIgnoreCase("online") && !args[2].equalsIgnoreCase("offline")) return false;
        type = args[2];
        return true;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String[] args = event.getMessage().getContentRaw().split(" ");


        if(!args[0].equalsIgnoreCase(Config.prefix + "events")) return;

            if(!modifications(args)) {
                String description = (args.length == 3) ? "Выбирите __online__ или __offline__ мероприятия" : howToUse();
                EmbedBuilder errorMsg = Messages.createErrorMsg("Неверный синтаксис", description, true, getName());
                event.getChannel().sendMessage(errorMsg.build()).queue();
                return;
            }

            CoursesParser parser = new CoursesParser();
            LinkedList<CoursesRecord> events = parser.CoursesList();
            if(events == null || events.isEmpty()) {
                EmbedBuilder msg = Messages.createExceptionMsg("Не найдено ни одного события", "", false, getName());
                event.getChannel().sendMessage(msg.build()).queue();
                return;
            }

            if(!type.isEmpty()) events = filter(type, events);

            if(events == null || events.isEmpty()) {
                EmbedBuilder exceptionMsg = Messages.createExceptionMsg("Ничего не нашлось", "Возможно ошибка не сервере", false, getName());
                event.getChannel().sendMessage(exceptionMsg.build()).queue();
                return;
            }
            event.getChannel().sendTyping().queue();
            while(!events.isEmpty()) {
                EmbedBuilder msg = Messages.createEventMsg((type.isEmpty()) ? "Все":type, events);
                event.getChannel().sendMessage(msg.build()).queue();
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
        return "Обязательно указывайте значение флага: " + getFlags();
    }

    @Override
    public String getFlags() {
        return "[-t {online, offline}]";
    }
}
