
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.BaseMapper;
import com.cchcz.blog.dao.beans.BizArticle;
import com.cchcz.blog.model.vo.ArticleConditionVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface BizArticleMapper extends BaseMapper<BizArticle> {

    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param vo
     * @return
     */
    List<BizArticle> findPageBreakByCondition(ArticleConditionVO vo);

    /**
     * 统计指定文章的标签集合
     *
     * @param list
     * @return
     */
    List<BizArticle> listTagsByArticleId(List<Long> list);

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    List<BizArticle> queryByIds(List<Long> ids);

    /**
     * 获取文章详情，关联查询文章标签、文章类型
     *
     * @param id
     * @return
     */
    BizArticle get(Long id);

    /**
     * 获取上一篇和下一篇(是否可以通过get时查出来？ BizArticle中关联两个BizArticle：prev & next)
     *
     * @param insertTime
     * @return
     */
    List<BizArticle> getPrevAndNextArticles(Date insertTime);

    /**
     * 获取热门文章
     *
     * @return
     */
    List<BizArticle> listHotArticle();

    /**
     * 是否存在文章
     *
     * @param id
     * @return
     */
    Integer isExist(Long id);

    /**
     * 获取封面图片素材
     *
     * @return
     */
    List<String> listMaterial();

}
