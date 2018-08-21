package com.cchcz.blog.service;

import com.cchcz.blog.model.entity.Comment;
import com.cchcz.blog.BaseJunitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author cchcz
 * @version 1.0
 * @date 2018/4/28 11:14
 * @since 1.0
 */
public class BizCommentServiceImplTest extends BaseJunitTest {

    @Autowired
    private BizCommentService commentService;

    @Test
    public void comment() throws InterruptedException {
        Comment comment = new Comment();
        comment.setPid(1L);
        comment.setNickname("测试");
        comment.setEmail("1098308189@qq.com");
        comment.setQq("1098308189");
        commentService.comment(comment);

        TimeUnit.SECONDS.sleep(60);
    }
}
