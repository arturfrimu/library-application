package com.arturfrimu.library;

import com.arturfrimu.library.spec.support.TestSupportConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {LibraryApplication.class, TestSupportConfig.class})
class LibraryApplicationTests {
    @Test
    void contextLoads() {}
}
