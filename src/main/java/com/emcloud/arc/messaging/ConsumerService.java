package com.emcloud.arc.messaging;

import com.emcloud.message.event.AbstractMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ConsumerService {
    private final Logger log = LoggerFactory.getLogger(ConsumerService.class);

    @StreamListener(ConsumerChannel.CHANNEL)
    public void consume(AbstractMessageEvent messageEvent) {
        log.info("Received Ser type:{} action:{} message: {}.", messageEvent.getType(), messageEvent.getAction(), messageEvent.getMessage().toString());
    }
}
