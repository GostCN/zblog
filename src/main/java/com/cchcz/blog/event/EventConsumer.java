package com.cchcz.blog.event;

/**
 * <ClassName>EventConsumer</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 16:07
 */
public interface EventConsumer<T extends Event> {
    void consume(T event);

    Class<T> getType();

    boolean isDeprecated();
}
