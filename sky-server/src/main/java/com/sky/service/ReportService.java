package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ReportService {

	/**
	 * 根据时间计算营业额
	 * @param begin
	 * @param end
	 * @return
	 */
	TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

	/**
	 * 用户数量统计
	 * @param begin
	 * @param end
	 * @return
	 */
	UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

	/**
	 * 订单相关数据统计
	 * @param begin
	 * @param end
	 * @return
	 */
	OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

	/**
	 * 统计时间段内的销量前十
	 * @param begin
	 * @param end
	 * @return
	 */
	SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}
