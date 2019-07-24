package com.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizContent {

	private String out_trade_no;

	private String product_code;

	private Float total_amount;

	private String subject;

	//对象属性
	private SysServiceProvider extend_params;

}
