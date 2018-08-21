
package com.cchcz.blog.service;


import com.cchcz.blog.model.entity.OSProject;
import com.cchcz.blog.model.vo.OSProjectConditionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 标签
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface BizOSProjectService {

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    OSProject insert(OSProject entity);

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    void insertList(List<OSProject> entities);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    boolean removeByPrimaryKey(Long primaryKey);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    boolean update(OSProject entity);

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    boolean updateSelective(OSProject entity);

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    OSProject getByPrimaryKey(Long primaryKey);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    OSProject getOneByEntity(OSProject entity);

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    List<OSProject> listAll();

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity
     * @return
     */
    List<OSProject> listByEntity(OSProject entity);

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    PageInfo<OSProject> findPageBreakByCondition(OSProjectConditionVO vo);
}
