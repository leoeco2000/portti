package com.lemon.portti.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 上下文创建完成后执行的事件监听器
 *
 */
public class AppPreparedEventListener implements
    ApplicationListener<ApplicationPreparedEvent> {
  private Logger logger = LoggerFactory.getLogger(AppPreparedEventListener.class);

  @Override
  public void onApplicationEvent(ApplicationPreparedEvent event) {
    ConfigurableApplicationContext cac = event.getApplicationContext();
    passContextInfo(cac);
  }

  /**
   * 传递上下文
   * @param cac
   */
  private void passContextInfo(ApplicationContext cac) {
    System.err.println("dosomething()...");
  }

}