package bot;

import bot.command.*;
import bot.command.communicate.ByeCommand;
import bot.command.communicate.Customization;
import bot.command.communicate.HelloCommand;
import bot.command.communicate.HelpCommand;
import bot.command.memes.MemesCommand;
import bot.command.memes.VoiceMemesCommand;
import bot.command.service.*;
import bot.command.user.CoursesCommand;
import bot.command.user.FilmsCommand;
import bot.command.user.GamesCommand;
import bot.module.text.MessageFilter;
import bot.module.voice.SoundGreeting;
import bot.util.Config;
import bot.module.text.Log;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    private static JDA jda;

    public static void main(String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(Config.token).build();
        }
        catch (LoginException e) {
            System.out.println("Can't login with server");
            e.printStackTrace();
        } catch (NullPointerException e ) {
            e.printStackTrace();
        }

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.watching("за кожаными мешками"));

        RegisteredCommands reg = new RegisteredCommands();

        // Communicate commands
        jda.addEventListener(new HelpCommand());
        jda.addEventListener(new HelloCommand());
        jda.addEventListener(new ByeCommand());
        jda.addEventListener(new Customization());
        // Memes Command
        jda.addEventListener(new MemesCommand());
        jda.addEventListener(new VoiceMemesCommand());
        // User commands
        jda.addEventListener(new CoursesCommand());
        jda.addEventListener(new FilmsCommand());
        jda.addEventListener(new GamesCommand());
        // Service commands
        jda.addEventListener(new EnableFilter());
        jda.addEventListener(new EnableDenied());
        jda.addEventListener(new EnableLog());
        jda.addEventListener(new EnableSoundGreeting());
        jda.addEventListener(new ClearChat());
        jda.addEventListener(new CommandHandler()); // always in the end commands
        // Modules
        jda.addEventListener(new Log());
        jda.addEventListener(new MessageFilter());
        jda.addEventListener(new SoundGreeting());


    }

}
// C:\Users\admin\Documents\IdeaProject\VolodyaSkynet\target\VolodyaSkynet-1.0-SNAPSHOT-jar-with-dependencies.jar