package org.scarike.minecraft.reactor.get;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.QQMessage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.scarike.minecraft.util.Format.formatQQ;

@Getter
@Setter
@Accessors(chain = true)
public class QQMessageConverter {
    private long self;
    private List<Long> origin;
    private List<Pattern> regex;
    private String format;


    public static final Pattern CQ_REGEX=Pattern.compile("\\[CQ:.*]");

    public String convert(QQMessage message){
        //如果不是消息事件，则返回null忽略
        if (!"message".equals(message.getPost_type())){
            return null;
        }
        //如果self-id不是配置的本身，则返回null忽略
        if (!(message.getSelf_id()!=null&&message.getSelf_id().equals(self))) {
            return null;
        }
        String player;
        if("private".equals(message.getMessage_type())){
            if(origin!=null&&origin.size()>0&&(!origin.contains(message.getUser_id()))){
                return null;
            }
            player = message.getSender().getNickname();
        }else if("group".equals(message.getMessage_type())){
            if(origin!=null&&origin.size()>0&&(!origin.contains(message.getGroup_id()))){
                return null;
            }
            if ("normal".equals(message.getSub_type())){
                player =message.getSender().getCard();
                if(player==null||"".equals(player)){
                    player=message.getSender().getNickname();
                }
            }else if("anonymous".equals(message.getSub_type())){
                player="匿名:"+message.getAnonymous().getName();
            }else {
                return null;
            }
        }else {
            //未知的消息类型，忽略
            return null;
        }
        String raw_message=message.getRaw_message();

        if(regex!=null){
            for (Pattern pattern : regex) {
                if (pattern.matcher(raw_message).matches()) {
                    raw_message = CQ_REGEX.matcher(raw_message).replaceAll("");
                    return formatQQ(format,player,raw_message);
                }
            }
        }
        return null;
    }
}
