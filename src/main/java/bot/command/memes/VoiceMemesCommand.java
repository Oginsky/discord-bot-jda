package bot.command.memes;

import bot.command.CommandHandler;
import bot.command.ICommand;
import bot.tool.music.PlayerManager;
import bot.util.Config;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class VoiceMemesCommand extends ListenerAdapter implements ICommand {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw().trim();
        if (content.length() > 30) return; // optimization, if it's long message it's not a mem command;
        if (event.getMember().getUser().isBot()) return;

        content = content.replaceAll("[,.]", "").toLowerCase();
       if(!content.startsWith(Config.prefix)) return;
       content = content.substring(1);
        String memURL = "";

        switch (content) {
            case "тьфу": memURL = "https://www.youtube.com/watch?v=ge1hblUpvDE&ab_channel=TrismegistusLol";break;
            case "совсем дурак": memURL = "https://www.youtube.com/watch?v=6GmIZG-SgqM&ab_channel=%D0%98%D0%B3%D0%BE%D1%80%D1%8C%D0%AE%D0%B4%D0%B5%D0%BD%D0%BA%D0%BE";break;
            case "естественно": memURL = "https://www.youtube.com/watch?v=BpGOB8CdWtw&ab_channel=%D0%92%D0%A1%D0%95%D0%94%D0%9B%D0%AF%D0%92%D0%98%D0%94%D0%95%D0%9E%D0%9C%D0%9E%D0%9D%D0%A2%D0%90%D0%96%D0%90";break;
            case "внатуре четко": memURL = "https://www.youtube.com/watch?v=1ZT7uXez-WE&ab_channel=%D0%AE%D1%80%D0%B8%D0%B9%D0%AF%D1%86%D1%8B%D0%BD%D0%B5%D0%BD%D0%BA%D0%BE";break;
            case "мальчик иди отсюда": memURL = "https://www.youtube.com/watch?v=Ju1VyLDaqYA&ab_channel=%D0%94%D0%BC%D0%B8%D1%82%D1%80%D0%B8%D0%B9%D0%92%D0%BB%D0%B0%D0%B4%D0%B8%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B8%D1%87";break;
            case "вырубай интернет": memURL = "https://www.youtube.com/watch?v=dglMh5er36U&ab_channel=%D0%92%D0%B0%D0%BB%D0%B5%D1%80%D0%B8%D0%B9%D0%93%D0%B0%D0%BD%D0%B8%D0%B5%D0%B2";break;
            case "ваше дело": memURL = "https://www.youtube.com/watch?v=ONy3k4V7CPw&ab_channel=WOTKINMAN";break;
            case "залп": memURL = "https://www.youtube.com/watch?v=pQIXIpz-gu4&ab_channel=JackFaiSeT";break;
            case "why are u gae": memURL = "https://www.youtube.com/watch?v=Ay0pEyN7A_s&ab_channel=JohnnyBee";break;
            case "спасибо кеп": memURL = "https://www.youtube.com/watch?v=7Zm1hPbmzPw&ab_channel=BigManTyrone";break;
            case "что ты такое": memURL = "https://www.youtube.com/watch?v=EPYWKAwf8eI&ab_channel=superduperjackass";break;
            case "ты не пройдешь": memURL = "https://www.youtube.com/watch?v=3xYXUeSmb-Y&ab_channel=BillCampbell";break;
        }
        if(memURL.isEmpty()) return;
        CommandHandler.ignoreDenied = true;

        final TextChannel channel = event.getChannel();
        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();


        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Вы должны быть подключены к голосовому каналу для этой команды").queue();
            return;
        }
        AudioManager manager = event.getGuild().getAudioManager();
        PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.getQueue().clear();
        manager.openAudioConnection(member.getVoiceState().getChannel());
        PlayerManager.getInstance().loadAndPlay(event.getGuild(), memURL, true);
    }


    @Override
    public String getName() {
        return "тьфу, совсем дурак, естественно, внатуре четко, мальчик иди отсюда, " +
                "вырубай интернет, ваше дело, залп, why are u gae, спасибо кеп, что ты такое, " +
                "ты не пройдешь";
    }

    @Override
    public String getDescriptions() {
        return "";
    }
}
