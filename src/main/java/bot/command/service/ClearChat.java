package bot.command.service;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.message.Messages;
import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class ClearChat extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String[] args = event.getMessage().getContentRaw().split(" ");
        if(!args[0].equalsIgnoreCase(Config.prefix + getName())) return;

        if(!CommandHandler.hasPermission(event.getMember())) {
            EmbedBuilder exceptionMsg = Messages.createExceptionMsg("Недостаточно прав",
                    "У вас нет прав администратора для этого действия", false, "");
            event.getChannel().sendMessage(exceptionMsg.build()).queue();
            return;
        }

        if(args.length != 2){
            EmbedBuilder msg = Messages.createErrorMsg("Неверный синтаксис", howToUse(), true, getName());
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        }

        int count;
        try{
            count = Integer.parseInt(args[1]);
            if(count < 0 || count > 100) throw new NumberFormatException();
            List<Message> chatsMsgs = event.getChannel().getHistory().retrievePast((count == 100) ? count : count +1).complete();
            event.getChannel().deleteMessages(chatsMsgs).queue();
        } catch (NumberFormatException e) {
            EmbedBuilder msg = Messages.createErrorMsg("Неверный аргумент", howToUse(), true, getName());
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        } catch (Exception e) {
            // try delete old msg
            EmbedBuilder msg = Messages.createErrorMsg("Невозможно удалить слишком старые сообщения",
                    "Можно удалять сообщения в пределах двух недель", true, getName());
            event.getChannel().sendMessage(msg.build()).queue();
            return;
        }
        // success
        EmbedBuilder info = new EmbedBuilder().setColor(Colors.enableMsg)
                .setTitle("Сообщения успешно удалены").setDescription("Количество удаленных сообщений: " + count);
        event.getChannel().sendMessage(info.build()).queue();
    }


    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescriptions() {
        return "Удаляет последние сообщения";
    }

    @Override
    public String howToUse() {
        return "Используйте " + Config.prefix + getName() + " и укажите сколько сообщений нужно удалить (max 100)";
    }

    @Override
    public String getRequiredArgs() {
        return "<msg's count> (max 100)";
    }
}
