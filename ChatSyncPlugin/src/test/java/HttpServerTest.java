import com.google.gson.Gson;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;
import org.scarike.minecraft.conf.Configuration;
import org.scarike.minecraft.entity.MinecraftMessage;
import org.scarike.minecraft.entity.QQMessage;
import org.scarike.minecraft.reactor.get.QQMessageConverter;
import org.scarike.minecraft.util.Format;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

public class HttpServerTest {

    @Test
    public void run() throws InterruptedException, IOException, InvalidConfigurationException {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.load(new File("src/main/resources/config.yml"));
        Configuration conf = Configuration.readConf(configuration);
        return;
    }
}