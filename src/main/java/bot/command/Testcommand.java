package bot.command;

import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Testcommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //if(!CommandHandler.isValid(event)) return;
        if(event.getMember().getUser().isBot()) return;
        String arg = event.getMessage().getContentDisplay();
        System.out.println(arg);

        // It's work!
       /* EmbedBuilder embed = new EmbedBuilder();
        File file = new File("C:\\Users\\admin\\Documents\\IdeaProject\\VolodyaSkynet\\src\\main\\resources\\classic.jpg");
        embed.setImage("attachment://C:\\Users\\admin\\Documents\\IdeaProject\\VolodyaSkynet\\src\\main\\resources\\classic.jpg")
                .setDescription("This is a cute cat :3");
        event.getChannel().sendFile(file).embed(embed.build()).queue();
        */
        // It's too!
        //File file = new File("C:\\Users\\admin\\Documents\\IdeaProject\\VolodyaSkynet\\src\\main\\resources\\classic.jpg");
       // event.getChannel().sendFile(file).queue();

    }

}
