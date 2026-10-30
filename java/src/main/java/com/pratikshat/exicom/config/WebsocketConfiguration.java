package com.pratikshat.exicom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry reg) {
		reg.enableSimpleBroker("/topic");
		reg.setApplicationDestinationPrefixes("/app");
	}
	@Override
	public void registerStompEndpoints(StompEndpointRegistry endpoint) {
		endpoint
			.addEndpoint("/notification")
			.setAllowedOrigins("*")
			.addInterceptors(new HttpSessionHandshakeInterceptor())
			.setHandshakeHandler(new WebSocketHandshakehandler())
			.withSockJS();
		endpoint.addEndpoint("/notification")
			.setAllowedOrigins("*")
			.addInterceptors(new HttpSessionHandshakeInterceptor())
			.setHandshakeHandler(new WebSocketHandshakehandler());
	}
}
