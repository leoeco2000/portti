package com.lemon.portti.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/9/1.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.boot.mail.properties")
@Data
public class MailProperties {

    private String host;

    private Integer port;

    private String userName;

    private String password;

    private String protocol;

    private String needAuth;

    private String sslClass;

}