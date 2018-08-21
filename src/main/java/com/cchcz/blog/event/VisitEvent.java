package com.cchcz.blog.event;

/**
 * <ClassName>VisitEvent</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 16:00
 */
public class VisitEvent extends Event {
    private String ip;

    private VisitEvent(String ip) {
        this.ip = ip;
        super.setMessage("用户访问");
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static VisitEvent create(String ip) {
        return new VisitEvent(ip);
    }
}
