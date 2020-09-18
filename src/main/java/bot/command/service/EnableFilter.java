package bot.command.service;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.command.ServiceCommand;
import bot.message.Messages;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EnableFilter extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(!args[0].equalsIgnoreCase(Config.prefix + getName())) return;
        if(!CommandHandler.hasPermission(event.getMember())) {
            EmbedBuilder exceptionMsg = Messages.createExceptionMsg("Недостаточно прав", "У вас нет прав администратора для этого действия", false, "");
            event.getChannel().sendMessage(exceptionMsg.build()).queue();
            return;
        }

        if( args.length != 2 || (!args[1].equals("+") && !args[1].equals("-")) ){
            EmbedBuilder msg = Messages.createErrorMsg("Неверный синтаксис", howToUse(), true, getName());
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        }
        ServiceCommand.getGuildConfig(event.getGuild().getIdLong()).filter = args[1].equals("+");
        EmbedBuilder msg = Messages.createEnableMsg("Фильтр " + ( (args[1].equals("+"))? "включен" : "выключен" ) );
        event.getChannel().sendMessage(msg.build()).queue();

    }

    @Override
    public String getName() {
        return "filter";
    }

    @Override
    public String getDescriptions() {
        return "Включает/Отключает фильтрацию сообщений";
    }

    @Override
    public String howToUse() {
        return "Напишите: " + Config.prefix + getName() + " и '+' или '-' для вкл/выкл";
    }

    @Override
    public String getRequiredArgs() {return "< +/- >";};
}
