
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.SysConfig;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    SysConfig get();

    Map<String, Object> getSiteInfo();
}
