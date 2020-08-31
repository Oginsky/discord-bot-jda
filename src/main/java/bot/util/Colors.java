package bot.util;

import bot.command.CommandType;

import java.awt.Color;

public class Colors {

    public static final Color errorMsg = new Color(170, 54, 54);
    public static final Color simpleMsg = new Color(255, 255, 255);
    public static final Color logMsg = new Color(141, 141, 141);
    public static final Color eventsMsg = new Color(0xFF40931F);
    public static final Color filmsMsg = new Color(212, 88, 37);
    public static final Color exceptionMsg = new Color(242, 252, 13);

    public static Color eventType(String typeOfEvent) {
        switch (typeOfEvent) {
            case "Онлайн" : return new Color(0x34B808);
            case "offline" : return new Color(0xFC8830);
            default:
                    return new Color(0x1D5CF5);
        }
    }

    public static Color helpType(CommandType type) {
        switch (type){
            case USER: return new Color(0x27B472);
            case SERVICE: return new Color(0xD042DC);
            case COMMUNICATE: return new Color(0xE54B4B);
            case MEM: return new Color(0xFFE50F);
            default: return new Color(0x4DB3FF);
        }
    }

}
