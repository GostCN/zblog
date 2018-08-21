
package com.cchcz.blog.dao.mapper;

import com.cchcz.blog.dao.beans.BizArticleArchives;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Repository
public interface BizArticleArchivesMapper {

    List<BizArticleArchives> listArchives();
}
