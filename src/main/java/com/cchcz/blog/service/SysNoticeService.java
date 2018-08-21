
package com.cchcz.blog.service;


import com.cchcz.blog.model.dto.SysNoticeDTO;
import com.cchcz.blog.model.entity.Notice;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.NoticeConditionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 系统通知
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface SysNoticeService extends AbstractService<Notice, Long> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<Notice> findPageBreakByCondition(NoticeConditionVO vo);

    /**
     * 获取已发布的通知列表
     *
     * @return
     */
    List<SysNoticeDTO> listRelease();
}
