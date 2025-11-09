package com.arturfrimu.library.testcontainer;

import com.arturfrimu.library.testcontainer.container.Containers;
import com.arturfrimu.library.testcontainer.container.WireMockTestContainer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class TestcontainersApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        Containers.run();

        String wireMockBase = "http://" +
                WireMockTestContainer.wireMockContainer.getHost() + ":" +
                WireMockTestContainer.wireMockContainer.getFirstMappedPort();

        Map<String, Object> p = new HashMap<>();
        p.put("application.person.url", wireMockBase);

        ctx.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("override-props", p));
    }
}