package com.springcloud.dao;

import com.springcloud.entity.OrderDetails;
import com.springcloud.entity.Orders;

import java.util.List;

public interface OrderDetailsMapper {
	int deleteByPrimaryKey(Integer orderDetailId);

	int insert(OrderDetails record);

	OrderDetails selectByPrimaryKey(Integer orderDetailId);

	List<OrderDetails> selectAll();

	int updateByPrimaryKey(OrderDetails record);

	/**
	 * 查询指定订单编号的订单明细信息
	 * 
	 * @param orderId 订单编号
	 * @return 成功返回java.util.List类型的实例，否则返回null
	 */
	public abstract List<OrderDetails> selectByOrderId(Integer orderId);
	
	/**
	 * 批量添加订单明细
	 * @param orders	一组订单明细
	 * @return	成功返回大于0的整数，否则返回小于等于0的整数
	 */
	public abstract Integer insertOrderDetails(Orders orders);
	
}