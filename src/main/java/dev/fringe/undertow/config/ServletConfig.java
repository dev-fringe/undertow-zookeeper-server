package dev.fringe.undertow.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.fringe.undertow.scheduled.ZkRegisterJob;

@EnableWebMvc
@Configuration
@Import({ZkCuratorConfig.class, ZkServiceDiscoveryConfig.class, ZkRegisterJob.class})
@ComponentScan(basePackages = {"dev.fringe.undertow.web","dev.fringe.undertow.service"})
@EnableScheduling
@PropertySource("classpath:app.properties")
public class ServletConfig implements WebMvcConfigurer  {

}
