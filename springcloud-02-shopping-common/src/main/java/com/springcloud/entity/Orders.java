package com.springcloud.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * orders表对应的实体类，用于封装一行订单信息
 * 
 * @author 万娟
 *
 */
@Data
//添加无参的构造方法
@NoArgsConstructor
//添加全参的构造方法
@AllArgsConstructor
public class Orders implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9040866059091291450L;

	/**
	 * 订单编号
	 */
	private Integer orderId;

	/**
	 * 当前订单对应的用户信息
	 */
	private Users user;
	
	/**
	 * 用户编号
	 */
	private Integer userId;

	/**
	 * 收货人姓名，如果省略默认为用户表中的用户姓名
	 */
	private String receiverName;

	/**
	 * 收货人电话，如果省略默认为用户表中的联系电话
	 */
	private String receiverTel;

	/**
	 * 收货人地址，如果省略默认为用户表中的收货地址
	 */
	private String receiverAddr;

	/**
	 * 下单时间，默认为当前时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderTime;

	/**
	 * 订单总额
	 */
	private Double orderTotal;

	/**
	 * 订单状态：0为待付款，1为待发货，2为待收货，3为已付款，4为已退货
	 */
	private Integer orderStatus;

	/**
	 * 查询条件：订单起始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDateMin;

	/**
	 * 查询条件：订单终止时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDateMax;

	/**
	 * 查询条件：起始年月
	 */
	private String startMonth;

	/**
	 * 查询条件：终止年月
	 */
	private String endMonth;

	/**
	 * 统计结果的年月
	 */
	private String orderMonth;

	/**
	 * 统计结果的销售额
	 */
	private Double orderPrice;
	
	/**
	 * 用于保存当前订单中所有的订单明细信息
	 */
	private List<OrderDetails> orderDetailsList;
}