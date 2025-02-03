package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {
    /**
     * 套餐信息分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 保存套餐及其相应的菜品
     * @param setmealDTO
     */
    void saveSetmealWithDish(SetmealDTO setmealDTO);

    /**
     * 根据ids集合批量删除套餐
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根根据id查询套餐，用于修改套餐页面回显
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    void startOrStop(Integer status, Long id);

    /**
     * c端-查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品列表
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
