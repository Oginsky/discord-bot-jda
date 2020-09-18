package bot.command;

import bot.command.communicate.ByeCommand;
import bot.command.communicate.HelloCommand;
import bot.command.communicate.HelpCommand;
import bot.command.memes.MemesCommand;
import bot.command.communicate.Customization;
import bot.command.memes.VoiceMemesCommand;
import bot.command.service.*;
import bot.command.user.CoursesCommand;
import bot.command.user.FilmsCommand;
import bot.command.user.GamesCommand;

public class RegisteredCommands {


    public static class Command {
        public ICommand command;
        public CommandType type;

        public Command(ICommand command, CommandType type) {
            this.command = command;
            this.type = type;
        }
    }

    public static final Command[] commands;

    static {
        commands = new Command[] {
                new Command(new HelpCommand(), CommandType.COMMUNICATE),
                new Command(new HelloCommand(), CommandType.COMMUNICATE),
                new Command(new ByeCommand(), CommandType.COMMUNICATE),
                new Command(new CoursesCommand(), CommandType.USER),
                new Command(new FilmsCommand(), CommandType.USER),
                new Command(new GamesCommand(), CommandType.USER),
                new Command(new MemesCommand(), CommandType.MEM),
                new Command(new VoiceMemesCommand(), CommandType.MEM),
                new Command(new Customization(), CommandType.COMMUNICATE),
                new Command(new EnableFilter(), CommandType.SERVICE),
                new Command(new EnableDenied(), CommandType.SERVICE),
                new Command(new EnableLog(), CommandType.SERVICE),
                new Command(new ClearChat(), CommandType.SERVICE),
                new Command(new EnableSoundGreeting(), CommandType.SERVICE)
        };
    }

    public static String howUsingByName(String name) {
        for(Command command: commands)
            if(command.command.getName().equals(name))
                return command.command.howToUse();
            return "";
    }

}
