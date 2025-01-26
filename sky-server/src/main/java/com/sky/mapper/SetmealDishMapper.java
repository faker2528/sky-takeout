package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdByDishIds(List<Long> dishIds);

    /**
     * 批量插入套餐所对应的菜品数据
     * @param setmealDishes
     */
    @AutoFill(OperationType.INSERT)
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据setmealIds集合批量删除菜品与套餐的关联关系
     * @param setmealIds
     */
    void deleteBySetmealIds(List<Long> setmealIds);

    /**
     * 根据setmealId获取setmeal_dish数据，查询套餐和菜品的关联关系
     * @param setmealId
     * @return
     */
    @Select("SELECT * FROM setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);

    /**
     * 根据套餐id删除菜品与套餐的关联关系
     * @param setmealId
     */
    @Delete("DELETE FROM setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);
}
