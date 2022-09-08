package org.scarike.minecraft.entity.post;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
@Accessors(chain = true)
public class RegexRule implements Rule {
    private List<Pattern> include;
    private List<Pattern> exclude;
    private List<String> players;

    @Override
    public boolean match(String player, String message) {
        if (players == null || players.size() == 0 ||
                players.contains(player)) {
            if (exclude != null) {
                for (Pattern exc : exclude) {
                    if (exc.matcher(message).matches()) {
                        return false;
                    }
                }
            }
            if(include!=null){
                for (Pattern inc : include) {
                    if (inc.matcher(message).matches()) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
