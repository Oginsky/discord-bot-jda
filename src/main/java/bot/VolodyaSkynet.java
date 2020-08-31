package bot;

import bot.command.*;
import bot.command.communicate.ByeCommand;
import bot.command.communicate.HelloCommand;
import bot.command.communicate.HelpCommand;
import bot.command.communicate.payFCommand;
import bot.command.memes.MemesCommand;
import bot.command.service.ServiceCommands;
import bot.command.user.CoursesCommand;
import bot.command.user.FilmsCommand;
import bot.util.Config;
import bot.util.Log;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class VolodyaSkynet {

    private static JDA jda;

    public static void main(String[] args) {
        try {jda = new JDABuilder(AccountType.BOT).setToken(Config.token).build();}
        catch (LoginException e) {
            System.out.println("Can't login with server");
            e.printStackTrace();
        }

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.watching("за кожанными мешками"));

        RegisteredCommands reg = new RegisteredCommands();

        // Communicate commands
        jda.addEventListener(new HelpCommand());
        jda.addEventListener(new HelloCommand());
        jda.addEventListener(new ByeCommand());
        jda.addEventListener(new payFCommand());
        jda.addEventListener(new MemesCommand());
        // User commands
        jda.addEventListener(new CoursesCommand());
        jda.addEventListener(new FilmsCommand());
        // Section for testing command
        jda.addEventListener(new Testcommand());
        // Service commands
        jda.addEventListener(new ServiceCommands());
        jda.addEventListener(new Log());
        jda.addEventListener(new CommandHandler()); // always in the end

    }

}
