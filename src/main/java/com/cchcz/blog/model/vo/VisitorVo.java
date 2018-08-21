package com.cchcz.blog.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * <ClassName>VisitorVo</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月22日 10:34
 */
public class VisitorVo {
    private String name;
    private List<String> latLng;

    public String getName() {
        return name;
    }

    public VisitorVo setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getLatLng() {
        return latLng;
    }

    public VisitorVo setLatLng(List<String> latLng) {
        this.latLng = latLng;
        return this;
    }

    private VisitorVo() {
        latLng = new ArrayList<>();
    }

    public static VisitorVo create(String name, String lat, String lng) {
        VisitorVo visitorVo = new VisitorVo();
        visitorVo.setName(name);
        List<String> latLng = visitorVo.getLatLng();
        latLng.add(lat);
        latLng.add(lng);
        return visitorVo;
    }

}
