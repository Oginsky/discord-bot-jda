package bot.module.voice;

import bot.command.ServiceCommand;
import bot.tool.music.AudioPlayerSendHandler;
import bot.tool.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class SoundGreeting extends ListenerAdapter {

    private static AudioManager manager;

    private boolean findRole(List<Role> roles, String roleName) {
        for(Role role: roles)
            if(role.getName().equalsIgnoreCase(roleName))
                return true;
        return false;
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        if(event.getMember().getUser().equals(event.getJDA().getSelfUser())) return;
        if(!ServiceCommand.getGuildConfig(event.getGuild().getIdLong()).soundGreeting) return;
        Guild guild = event.getGuild();
        VoiceChannel channel = event.getChannelJoined();
        manager = guild.getAudioManager();
        //((AudioManager) manager).setSendingHandler(new AudioPlayerSendHandler());
        String soundURL = "";

        if(event.getChannelJoined().getName().equalsIgnoreCase("танкисты-пробиваторы"))
            soundURL = "https://www.youtube.com/watch?v=-BPlwV6mUyw&ab_channel=officialacaee";
        else if(event.getMember().getIdLong() == 503245858481897493L)
            soundURL = "https://www.youtube.com/watch?v=6SnfXSYEYxY&ab_channel=Seraphine";
        else if(findRole(event.getMember().getRoles(), "босс качалки"))
            soundURL = "https://www.youtube.com/watch?v=iCa6o_zzZXs&ab_channel=dumbassdevil";
        else if(findRole(event.getMember().getRoles(), "днище"))
            soundURL = "https://www.youtube.com/watch?v=cTbPw20ZzNg&ab_channel=SourceTV";
        if (!soundURL.isEmpty()) {
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.getQueue().clear();
            manager.openAudioConnection(channel);
            PlayerManager.getInstance().loadAndPlay(event.getGuild(), soundURL, false);
        }
    }

    public static void closeConnection() {
        manager.closeAudioConnection();
    }
}
