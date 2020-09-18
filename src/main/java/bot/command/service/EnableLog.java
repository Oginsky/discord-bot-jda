package bot.command.service;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.command.ServiceCommand;
import bot.message.Messages;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class EnableLog extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        if(!args[0].equalsIgnoreCase(Config.prefix + getName()))  return;
        if(!CommandHandler.hasPermission(event.getMember())) {
            EmbedBuilder exceptionMsg = Messages.createExceptionMsg("Недостаточно прав",
                    "У вас нет прав администратора для этого действия", false, "");
            event.getChannel().sendMessage(exceptionMsg.build()).queue();
            return;
        }

        //wrong syntax
        if( args.length != 3 || !(args[1].equals("+") || args[1].equals("-")) ) {
            EmbedBuilder errorMsg = Messages.createErrorMsg("Неверный синтаксис", howToUse(), true, getName());
            event.getChannel().sendMessage(errorMsg.build()).queue();
            return;
        }
        // check exist text channel by name
        List<TextChannel> channels = event.getGuild().getTextChannelsByName(args[2], true);
        if(channels == null || channels.isEmpty()) {
            EmbedBuilder exceptionMsg = Messages.createExceptionMsg("Не найден текстовый канал с указанным именем",
                    "Указывайте имя существующего канала", true, getName());
            event.getChannel().sendMessage(exceptionMsg.build()).queue();
            return;
        }
        // set hasLog
        long guildID = event.getGuild().getIdLong();
        ServiceCommand.getGuildConfig(guildID).hasLog = args[1].equals("+");
        ServiceCommand.getGuildConfig(guildID).logChannelID = event.getGuild().getTextChannelsByName(args[2], true).get(0).getIdLong();
        // send info msg
        EmbedBuilder msg = Messages.createEnableMsg("log " + ( (args[1].equals("+"))? "включен - канал " + args[2] : "выключен" ) );
        event.getChannel().sendMessage(msg.build()).queue();
    }

    @Override
    public String getName() {
        return "log";
    }

    @Override
    public String getDescriptions() {
        return "Включает/Отключает ведение лога на сервере";
    }

    @Override
    public String howToUse() {
        return "Используйте: " + Config.prefix + getName() + " +/- <textchannel> для вкл/выкл ведения лога в канале textchannel";
    }
    @Override
    public String getRequiredArgs() {return "< +/- textchannel >";};
}
