package com.cchcz.blog.service;

import com.cchcz.blog.dao.beans.SysAttach;
import com.cchcz.blog.model.vo.SysAttachConditionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by wangq on 2017/3/20.
 */
public interface SysAttachService {
    /**
     * 分页查询附件
     *
     * @return
     */
    PageInfo<SysAttach> findPageBreakByCondition(SysAttachConditionVO vo);

    /**
     * 保存附件
     *
     * @param fname
     * @param fkey
     * @param ftype
     * @param author
     */
    void save(String fname, String fkey, String ftype, Integer author);

    /**
     * 根据附件id查询附件
     *
     * @param id
     * @return
     */
    SysAttach selectById(Integer id);

    /**
     * 删除附件
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 获取素材库
     *
     * @return
     */
    List<String> listMaterial();

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    boolean updateSelective(SysAttach entity);
}
