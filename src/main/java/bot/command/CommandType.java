package bot.command;

public enum CommandType {
    SERVICE("Служебные команды"), USER("Пользовательские команды"), COMMUNICATE("Communicate"), MEM("Мемы");

    private String name;

    CommandType(String name) {this.name = name;}

    public String getName() {return name;}

}
