package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;

	/**
	 * 根据时间计算营业额
	 * @param begin
	 * @param end
	 * @return
	 */
	@Override
	public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
		List<LocalDate> dateList = new ArrayList<>(); // 存放从begin到end的每天的日期
		dateList.add(begin);
		while (!begin.equals(end)) {
			begin = begin.plusDays(1);
			dateList.add(begin);
		}

		List<Double> turnoverList = new ArrayList<>();
		dateList.forEach(date->{
			LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
			LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
			Map map = new HashMap();
			map.put("begin", beginTime);
			map.put("end", endTime);
			map.put("status", Orders.COMPLETED);
			Double turnover = orderMapper.getTurnoverByMap(map);
			turnover = turnover == null ? 0.0 : turnover;
			turnoverList.add(turnover);
		});

		return TurnoverReportVO.builder()
				.dateList(StringUtils.join(dateList, ","))
				.turnoverList(StringUtils.join(turnoverList, ","))
				.build();
	}

	/**
	 * 用户数量统计
	 * @param begin
	 * @param end
	 * @return
	 */
	@Override
	public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
		List<LocalDate> dateList = new ArrayList<>(); // 存放从begin到end的每天的日期
		dateList.add(begin);
		while (!begin.equals(end)) {
			begin = begin.plusDays(1);
			dateList.add(begin);
		}
		// 存放每天的新增用户数量
		List<Integer> newUserList = new ArrayList<>();
		// 存放每天的总用户数量
		List<Integer> totalUserList = new ArrayList<>();

		dateList.forEach(date->{
			LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
			LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
			Map map = new HashMap();
			map.put("end", endTime);

			// 先查用户总数（只需要一个end参数）
			Integer totalUserNum = userMapper.countByMap(map);
			totalUserNum = totalUserNum == null ? 0 : totalUserNum;
			totalUserList.add(totalUserNum);

			map.put("begin", beginTime);

			// 再查新增用户数量
			Integer newUserNum = userMapper.countByMap(map);
			newUserNum = newUserNum == null ? 0 : newUserNum;
			newUserList.add(newUserNum);
		});


		return UserReportVO.builder()
				.dateList(StringUtils.join(dateList, ","))
				.newUserList(StringUtils.join(newUserList, ","))
				.totalUserList(StringUtils.join(totalUserList, ","))
				.build();
	}

	/**
	 * 订单相关数据统计
	 * @param begin
	 * @param end
	 * @return
	 */
	@Override
	public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
		// 生成日期列表
		List<LocalDate> dateList = begin.datesUntil(end.plusDays(1))
										.collect(Collectors.toList());

		// 存放每日订单数和没日有效订单数
		List<Integer> orderCountList = new ArrayList<>();
		List<Integer> validOrderCountList = new ArrayList<>();
		dateList.forEach(date->{
			Map map = new HashMap();
			LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
			LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

			map.put("begin", beginTime);
			map.put("end", endTime);

			// 获取当日订单总数
			Integer orderCount = orderMapper.countByMap(map);
			orderCountList.add(orderCount == null ? 0 : orderCount);

			map.put("status", Orders.COMPLETED);

			// 获取当日有效订单总数
			Integer validCount = orderMapper.countByMap(map);
			validOrderCountList.add(validCount == null ? 0 : validCount);
		});



		// 获取总订单数和有效订单数
		Integer totalOrderCount = orderCountList.stream().mapToInt(Integer::intValue).sum();

		Integer validOrderCount = validOrderCountList.stream().mapToInt(Integer::intValue).sum();

		// 计算订单完成率
		Double orderCompletionRate = totalOrderCount == 0 ? 0.0 : validOrderCount.doubleValue() / totalOrderCount.doubleValue();


		return OrderReportVO.builder()
				.dateList(StringUtils.join(dateList, ","))
				.totalOrderCount(totalOrderCount)
				.validOrderCount(validOrderCount)
				.orderCompletionRate(orderCompletionRate)
				.orderCountList(StringUtils.join(orderCountList, ","))
				.validOrderCountList(StringUtils.join(validOrderCountList, ","))
				.build();
	}

	/**
	 * 统计时间段内的销量前十
	 * @param begin
	 * @param end
	 * @return
	 */
	@Override
	public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {

		LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
		LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

		Map map = new HashMap<>();
		map.put("begin", beginTime);
		map.put("end", endTime);

		List<GoodsSalesDTO> topList = orderDetailMapper.getSalesTop(map);
		List<String > nameList = topList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
		List<Integer> numberList = topList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

		return SalesTop10ReportVO.builder()
				.nameList(StringUtils.join(nameList, ","))
				.numberList(StringUtils.join(numberList, ","))
				.build();
	}

}
