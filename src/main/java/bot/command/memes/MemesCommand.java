package bot.command.memes;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.util.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class MemesCommand extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String content = event.getMessage().getContentRaw().trim();
        if(content.length() > 20) return; // optimization, if it's long message it's not a mem command;
        if(event.getMember().getUser().isBot()) return;

        content = content.replaceAll("[,.]", "").toLowerCase();
        String memURL = "";

        switch (content) {
            case "f":
                event.getChannel().sendMessage("https://i.imgur.com/orpwVv4.jpg").queue();
                event.getChannel().sendMessage("https://i.imgur.com/HyNHy4y.jpg").queue();
                event.getChannel().sendMessage("https://i.imgur.com/LBzCOSR.jpg").queue();
                memURL = "https://i.imgur.com/QgUbHhD.jpg";
                break;

            case "не говори так": memURL = "https://i.imgur.com/WGqPowR.jpg";break;
            case "ваше мнение": memURL = "https://i.imgur.com/nUFZcHE.jpeg";break;
            case "все нормально": memURL = "https://i.imgur.com/LDPd7r5.jpg";break;
            case "денеги на карту": memURL = "https://i.imgur.com/7TBOfSo.jpeg";break;
            case "заболел": memURL = "https://i.imgur.com/uDJoZoz.png";break;
            case "зигую": memURL = "https://i.imgur.com/GR4x49t.jpeg";break;
            case "качалка": memURL = "https://i.imgur.com/W2pGZFN.jpg";break;
            case "классика": case "classic": memURL = "https://i.imgur.com/6IsFn7s.jpg";break;
            case "критический промах": memURL = "https://i.imgur.com/TXwOPfH.jpeg";break;
            case "нарезка": memURL = "https://i.imgur.com/bUXvnaT.png";break;
            case "не понял": memURL = "https://i.imgur.com/CEzdCLX.jpeg";break;
            case "я помогу":memURL = "https://i.imgur.com/epQtuog.jpg";break;
            case "работа": memURL = "https://i.imgur.com/wgSA64P.jpg";break;
            case "скоро умру": memURL = "https://i.imgur.com/7jHbzoF.png";break;
            case "у меня проблемы": memURL = "https://i.imgur.com/wX5fvVl.jpeg";break;
            case "устал": memURL = "https://i.imgur.com/vszHvEr.jpeg";break;
            case "я не понял": memURL = "https://i.imgur.com/vKRdTMr.jpeg";break;
            case "понял": memURL = "https://i.imgur.com/3wbG6jp.jpeg";break;
            case "я разочарован": memURL = "https://i.imgur.com/3yp2bgc.jpg";break;
            case "я спать": memURL = "https://i.imgur.com/NsvAKMp.jpeg";break;
            case "я тут": memURL = "https://i.imgur.com/bZdlGRx.jpeg";break;
            case "я ушел": case"я пойду": case"я ушёл": memURL = "https://i.imgur.com/Rz8S1Qb.jpeg";break;
            case "хороший код": memURL = "https://i.imgur.com/4k8nRws.jpg";break;
        }
        if(memURL.isEmpty()) return;

        CommandHandler.ignoreDenied = true;
        event.getChannel().sendMessage(memURL).queue();
    }

    @Override
    public String getName() {

        return "f, не говори так, ваше мнение, все нормально, " +
                "денег нет, заболел, зигую, качалка, классика(classic), " +
                "критический промах, нарезка, не понял, я помогу, работа, " +
                "скоро умру, у меня проблемы, устал, я не понял, понял, " +
                "я разочарован, я спать, я тут, я ушел(я пойду), хороший код";
    }

    @Override
    public String getDescriptions() {
        return "";
    }
}
