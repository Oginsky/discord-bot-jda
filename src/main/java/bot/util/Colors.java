package bot.util;

import bot.command.CommandType;

import java.awt.Color;

public class Colors {

    public static final Color errorMsg = new Color(170, 54, 54);
    public static final Color simpleMsg = new Color(255, 255, 255);
    public static final Color logMsg = new Color(26, 25, 25, 230);
    public static final Color eventsMsg = new Color(0xFF40931F);
    public static final Color filmsMsg = new Color(212, 88, 37);
    public static final Color exceptionMsg = new Color(242, 252, 13);
    public static final Color enableMsg = new Color(52, 184, 8);
    public static final Color forbiddenWordMsg = new Color(227, 34, 100);
    public static final Color gamesMsg = new Color(105, 7, 219);

    public static Color eventType(String typeOfEvent) {
        if(typeOfEvent.equalsIgnoreCase("online")) return new Color(0x13AC68);
        else if(typeOfEvent.equalsIgnoreCase("offline")) return new Color(0xF58E3A);
        return new Color(0x1D5CF5);
    }

    public static Color helpType(CommandType type) {
        switch (type) {
            case USER: return new Color(0x27B472);
            case MEM: return new Color(0xFFE50F);
            case SERVICE: return new Color(0xD042DC);
            case COMMUNICATE: return new Color(0xE54B4B);
            default: return new Color(0x4DB3FF);
        }
    }

    public static Color logType(String type) {
        switch (type) {
            case "role":return new Color(0x8D348D);
            case "join":return new Color(0xD0383E);
            default: return logMsg;
        }
    }

}
