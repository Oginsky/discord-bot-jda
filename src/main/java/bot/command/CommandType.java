package bot.command;

public enum CommandType {
    SERVICE("Служебные команды"), USER("Пользовательские команды"), COMMUNICATE("Разговорные команды"), MEM("Мемы: Золотая коллекция");

    private String name;

    CommandType(String name) {this.name = name;}

    public String getName() {return name;}

}
