package com.springcloud.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.springcloud.entity.Orders;

/**
 * 订单模块模型层接口，用于定义单模型的方法
 * 
 * @author 万娟
 *
 */
public interface OrdersService {

	/**
	 * 查询满足条件的订单信息（分页功能）
	 * 
	 * @param orders     查询条件
	 * @param pageNumber 页数
	 * @return 成功返回com.github.pagehelper.PageInfo<Oders>类型的实例
	 */
	public abstract PageInfo<Orders> selectOrders(Orders orders, Integer pageNumber);

	/**
	 * 修改指定订单编号的订单状态
	 * 
	 * @param orders 修改的订单信息
	 * @return 成功返回大于0的整数，否则返回小于等于0的整数
	 */
	public abstract Integer updateOrdersStatus(Orders orders);

	/**
	 * 查询指定日期范围内的销售额
	 * 
	 * @param orders 查询条件
	 * @return 成功返回java.util.List类型的实例，否则返回null
	 */
	public abstract List<Orders> selectGroup(Orders orders);

	/**
	 * 添加订单以及订单明细的数据
	 * 
	 * @param orders 订单与订单明细的信息
	 * @return 成功返回true，否则返回false
	 */
	public abstract boolean insert(Orders orders);

	/**
	 * 查询指定用户所有的订单信息
	 * 
	 * @param userId 订单编号
	 * @return 成功返回java.util.List类型的实例，否则返回null
	 */
	public abstract List<Orders> selectByUserId(Integer userId);

}
