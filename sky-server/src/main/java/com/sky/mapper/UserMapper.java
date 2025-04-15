package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("SELECT * FROM user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入用户，用户注册
     * @param user
     */
    void insert(User user);

    /**
    * 根据用户id查询用户
    * @param id
    * @return
    */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(Long id);

    /**
     * 根据动态条件查询用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}



