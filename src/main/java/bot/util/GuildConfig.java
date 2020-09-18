package bot.util;

public class GuildConfig {
    public boolean hasLog;
    public long logChannelID;
    public boolean filter;
    public boolean hasDenied;
    public int deniedCoeff;
    public boolean soundGreeting;

    public GuildConfig() {
     this.hasLog = false;
     this.logChannelID = 0;

     this.filter = true;

     this.hasDenied = false;
     this.deniedCoeff = 0;

     this.soundGreeting = true;
    }
}
