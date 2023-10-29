package com.gtihub.lipiridi.webflux;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("logging.webflux.http")
public class LoggingProperties {

    private boolean enabled = true;

    private List<String> ignorePatterns = new ArrayList<>();

    public boolean isEnabled() {
        return this.enabled;
    }

    public List<String> getIgnorePatterns() {
        return this.ignorePatterns;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setIgnorePatterns(List<String> ignorePatterns) {
        this.ignorePatterns = ignorePatterns;
    }
}
