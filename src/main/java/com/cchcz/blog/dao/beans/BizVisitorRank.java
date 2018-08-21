package com.cchcz.blog.dao.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Id;

/**
 * created by zhangcheng 2018/8/5
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizVisitorRank {
    @Id
    private long id;
    private String continent;
    private String country;
    private String provice;
    private String city;
    private long num;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
