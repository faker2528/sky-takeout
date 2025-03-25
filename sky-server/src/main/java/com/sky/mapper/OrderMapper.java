package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    /**
     * 插入一条订单数据
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
    * 分页条件查询
    * @param ordersPageQueryDTO
    * @return
    */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
    * 根据订单id查询订单
    * @param id
    * @return
    */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 根据订单status查询订单数量
     * @param status
     * @return
     */
    @Select("SELECT COUNT(id) FROM orders WHERE status = #{status}")
    Integer countStatus(Integer status);
}
