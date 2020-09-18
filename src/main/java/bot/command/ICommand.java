package bot.command;

public interface ICommand {

    String getName();

    String getDescriptions();

    default String howToUse() {
        return "";
    }

    default String getFlags() {
        return "";
    }

    default String getRequiredArgs() {return ""; }

}
