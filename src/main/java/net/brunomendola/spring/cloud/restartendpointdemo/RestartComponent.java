package net.brunomendola.spring.cloud.restartendpointdemo;

import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Component;

@Component
public class RestartComponent {
  private final RestartEndpoint restartEndpoint;

  public RestartComponent(RestartEndpoint restartEndpoint) {
    this.restartEndpoint = restartEndpoint;
  }

  public void restart() {
    restartEndpoint.restart();
  }
}
