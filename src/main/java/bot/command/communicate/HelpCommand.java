package bot.command.communicate;

import bot.command.CommandType;
import bot.command.ICommand;
import bot.command.RegisteredCommands;
import bot.message.Messages;
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
        if(event.getMember().getUser().isBot()) return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        if(isHelpCommand(args[0])) {
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
        return "Для более подробной информации используйте: " + Config.prefix + "help [command]";
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
