package com.cchcz.blog.event;

import lombok.extern.slf4j.Slf4j;

/**
 * <ClassName>VisitEventConsumer</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 16:08
 */
@Slf4j
public class DefaultEventConsumer implements EventConsumer<Event> {
    @Override
    public void consume(Event event) {
        log.info("默认消费：{}", event.toString());
    }

    @Override
    public Class<Event> getType() {
        return Event.class;
    }

    @Override
    public boolean isDeprecated() {
        return false;
    }


    public static DefaultEventConsumer defaultOne() {
        return SingleTonHolder.DEFAULT_EVENT_CONSUMER;
    }

    private DefaultEventConsumer() {
    }

    private static class SingleTonHolder {
        private static final DefaultEventConsumer DEFAULT_EVENT_CONSUMER = new DefaultEventConsumer();
    }
}
