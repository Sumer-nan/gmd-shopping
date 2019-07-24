package com.springcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springcloud.dao.OrderDetailsMapper;
import com.springcloud.entity.OrderDetails;
import com.springcloud.service.OrderDetailsService;

/**
 * 订单明细模块模型层的实现类，用于实现订单明细模块的方法
 * 
 * @author 万娟
 *
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired // 反向注入
	private OrderDetailsMapper orderDetailsMapper;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public PageInfo<OrderDetails> selectByOrderId(Integer orderId, Integer pageNumber) {

		// 设置分页信息
		PageHelper.startPage(pageNumber + 1, 5);
		// 查询指定订单编号的的订单明细信息
		List<OrderDetails> list = this.orderDetailsMapper.selectByOrderId(orderId);
		return new PageInfo<>(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addShopping(Integer userId, OrderDetails orderDetails) {
		try {
			ListOperations<String, OrderDetails> opsForList = this.redisTemplate.opsForList();
			//创建Redis数据库中保存数据的键
			String key = "user" + userId;
			
			//向购物车中添加数据
			//1、获得指定键的list的长度（获得此用户购物车种订单明细的数量）
			Long size = opsForList.size(key);
			
			if(size == 0) {
				//当前用户的购物车为空，直接将订单明细存入list中即可
				opsForList.leftPush(key, orderDetails);
			}else{
				//当前用户的购物车不为空，判断购物车中是否已经存在新购买的商品订单明细
				//1、获得redis中指定的list中所有的数据
				List<OrderDetails> list = opsForList.range(key, 0, -1);
				//2、在list中查找新的订单明细是否存在(在list集合中查找订单明细首次出现的位置，未找到返回-1)
				int indexOf = list.indexOf(orderDetails);
				
				if(indexOf == -1) {
					//在购物车中没有找到新购买的订单明细，直接将新的订单明细添加到Redis中指定键的list中即可
					opsForList.leftPush(key, orderDetails);
				}else {
					//在购物车中找到了新购买的订单明细
					//获得Redis中指定键list的第N个元素
					OrderDetails o = opsForList.index(key, indexOf);
					Integer num1 = o.getGoodsFinalNum();
					Integer num2 = orderDetails.getGoodsFinalNum();
					
					//修改订单明细的购买数量
					o.setGoodsFinalNum(num1 + num2);
					//即那个修改后的订单明细重新放回Redis中指定键的list原来的位置
					opsForList.set(key, indexOf, o);
				}
			}
			
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDetails> selectShopping(Integer userId){
		ListOperations<String,OrderDetails> opsForList = this.redisTemplate.opsForList();
		String key = "user" + userId;
		return opsForList.range(key, 0, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean removeShopping(Integer userId, OrderDetails orderDetails) {
		ListOperations<String, OrderDetails> opsForList = this.redisTemplate.opsForList();
		//根据用户编号生成Redis中list集合对应的键
		String key = "user" + userId;
		
		//获得Redis中指定键的list集合的长度
		Long size = opsForList.size(key);
		
		//如果list中没有元素，结束方法
		if(size == 0) {
			return false;
		}
		
		//获得list中所有的元素
		List<OrderDetails> list = opsForList.range(key, 0, -1);
		//在list集合中查找参数首次出现的位置，没有找到返回-1
		int indexOf = list.indexOf(orderDetails);
		//如果在购物车中没有找到指定的元素，结束方法
		if(indexOf == -1) {
			return false;
		}
		
		//将商品的信息从list中移除
		list.remove(indexOf);
		
		//删除键
		this.redisTemplate.delete(key);
		
		//重新为用户创建一个list
		for(OrderDetails o : list) {
			opsForList.rightPush(key, o);
		}
		
		return true;
	}

}
