package org.scarike.minecraft.reactor.post;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.scarike.minecraft.entity.MinecraftMessage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Accessors(chain = true)
public class RegexRule implements Rule {
    private List<Pattern> include;
    private List<Pattern> exclude;
    private List<String> white;
    private List<String> black;

    @Override
    public boolean match(MinecraftMessage _message) {
        String player= _message.getPlayer();
        String message=_message.getMessage();

        //黑名单过滤
        if(black!=null&&black.size()>0&&black.contains(player)){
            return false;
        }

        //白名单过滤
        if(white!=null&&white.size()>0&&(!white.contains(player))){
            return false;
        }
        if (exclude != null) {
            for (Pattern exc : exclude) {
                if (exc.matcher(message).matches()) {
                    return false;
                }
            }
        }
        if(include!=null){
            for (Pattern inc : include) {
                Matcher matcher=inc.matcher(message);
                if (matcher.matches()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
