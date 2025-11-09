package com.arturfrimu.library.config;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Slf4j
@UtilityClass
public class ClockProvider {

    private static ApplicationContext applicationContext;

    @Component
    public static class ClockProviderInitializer implements ApplicationContextAware {

        @Override
        public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
            log.info("Setting ApplicationContext in ClockProvider");
            ClockProvider.applicationContext = applicationContext;
            log.info("ApplicationContext successfully set in ClockProvider");
        }
    }

    public static Instant getCurrentInstant() {
        ApplicationContext context = applicationContext;
        if (context == null) {
            log.warn("ApplicationContext is null in ClockProvider, falling back to Instant.now()");
            return Instant.now();
        }
        try {
            Clock clock = context.getBean(Clock.class);
            Instant instant = clock.instant();
            log.debug("Using Clock from ApplicationContext, instant: {}", instant);
            return instant;
        } catch (Exception e) {
            log.warn("Failed to get Clock bean from ApplicationContext, falling back to Instant.now(): {}", e.getMessage());
            return Instant.now();
        }
    }

    public static long getCurrentTimeMillis() {
        ApplicationContext context = applicationContext;
        if (context == null) {
            log.warn("ApplicationContext is null in ClockProvider, falling back to System.currentTimeMillis()");
            return System.currentTimeMillis();
        }
        try {
            Clock clock = context.getBean(Clock.class);
            long timestamp = clock.millis();
            log.debug("Using Clock from ApplicationContext, timestamp: {}", timestamp);
            return timestamp;
        } catch (Exception e) {
            log.warn("Failed to get Clock bean from ApplicationContext, falling back to System.currentTimeMillis(): {}", e.getMessage());
            return System.currentTimeMillis();
        }
    }
}

