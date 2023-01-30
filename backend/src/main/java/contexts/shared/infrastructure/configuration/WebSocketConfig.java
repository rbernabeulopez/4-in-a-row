package contexts.shared.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //Add the stamp endpoints
    //Allow the request from every different origin. Enable cross origin resource sharing for the whole website.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/service/v1").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //Declare topic prefixes, give our app a destination prefix in which the user will be sending the data to the server
        registry.setApplicationDestinationPrefixes("/service/v1");
        registry.enableSimpleBroker("/join-game","/game-notifications", "/personal-notifications");
    }
}
