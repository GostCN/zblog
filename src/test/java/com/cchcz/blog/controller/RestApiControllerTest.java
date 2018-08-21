
package com.cchcz.blog.controller;

import com.cchcz.blog.BaseJunitTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Api接口单元测试
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/25 14:37
 * @since 1.0
 */
public class RestApiControllerTest extends BaseJunitTest {

    @Test
    public void qq() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(host + "/api/qq/1098308189")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 打印出执行结果
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}