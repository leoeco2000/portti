package com.lemon.portti;

import com.lemon.portti.web.listener.AppEnvPreparedEventListener;
import com.lemon.portti.web.listener.AppFailedEventListener;
import com.lemon.portti.web.listener.AppPreparedEventListener;
import com.lemon.portti.web.listener.AppStartedEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PorttiApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(PorttiApplication.class);
    app.addListeners(
        new AppStartedEventListener(),
        new AppEnvPreparedEventListener(),
        new AppPreparedEventListener(),
        new AppFailedEventListener());
    app.run(args);
  }

}
