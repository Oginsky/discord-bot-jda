package bot.command.communicate;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.util.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.internal.StringUtil;

import java.util.Random;

public class HelloCommand extends ListenerAdapter implements ICommand {

    private static final String[] hello;
    private static final String[] keyWords;
    static {
        hello = new String[] { "Aloha, ", "Bonjou, ", "Bon die! Salute, ",
        "Сән бәәцхәнт, ", "Саламатчылык, ", "Silamun eleykum, ", "Hau, ",
        "Ave, ", "Manao ahoana, ", "Jeeka, ma tzuula, ", "Сайн байна уу, ",
        "Oi, ", "Здравствуйте, ", "Dumêlang, ", "Kumusta, ", "Hello, ",
        "Привіт, ", "A gaishai ka, ", "नमस्ते, ", "Dobar Dan, ", "Guuten takh, ",
        "God dag, ", "Buenas, ", "Mogethin, ", "бирибиэт, ", "Nibula, "
        };
        keyWords = new String[] {"Hi", "Hello", "Hey", "Привет", "Тевирп", "Здраствуй"};
    }

    private boolean isHelloCommand(String content) {
        for(String command: keyWords)
            if(content.equalsIgnoreCase(Config.prefix+command)) return true;
        return false;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String[] content = event.getMessage().getContentRaw().split(" ");

       if(isHelloCommand(content[0])) {
            Random rand = new Random();
            event.getChannel().sendMessage(hello[rand.nextInt(hello.length)] + event.getMember().getUser().getName() + "!").queue();
        }
    }

    @Override
    public String getName() {
        return "Hi"; /*StringUtil.join(keyWords, "\\");*/
    }

    @Override
    public String getDescriptions() {
        return "Приветствие";
    }
}
