
package com.cchcz.blog.model.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {

    public Boolean enableKaptcha;

    public boolean getEnableKaptcha() {
        return null == enableKaptcha ? false : enableKaptcha;
    }

}
