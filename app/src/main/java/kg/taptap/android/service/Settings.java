package kg.taptap.android.service;

import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Properties;

@Singleton
public class Settings {
    private Application application;

    private String configFile = "application.properties";
    private Properties properties;

    @Inject
    public Settings(Application application) {
        this.application = application;
    }

    public String getProperty(String name) {
        return getConfig().getProperty(name);
    }

    public String getProperty(String name, String defaultValue) {
        return getConfig().getProperty(name, defaultValue);
    }

    public boolean getBooleanProperty(String name) {
        return StringUtils.equals(getProperty(name), "true");
    }

    private Properties getConfig() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(application.getAssets().open(configFile));
            } catch (IOException e) {
                throw new RuntimeException("Please create assets/" + configFile + " file", e);
            }
        }
        
        return properties;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
}
