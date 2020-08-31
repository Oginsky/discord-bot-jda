package bot.util;

public class Config {

    public static boolean isHasLog() {
        return hasLog;
    }

    public static String getLogChannelID() {
        return logChannelID;
    }

    public static boolean isBotAnswer() {
        return botAnswer;
    }

    public static void setBotAnswer(boolean botAnswer) {
        Config.botAnswer = botAnswer;
    }

    public static void setHasLog(boolean hasLog) {
        Config.hasLog = hasLog;
    }

    public static void setLogChannelID(String logChannelID) {
        Config.logChannelID = logChannelID;
    }

    public static boolean isHasDenied() {
        return hasDenied;
    }

    public static void setIsDenied(boolean hasDenied) {
        Config.hasDenied = hasDenied;
    }

    public static int getDeniedCoeff() {
        return deniedCoeff;
    }

    public static void setDeniedCoeff(int deniedCoeff) {
        Config.deniedCoeff = deniedCoeff;
    }

    public static boolean isRequiredPrefix() {
        return requiredPrefix;
    }

    public static void setRequiredPrefix(boolean requiredPrefix) {
        Config.requiredPrefix = requiredPrefix;
    }

    public static final String token = "";
    public static final String prefix = "!";

    public static final long adminID = 0L;
    public static final String badjokeID = "";

    private static boolean hasLog = false;
    private static String logChannelID = "";

    private static boolean botAnswer = false;

    private static boolean requiredPrefix = false;
    private static boolean hasDenied = false;
    private static int deniedCoeff = 0;

}
