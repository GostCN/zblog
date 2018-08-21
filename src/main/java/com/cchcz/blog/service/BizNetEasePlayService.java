package com.cchcz.blog.service;

import com.cchcz.blog.model.entity.NetEasePlay;
import com.cchcz.blog.model.object.AbstractService;
import com.cchcz.blog.model.vo.NetEasePlayConditionVO;
import com.github.pagehelper.PageInfo;

public interface BizNetEasePlayService extends AbstractService<NetEasePlay, Integer> {

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<NetEasePlay> findPageBreakByCondition(NetEasePlayConditionVO vo);

    /**
     * 获取默认的歌单
     *
     * @return
     */
    NetEasePlay getDefault();
}
