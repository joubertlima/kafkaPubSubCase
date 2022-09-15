package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class LoadProperties {

    public static Properties loadConfig(final String configFile) {
        try {
            if (!Files.exists(Paths.get(configFile))) {
                throw new IOException(configFile + " not found.");
            }
            final Properties cfg = new Properties();
            try (InputStream inputStream = new FileInputStream(configFile)) {
                cfg.load(inputStream);
            }
            return cfg;
        } catch (IOException e) {
            return null;
        }
    }
}
