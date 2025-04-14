package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
	@Autowired
	private OrderMapper orderMapper;
	/**
	 * 定时处理超时订单
	 */
	@Scheduled(cron = "0 * * * * ?")
	public void processTimeoutOrder() {
		log.info("定时处理超时订单:{}", LocalDateTime.now());

		// 当前时间 - 15 分钟
		LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
		List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
		if (!ordersList.isEmpty()) {
			ordersList.forEach(order -> {
				order.setStatus(Orders.CANCELLED);
				order.setCancelReason("订单超时，自动取消！");
				order.setCancelTime(LocalDateTime.now());
				orderMapper.update(order);
			});
		}
	}

	@Scheduled(cron = "0 0 1 * * ?")    // 每天凌晨1点
	public void processDeliveryOrder() {
		log.info("定时处理处于派送中的订单:{}", LocalDateTime.now());
		LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
		List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);
		if (!ordersList.isEmpty()) {
			ordersList.forEach(order -> {
				order.setStatus(Orders.COMPLETED);
				orderMapper.update(order);
			});
		}
	}
}
