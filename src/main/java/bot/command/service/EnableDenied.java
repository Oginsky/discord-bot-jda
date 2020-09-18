package bot.command.service;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.command.RegisteredCommands;
import bot.command.ServiceCommand;
import bot.message.Messages;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EnableDenied extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(!args[0].equalsIgnoreCase(Config.prefix + getName())) return;
        if(event.getAuthor().getIdLong() != Config.ownerID) {
            EmbedBuilder msg = Messages.createErrorMsg("Недостаточно прав для этого действия", "", false, "");
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        }

        if( args.length != 2){
            EmbedBuilder msg = Messages.createErrorMsg("Неверный синтаксис", howToUse(), true, getName());
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        }

        try {
            int value = Integer.parseInt(args[1]);
            if(value < 0 || value > 100) throw new NumberFormatException();
            ServiceCommand.getGuildConfig(event.getGuild().getIdLong()).deniedCoeff = value;
            ServiceCommand.getGuildConfig(event.getGuild().getIdLong()).hasDenied = (value != 0);
            EmbedBuilder msg = Messages.createEnableMsg("Denied " + ( (value == 0)? "отключен" : value ) );
            event.getChannel().sendMessage(msg.build()).queue();
            CommandHandler.ignoreDenied = true;
        } catch (NumberFormatException e) {
            EmbedBuilder errorMsg = Messages.createExceptionMsg("Неверно указан коэффициент", RegisteredCommands.howUsingByName("denied"), true, getName());
            event.getChannel().sendMessage(errorMsg.build()).queue();
        }

    }

    @Override
    public String getName() {
        return "denied";
    }

    @Override
    public String getDescriptions() {
        return "Неизвестно";
    }

}
