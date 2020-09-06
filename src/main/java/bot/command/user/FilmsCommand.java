package bot.command.user;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.message.Messages;
import bot.tool.parsers.FilmsParser;
import bot.tool.records.FilmRecord;
import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmsCommand extends ListenerAdapter implements ICommand {

    private static final String filmChooseMsgContent = "\uD83D\uDEB7 - нечеловек **(250р)**\n\uD83D\uDEAE - пролетариат **(350р)**\n\uD83D\uDCB0 - капиталист **(все)**";
    private static long filmChooseMsgID;

    private static String filmName;
    private static String day;
    private static boolean findError = false;

    private boolean modifications(String[] args, TextChannel channel) {
        findError = false;
        filmName = day = "";
        for(int i = 1; i < args.length;) {
            switch (args[i]) {
                case "-f":
                    if(args.length <= i+1) return false;
                    filmName = args[i+1];
                    i+=2;
                    while (i < args.length && args[i].charAt(0) != '-') filmName += " " + args[i++];
                    break;
                case "-d":
                    if(args.length <= i+1)return false;
                    // Check correct date
                    Pattern datePattern = Pattern.compile("(0[1-9]|1[012])\\.(0[1-9]|[12][0-9]|3[01])");
                    if(!datePattern.matcher(args[i+1]).matches()) {
                        findError = true;
                        EmbedBuilder exceptionMsg = Messages.createErrorMsg("Некорректная дата", "Указывайте дату в формате MM.DD", true, getName());
                        channel.sendMessage(exceptionMsg.build()).queue();
                        return false;
                    }
                    //Date
                    Calendar calendar = Calendar.getInstance();
                    day = calendar.get(Calendar.YEAR) + args[i+1].replace(".", "");
                    i+=2;
                    break;
                default: return false;
            }
        }
        return true;
    }

    /*choose max price of session int the theatre*/
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!CommandHandler.isValid(event)) return;

        String[] content = event.getMessage().getContentRaw().split(" ");

        if (!content[0].equalsIgnoreCase(Config.prefix + "films")) return;

        if(!modifications(content, event.getChannel())) {
            if(findError) return;
            EmbedBuilder msg = Messages.createErrorMsg("Неверный синтаксис", howToUse(), true, getName());
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        }
        //System.out.println("FILMNAME: " + filmName);
        EmbedBuilder chooseFilmMsg = new EmbedBuilder();
        chooseFilmMsg.setTitle("Выберите ваш класс");
        chooseFilmMsg.setColor(Colors.exceptionMsg);
        chooseFilmMsg.setDescription(filmChooseMsgContent);

        event.getChannel().sendMessage(chooseFilmMsg.build()).queue(message -> {
            message.addReaction("\uD83D\uDEB7").queue(); // 🚷
            message.addReaction("\uD83D\uDEAE").queue(); // 🚮
            message.addReaction("\uD83D\uDCB0").queue(); // 💰
            filmChooseMsgID = message.getIdLong();
        });

    }
        /*print suitable sessions*/
        @Override
        public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
            if(event.getMember().getUser().equals(event.getJDA().getSelfUser())) return;

            long msgID = event.getMessageIdLong();
            if(event.getMessageIdLong() != filmChooseMsgID) return;

            // choose price
            String reactionUnicode = event.getReactionEmote().getName();
            int maxPrice = -1;
            switch (reactionUnicode) {
                case "\uD83D\uDEB7" -> maxPrice = 250;
                case "\uD83D\uDEAE" -> maxPrice = 350;
                case "\uD83D\uDCB0" -> maxPrice = Integer.MAX_VALUE;
                default -> event.getReaction().removeReaction().queue();
            }

            if(maxPrice == -1) return; // if add another reactions
            // clear choose msg
            event.getChannel().deleteMessageById(filmChooseMsgID).complete();
            // search films sessions
            FilmsParser parser = new FilmsParser(filmName, day);
            event.getChannel().sendTyping().queue();
            LinkedList<FilmRecord> films = parser.filmsList();

            // not found
            if(films == null || films.isEmpty()) {
                EmbedBuilder exceptionMsg = Messages.createExceptionMsg("В прокате ничего не найдено",
                        "Видимо на этот день ничего нет. (Возможно неверно указано название фильма или выбрана несуществующая дата)",
                        true, getName());
                event.getChannel().sendMessage(exceptionMsg.build()).queue();
                return;
            }

            // send films
            String info = "Показаны "+ ((maxPrice == Integer.MAX_VALUE) ? "все сеансы" : ("сеансы до " + maxPrice + "р"));
            info += " на " + ((day.isEmpty()) ? "сегодня" : day);
            event.getChannel().sendMessage(info).queue();
            while (!films.isEmpty()) {
                EmbedBuilder msg = Messages.createFilmsMsg(films, maxPrice, !filmName.isEmpty());
                event.getChannel().sendMessage(msg.build()).queue();
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
            return "[-f <filmname>], [-d <MM.DD>] (до недели)";
    }

}
