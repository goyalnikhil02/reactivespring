package com.springbotgradle.gradledemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import com.springbotgradle.gradledemo.RouterHandlers;

@Configuration
public class ReactiveConfig {

	 @Bean
	    RouterFunction<?> routerFunction(RouterHandlers routerHandlers) {
		
		 return RouterFunctions.route(RequestPredicates.GET("/rest/employee/all"),routerHandlers::getAll)
				 .andRoute(RequestPredicates.GET("/rest/employee/{id}"), routerHandlers::getId)
		         .andRoute(RequestPredicates.GET("/rest/employee/{id}/events"), routerHandlers::getEvents) ; 
		 
		 
	 }
}
