package io.github.singhalmradul.authorizationserver.configuration;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/login"),
                request -> ok().render("login"));
    }

}
