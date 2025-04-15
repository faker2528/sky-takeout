package com.sky.service;

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
}
