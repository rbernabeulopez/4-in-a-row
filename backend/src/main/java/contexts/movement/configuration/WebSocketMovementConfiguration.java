package contexts.movement.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMovementConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //Declare topic prefixes, give our app a destination prefix in which the user will be sending the data to the server
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/movements", "/user");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //Add the stamp endpoints
        //Allow the request from every different origin. Enable cross origin resource sharing for the whole website.
        registry.addEndpoint("/game").setAllowedOriginPatterns("*").withSockJS();
    }
}
