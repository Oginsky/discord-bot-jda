package bot.command.communicate;

import bot.command.CommandHandler;
import bot.command.CommandType;
import bot.command.ICommand;
import bot.command.RegisteredCommands;
import bot.message.Messages;
import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCommand extends ListenerAdapter implements ICommand {

    private static final String[] keyWords;

    static {
        keyWords = new String[]{"info", "help"};
    }

    private boolean isHelpCommand(String content) {
        for(String command: keyWords)
            if(content.equalsIgnoreCase(Config.prefix+command)) return true;
        return false;
    }


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")) {
            event.getChannel().sendMessage("Используйте " + Config.prefix + getName() + " для просмотра списка команд").queue();
            return;
        }

        if(!CommandHandler.isValid(event)) return;

        if(isHelpCommand(args[0])) {
            if(args.length > 1) {
                EmbedBuilder msg = new EmbedBuilder().setImage("https://i.imgur.com/Ntd9MyM.jpg").setColor(Colors.helpType(CommandType.USER));
                event.getChannel().sendMessage(msg.build()).queue();
                return;
            }
            event.getChannel().sendTyping().queue();
            for(CommandType type: CommandType.values()) {
                EmbedBuilder helpMsg = Messages.createHelpMsg(type, RegisteredCommands.commands, getDescriptions());
                event.getChannel().sendMessage(helpMsg.build()).queue();
            }
        }

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescriptions() {
        return "[] - опционально\n<> - обязательно\n{...} - допустимые значения";
    }

    @Override
    public String getFlags() {
        return "[command]";
    }

    @Override
    public String howToUse(){
        return "Используйте " + Config.prefix + "help " + getFlags() + ", где command - название доступной команды (без префикса)";
    }
}
