package com.sky.service;

import com.sky.dto.DishDTO;
import org.springframework.stereotype.Service;

@Service
public interface DishService {

    /**
     * 新增菜品和相应的口味
     * @param dishDTO
     */
    public void savaWithFlavor(DishDTO dishDTO);
}
