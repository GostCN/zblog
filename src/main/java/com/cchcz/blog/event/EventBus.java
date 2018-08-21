package com.cchcz.blog.event;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * <ClassName>EventBus</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 15:57
 */
@Slf4j
public class EventBus {
    private ArrayBlockingQueue<Event> queue = new ArrayBlockingQueue(10000);
    private ConcurrentMap<Class<? extends Event>, List<EventConsumer>> consumers = new ConcurrentHashMap<>();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    private ExecutorService consumerThreadPool = Executors.newFixedThreadPool(2);

    public boolean post(Event event) {
        queue.add(event);
        return true;
    }

    public synchronized boolean register(EventConsumer consumer) {
        List<EventConsumer> eventConsumers = consumers.get(consumer.getType());
        if (eventConsumers == null) {
            eventConsumers = new ArrayList<>();
        }
        eventConsumers.add(consumer);
        consumers.put(consumer.getType(), eventConsumers);
        return true;
    }

    public static EventBus getInstance() {
        return SingleTonHolder.EVENT_BUS;
    }

    private EventBus() {
        fixedThreadPool.submit(() -> {
            while (true) {
                try {
                    Event event = queue.take();
                    Class<? extends Event> aClass = event.getClass();
                    List<EventConsumer> eventConsumers = consumers.get(aClass);
                    if (eventConsumers != null) {
                        Iterator<EventConsumer> iterator = eventConsumers.iterator();
                        while (iterator.hasNext()) {
                            EventConsumer eventConsumer = iterator.next();
                            if (eventConsumer.isDeprecated()) {
                                iterator.remove();
                                continue;
                            }
                            consumerThreadPool.submit(() -> {
                                eventConsumer.consume(event);
                            });
                        }
                    } else {
                        consumerThreadPool.submit(() -> {
                            DefaultEventConsumer.defaultOne().consume(event);
                        });
                    }
                } catch (Exception e) {
                    log.error("EventBus Error", e);
                }
            }
        });
    }

    private static class SingleTonHolder {
        private static final EventBus EVENT_BUS = new EventBus();
    }

}
