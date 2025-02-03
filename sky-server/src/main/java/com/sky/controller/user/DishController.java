package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "c端-菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 根据分类ID查询菜品及其口味
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("c端-根据分类ID查询菜品")
    public Result<List<DishVO>> list(Long categoryId){
        log.info("c端-根据分类ID查询菜品:{}",categoryId);
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE); // 查询起售中的菜品
        List<DishVO> list = dishService.listWithFlavor(dish);
        return Result.success(list);
    }
}
