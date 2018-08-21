package com.cchcz.blog.event;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * <ClassName>Event</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 15:58
 */
public class Event {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
