package com.example.thiscode.domain.chat.handler;

import com.example.thiscode.security.common.CommonAuthenticationToken;
import com.example.thiscode.security.jwt.JwtSubject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (log.isDebugEnabled() && StompCommand.CONNECT.equals(accessor.getCommand())) {
            CommonAuthenticationToken token = (CommonAuthenticationToken) accessor.getUser();
            if (token == null) {
                throw new InsufficientAuthenticationException("Request is not authenticated");
            }
            JwtSubject jwtSubject = (JwtSubject) token.getPrincipal();
            Long userId = jwtSubject.getId();
            String nickname = jwtSubject.getNickname();
            log.info("CONNECT userId: {}, nickname: {}", userId, nickname);
            accessor.addNativeHeader("userId", String.valueOf(userId));
            accessor.addNativeHeader("nickname", nickname);
        }

            return message;
    }
}
