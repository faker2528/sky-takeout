package com.sky.service.impl;

import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private UserMapper userMapper;

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
			map.put("status", "5");
			Double turnover = orderMapper.getTurnoverByMap(map);
			turnover = turnover == null ? 0.0 : turnover;
			turnoverList.add(turnover);
		});

		return TurnoverReportVO.builder()
				.dateList(StringUtils.join(dateList, ","))
				.turnoverList(StringUtils.join(turnoverList, ","))
				.build();
	}

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

}
