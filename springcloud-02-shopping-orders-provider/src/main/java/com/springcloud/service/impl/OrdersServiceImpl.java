package com.springcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springcloud.common.PageUtils;
import com.springcloud.dao.OrderDetailsMapper;
import com.springcloud.dao.OrdersMapper;
import com.springcloud.entity.OrderDetails;
import com.springcloud.entity.Orders;
import com.springcloud.service.OrdersService;

/**
 * 订单模块模型层的实现类，用于实现订单模块的方法
 * 
 * @author 万娟
 *
 */
@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private OrderDetailsMapper orderDetailsMapper;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public PageInfo<Orders> selectOrders(Orders orders, Integer pageNumber) {
		if (orders.getUser() != null) {
			// 商品名称两端加%
			orders.getUser().setUserName("%" + orders.getUser().getUserName() + "%");
		}
		// 设置每页信息
		PageHelper.startPage(pageNumber + 1, PageUtils.PAGE_ROW_COUNT);
		// 查询满足条件的商品信息,并接收结果
		List<Orders> list = this.ordersMapper.selectOrders(orders);
		return new PageInfo<>(list);
	}

	// 增删改需要@Transactional事务，查询时不需要
	@Transactional
	@Override
	public Integer updateOrdersStatus(Orders orders) {
		return this.ordersMapper.updateOrdersStatus(orders);
	}

	@Override
	public List<Orders> selectGroup(Orders orders) {
		return this.ordersMapper.selectGroup(orders);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public boolean insert(Orders orders) {
		// 添加订单信息
		int orderId = this.ordersMapper.insert(orders);
		// 如果订单信息添加成功，才能添加订单明细信息
		if (orderId > 0) {
			Integer orderDetails = this.orderDetailsMapper.insertOrderDetails(orders);
			if (orderDetails > 0) {
				// 将redis中指定用户购物车的信息删除
				String key = "user" + orders.getUserId();
				ListOperations<String, OrderDetails> opsForList = this.redisTemplate.opsForList();
				// 获得当前用户在Redis中所有的购物车信息
				List<OrderDetails> range = opsForList.range(key, 0, -1);
				// 获得当前用户需要结算的购物车信息(订单明细)
				List<OrderDetails> orderDetailsList = orders.getOrderDetailsList();

				if (range.size() == orderDetailsList.size()) {
					// 当两个集合的长度相等，表示用户对购物车中所有的商品进行了估算
					Boolean delete = this.redisTemplate.delete(key);
					if (delete) {
						return true;
					}
				} else {
					// 当两个集合的长度不相等时，从所有购物车中删除已结算的购物信息
					for (OrderDetails o : orderDetailsList) {
						int index = range.indexOf(o);
						if (index != -1) {
							range.remove(index);
						}
					}
					this.redisTemplate.delete(key);
					for (OrderDetails o1 : range) {
						opsForList.rightPush(key, o1);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<Orders> selectByUserId(Integer userId) {
		return this.ordersMapper.selectByUserId(userId);
	}
}
