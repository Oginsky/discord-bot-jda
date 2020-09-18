package bot.module.text;

import bot.command.ServiceCommand;
import bot.message.Messages;
import bot.util.Colors;
import bot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Log extends ListenerAdapter {

    private String eventName;
    private String descriptions;
    private User author;
    private Color color;

    private boolean hasLog(long guildID) {
        return ServiceCommand.getGuildConfig(guildID).hasLog;
        //return Config.isHasLog(); old
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }

    private void sendLogMsg(Guild guild) {
        EmbedBuilder logMsg = Messages.createLogMsg(eventName, descriptions + "\nВремя: " + getDate(), author, color);
        TextChannel tc = guild.getTextChannelById(ServiceCommand.getGuildConfig(guild.getIdLong()).logChannelID);
        //TextChannel tc = guild.getTextChannelById(Config.getLogChannelID()); old
        if(tc != null)
            tc.sendMessage(logMsg.build()).queue();
      //  if(guild.getSelfMember().hasPermission(guild.getTextChannelById(Config.getLogChannelID()), Permission.MESSAGE_WRITE))
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Присоединился новый пользователь";
        descriptions = event.getMember().getUser().getName();
        author = event.getMember().getUser();
        color = Colors.logType("join");
        sendLogMsg(event.getGuild());

    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Сервер покинул пользователь";
        descriptions = event.getMember().getUser().getName();
        author = event.getMember().getUser();
        color = Colors.logType("join");
        sendLogMsg(event.getGuild());

    }
    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Статус";
        descriptions = event.getNewOnlineStatus().name();
        author = event.getMember().getUser();
        sendLogMsg(event.getGuild());
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Создана новая роль";
        descriptions = event.getRole().getName() + "\nАдмин права: " + ( (event.getRole().hasPermission(Permission.ADMINISTRATOR) ? "+" : "-") );
        author = null;
        color = Colors.logType("role");
        sendLogMsg(event.getGuild());
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Удалена роль";
        descriptions = event.getRole().getName() + "\nАдмин права: " + ( (event.getRole().hasPermission(Permission.ADMINISTRATOR) ? "+" : "-") );
        author = null;
        color = Colors.logType("role");
        sendLogMsg(event.getGuild());
    }

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Создан новый текстовый канал";
        descriptions = event.getChannel().getName();
        author = null;
        color = Colors.logMsg;
        sendLogMsg(event.getGuild());
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Удален текстовый канал";
        descriptions = event.getChannel().getName();
        author = null;
        color = Colors.logMsg;
        sendLogMsg(event.getGuild());
    }

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Создан новый голосовой канал";
        descriptions = event.getChannel().getName();
        author = null;
        color = Colors.logMsg;
        sendLogMsg(event.getGuild());
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        if(!hasLog(event.getGuild().getIdLong())) return;
        eventName = "Удален голосовой канал";
        descriptions = event.getChannel().getName();
        author = null;
        color = Colors.logMsg;
        sendLogMsg(event.getGuild());
    }

}
