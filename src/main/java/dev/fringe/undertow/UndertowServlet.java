package dev.fringe.undertow;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.util.ImmediateInstanceFactory;
import lombok.SneakyThrows;
import lombok.var;

public class UndertowServlet  {

	@Value("${server.port:8080}") int port; 
	@Value("${server.name:serviceA}") String serverName;
	
    @Bean
    @SneakyThrows
    public Undertow undertow() {
    	var context = new AnnotationConfigWebApplicationContext();// 여기서 프로퍼티를 받아 오자.
    	context.setConfigLocation(AppServer.class.getPackage().getName()+".config");    	
        var manager = Servlets.defaultContainer().addDeployment(Servlets.deployment()
        		.setClassLoader(this.getClass().getClassLoader())
                .setContextPath("/")
                .setDeploymentName(serverName + ".war")
                .addServlet(
                		Servlets.servlet("DispatcherServlet", DispatcherServlet.class, new ImmediateInstanceFactory<>(new DispatcherServlet(context))).addMapping("/*"))
                .addListener(
                		new ListenerInfo(ContextLoaderListener.class, new ImmediateInstanceFactory<>(new ContextLoaderListener(context)))));
        manager.deploy();
        var path = Handlers.path().addPrefixPath("/", manager.start());
        return Undertow.builder().addHttpListener(port, "0.0.0.0").setHandler(path).build();    	
    }
}
