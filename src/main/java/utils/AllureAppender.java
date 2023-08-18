package utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.qameta.allure.Allure;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class AllureAppender extends AppenderBase<ILoggingEvent> {

    private String prefix;

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if (prefix == null || "".equals(prefix)) {
            addError("Prefix is not set for MapAppender.");
            return;
        }
        String level = iLoggingEvent.getLevel().levelStr;
        String thread = iLoggingEvent.getThreadName();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String message = iLoggingEvent.getMessage();

        String log = format("[ %s ] | %s |  %s  - | %s |", level, thread, time, message);
        Allure.step(log);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}