
package com.cchcz.blog.service;


import com.cchcz.blog.model.entity.ArticleLove;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.ArticleLoveConditionVO;
import com.github.pagehelper.PageInfo;

/**
 * 文章点赞
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface BizArticleLoveService extends AbstractService<ArticleLove, Integer> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<ArticleLove> findPageBreakByCondition(ArticleLoveConditionVO vo);
}
