package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.Product;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的product表对应
 */
public interface ProductDao {
    /**
     * 根据主键删除
     * @param id product的id
     * @return 返回删除的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入product的数据
     * @param record product的实体
     * @return 返回插入的行数
     */
    int insert(Product record);

    /**
     * 选择性的插入
     * @param record product的实体
     * @return 返回插入的行数
     */
    int insertSelective(Product record);

    /**
     * 根据id字段查找对应的product
     * @param id product的id
     * @return 返回product
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * 有选择性的更新product实体的数据
     * @param record product的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKeySelective(Product record);

    /*
        处理大数据的更新
        int updateByPrimaryKeyWithBLOBs(Product record);
     */

    /**
     * 更新product实体的数据
     * @param record product的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKey(Product record);
}