package com.cchcz.blog.event;

import org.springframework.stereotype.Component;

/**
 * <ClassName>VisitEventProducer</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 16:10
 */
@Component
public class VisitEventProducer implements EventProducer<VisitEvent> {
    @Override
    public boolean post(VisitEvent event) {
        EventBus.getInstance().post(event);
        return true;
    }
}
