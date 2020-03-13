package net.brunomendola.spring.cloud.restartendpointdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.jmx.enabled=true")
class RestartEndpointDemoApplicationTests {

  @Test
  void contextLoads() {
  }

}
