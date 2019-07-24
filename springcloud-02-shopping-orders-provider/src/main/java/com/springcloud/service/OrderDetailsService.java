package com.springcloud.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.springcloud.entity.OrderDetails;

/**
 * 订单明细模块的模型层的接口，用于定义订单明细模块的方法
 * 
 * @author 万娟
 *
 */
public interface OrderDetailsService {

	/**
	 * 查询指定订单编号的订单明细信息
	 * 
	 * @param orderId    订单编号
	 * @param pageNumber 页数
	 * @return 返回com.github.pagehelper.PageInfo<OrderDetails>类型的实例
	 */
	public abstract PageInfo<OrderDetails> selectByOrderId(Integer orderId, Integer pageNumber);

	/**
	 * 向购物车中添加订单的明细信息
	 * 
	 * @param userId       用户编号
	 * @param orderDetails 订单明细
	 * @return 成功返回rtrue，否则返回false
	 */
	public abstract boolean addShopping(Integer userId, OrderDetails orderDetails);

	/**
	 * 获得指定用户的购物车信息
	 * 
	 * @param userId 用户编号
	 * @return 成功返回java.util.List类型的实例，否则返回null
	 */
	public abstract List<OrderDetails> selectShopping(Integer userId);

	/**
	 * 删除指定用户购物车中的商品信息
	 * 
	 * @param userId       用户编号
	 * @param orderDetails 商品编号
	 * @return
	 */
	public abstract boolean removeShopping(Integer userId, OrderDetails orderDetails);
}
