package com.cchcz.blog.event;

/**
 * <ClassName>EventProducer</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 15:58
 */
public interface EventProducer<T extends Event> {
    boolean post(T event);
}
