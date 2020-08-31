package bot.command.communicate;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.util.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.internal.StringUtil;

import java.util.Random;

public class ByeCommand extends ListenerAdapter implements ICommand {

    private static final String[] bye;
    private static final String[] keyWords;
    static {
        bye = new String[] { "Good bye, ", "Довиждане, ", "До побачення, ",
                "Ate a vista, ", "Farvel, ", "Nashledanou,  ", "Arrivederci, ",
                "Auf Wiedersehen, ", "Нахвамдис, ", "Selamat jalan, ", "Bless, ",
                "Adios, hasta la vista, ", "La revedere, ", "Viso gero, ", "kveðja, ",
                "pożegnanie, ", "näkemiin, ", "Қош болыңыз!, ", "баяртай, ", "さようなら, ",
                "sbohem, ", "Пока, ", "збогом, ", "beannacht, ", "au revoir, "
        };
        keyWords = new String[] {"Bye", "Good bye", "Пока", "Акоп", "До свидания"};
    }

    private boolean isByeCommand(String content) {
        for(String command: keyWords)
            if(content.startsWith(Config.prefix+command)) return true;
        return false;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!CommandHandler.isValid(event)) return;

        String content = event.getMessage().getContentRaw();
        boolean isBye = isByeCommand(content);

        if(isBye) {
            Random rand = new Random();
            event.getChannel().sendMessage(bye[rand.nextInt(bye.length)] + event.getMember().getUser().getName() + "!").queue();
        }
    }

    @Override
    public String getName() {
        return "Bye";
    }

    @Override
    public String getDescriptions() {
        return "Прощание на случайном языке";
    }
}
