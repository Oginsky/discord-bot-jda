package bot.command;

import bot.command.communicate.ByeCommand;
import bot.command.communicate.HelloCommand;
import bot.command.communicate.HelpCommand;
import bot.command.memes.MemesCommand;
import bot.command.user.CoursesCommand;
import bot.command.user.FilmsCommand;

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
                new Command(new MemesCommand(), CommandType.MEM)
        };
    }

}
