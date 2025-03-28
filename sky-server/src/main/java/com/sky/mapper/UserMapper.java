package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.User;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    User getById(Long id);}
