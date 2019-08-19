package com.lemon.portti.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.GenericTypeResolver;

/**
 * spring boot 启动监听类
 *
 */
public class AppStartedEventListener implements
    ApplicationListener<ApplicationStartedEvent> {

  private Logger logger = LoggerFactory.getLogger(AppStartedEventListener.class);

  @Override
  public void onApplicationEvent(ApplicationStartedEvent event) {
    SpringApplication app = event.getSpringApplication();
    app.setBannerMode(Mode.OFF);// 不显示banner信息
    logger.info("==MyApplicationStartedEventListener==");
  }

  public static void main(String[] args) {
    Class declaredEventType = GenericTypeResolver.resolveTypeArgument(AppStartedEventListener.class, ApplicationListener.class);
    System.err.println(declaredEventType.getName());
    System.err.println(declaredEventType.isAssignableFrom(ApplicationStartedEvent.class));
  }
}