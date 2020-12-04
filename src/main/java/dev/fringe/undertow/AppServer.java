package dev.fringe.undertow;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import io.undertow.Undertow;

@Configuration
@Import(UndertowServlet.class)
@PropertySource("classpath:app.properties")
public class AppServer implements InitializingBean {

	@Autowired Undertow undertow;

	public static void main(String[] args) {
		new AnnotationConfigApplicationContext(AppServer.class);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		undertow.start();
	}
}
