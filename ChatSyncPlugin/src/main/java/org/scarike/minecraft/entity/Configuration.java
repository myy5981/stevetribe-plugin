package org.scarike.minecraft.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.file.FileConfiguration;
import org.scarike.minecraft.entity.post.*;
import org.scarike.minecraft.exception.ConfigurationException;

import java.util.*;
import java.util.regex.Pattern;

@Getter
@Setter
@Accessors(chain = true)
public class Configuration {
    private static final Rule DEFAULT_RULE_ALL= message ->  Rule.DEFAULT_MATCHER_ALL;
    public static final String DEFAULT_FORMAT="$player$: $message$";

    private PostChatReactor post;

    private static Rule readRule(FileConfiguration conf, String name,Map<String,Rule> cache){
        if(name==null||name.equals("all")){
            return DEFAULT_RULE_ALL;
        }
        Rule r;
        if((r=cache.get(name))!=null){
            return r;
        }
        if (conf.get("post.rules." + name) == null) {
            throw new ConfigurationException("无法找到规则配置项："+name);
        }
        RegexRule rule=new RegexRule();
        List<String> includeList = conf.getStringList("post.rules."+ name + ".include");
        if(includeList!=null&&includeList.size()>0){
            List<Pattern> includes=new LinkedList<>();
            for (String s : includeList) {
                includes.add(Pattern.compile(s));
            }
            rule.setInclude(includes);
        }
        List<String> excludeList = conf.getStringList("post.rules."+ name + ".exclude");
        if(excludeList!=null&&excludeList.size()>0){
            List<Pattern> excludes=new LinkedList<>();
            for (String s : excludeList) {
                excludes.add(Pattern.compile(s));
            }
            rule.setExclude(excludes);
        }
        List<String> players = conf.getStringList("post.rules."+ name + ".player");
        if(players!=null&&players.size()>0){
            rule.setPlayers(players);
        }
        cache.put("name",rule);
        return rule;
    }

    private static List<Target> readTarget(Map<?,?> route,String defaultFormat){
        try {
            List<Target> targets=new LinkedList<>();
            List<Map<?,?>> targetConf = (List<Map<?, ?>>) route.get("target");
            if(targetConf==null||targetConf.size()==0){
                throw new ConfigurationException("至少需要一个target配置");
            }
            for (Map<?, ?> map : targetConf) {
                Target target=new Target();
                String type = (String) map.get("type");
                if (type ==null){
                    throw new ConfigurationException("属性type是必须的");
                }
                if("private".equals(type)){
                    target.setType(Target.MessageType.PRIVATE);
                }else if("group".equals(type)){
                    target.setType(Target.MessageType.GROUP);
                }else {
                    throw new ConfigurationException("type项可选private或group");
                }
                Number number=(Number) map.get("id");
                if(number==null){
                    throw new ConfigurationException("属性id是必须的");
                }
                target.setId(Long.toString(number.longValue()));
                String format=(String) map.get("format");
                target.setFormat(format==null?defaultFormat:format);
                targets.add(target);
            }
            return targets;
        }catch (ClassCastException e){
            throw new ConfigurationException("target配置语法错误",e);
        }
    }

    public static Configuration readConf(FileConfiguration conf){
        Configuration config=new Configuration();
        PostChatReactor post=new PostChatReactor().setFormat(conf.getString("post.format",DEFAULT_FORMAT));
        List<Map<?, ?>> routesConf = conf.getMapList("post.routes");
        List<Route> routes=new LinkedList<>();
        Map<String,Rule> rules=new HashMap<>(4,1);
        if(routesConf != null&&routesConf.size()>0){
            for (Map<?, ?> routeConf : routesConf) {
                String server_address = (String) routeConf.get("server_address");
                if (server_address==null){
                    throw new ConfigurationException("属性server_address是必须的");
                }
                Route route=new Route()
                        .setServerAddress(server_address)
                        .setRule(readRule(conf,(String) routeConf.get("rule"),rules));
                String format = (String) routeConf.get("format");
                route.setTargets(readTarget(routeConf,format!=null? format: post.getFormat()));
                routes.add(route);
            }
            post.setRoutes(routes);
        }
        return config.setPost(post);
    }

}
