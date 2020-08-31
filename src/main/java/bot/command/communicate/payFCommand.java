package bot.command.communicate;

import bot.util.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class payFCommand extends ListenerAdapter {
    private static final String[] pictureUrls;

    static {
        pictureUrls = new String[]{"https://pbs.twimg.com/media/D2chqAdXQAIApei.jpg",
                "https://f.simpleminecraft.ru/uploads/monthly_2019_06/1546876236.png.d8ff94cc33d7bf9a90957be89e9d8f9c.png",
                "https://pm1.narvii.com/7160/6df260ea11c7c04b99d1322de25cf5245b5d87c8r1-764-602v2_hq.jpg",
                "https://cs10.pikabu.ru/post_img/2018/05/09/4/og_og_1525839843267670237.jpg"
        };
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(Config.prefix + "F")) {
            for(String pictureUrl: pictureUrls)
                event.getChannel().sendMessage(pictureUrl).queue();
        }
    }

}
