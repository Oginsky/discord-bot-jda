package bot.command.communicate;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.message.Messages;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Customization extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String content = event.getMessage().getContentRaw();
        if( !content.equalsIgnoreCase(Config.prefix + getName()) ) return;

        EmbedBuilder info = Messages.createBotParametersMsg(event.getGuild());
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(info.build()).queue();

    }

    @Override
    public String getName() {
        return "customization";
    }

    @Override
    public String getDescriptions() {
        return "Вывод установленных настроек бота";
    }

    @Override
    public String howToUse() {
        return "Напишите " + Config.prefix + getName();
    }

}
