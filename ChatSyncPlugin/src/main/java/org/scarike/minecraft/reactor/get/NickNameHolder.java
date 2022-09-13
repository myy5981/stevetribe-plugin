package org.scarike.minecraft.reactor.get;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class NickNameHolder {
    private final Map<Long,String> name_map=new HashMap<>();
    private final String serverAddress;
    OkHttpClient cli=new OkHttpClient();

    public NickNameHolder(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getName(Long id){
        String name = name_map.get(id);
        if(name!=null){
            return name;
        }
        return null;
    }
}
