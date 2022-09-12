package org.scarike.minecraft.entity.post;

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
    private List<String> players;

    @Override
    public Matcher match(MinecraftMessage _message) {
        String player= _message.getPlayer();
        String message=_message.getMessage();
        if (players == null || players.size() == 0 ||
                players.contains(player)) {
            if (exclude != null) {
                for (Pattern exc : exclude) {
                    if (exc.matcher(message).matches()) {
                        return null;
                    }
                }
            }
            if(include!=null){
                for (Pattern inc : include) {
                    Matcher matcher=inc.matcher(message);
                    if (matcher.matches()) {
                        return matcher;
                    }
                }
                return null;
            }
            return DEFAULT_MATCHER_ALL;
        }
        return null;
    }
}
