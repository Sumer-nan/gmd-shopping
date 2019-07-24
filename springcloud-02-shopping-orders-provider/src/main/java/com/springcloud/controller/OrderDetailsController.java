package com.springcloud.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springcloud.common.PageUtils;
import com.springcloud.entity.OrderDetails;
import com.springcloud.service.OrderDetailsService;
import com.springcloud.vo.ResultValue;

/**
 * 订单明细模块的控制层
 * 
 * @author 万娟
 *
 */
@RestController
@RequestMapping("orderDetails")
public class OrderDetailsController {

	@Autowired
	private OrderDetailsService orderDetailsService;

	@RequestMapping(value = "/selectByOrderId")
	public ResultValue selectByOrderId(@RequestParam("orderId") Integer orderId,@RequestParam("pageNumber") Integer pageNumber) {
		ResultValue rv = new ResultValue();
		try {
			// 查询满足条件的订单明细信息
			PageInfo<OrderDetails> pageInfo = this.orderDetailsService.selectByOrderId(orderId, pageNumber);
			// 从分页信息中获得订单明细信息
			List<OrderDetails> list = pageInfo.getList();
			// 如果查询到了满足条件的订单明细信息
			if (list != null && list.size() > 0) {
				rv.setCode(0);
				Map<String, Object> map = new HashMap<>();
				// 将订单明细信息以指定的建存入Map集合中
				map.put("orderDetailsList", list);
				
				PageUtils pageUtils = new PageUtils(5,pageInfo.getPages() * 5);
				pageUtils.setPageNumber(pageNumber);
				// 将分页信息已指定的名字存入Map集合中
				map.put("pageUtils", pageUtils);
				rv.setDataMap(map);
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("没有找到满足条件的订单明细信息!");
		return rv;
	}
	
	@RequestMapping(value = "/addShopping")
	public ResultValue addShopping(@RequestParam("userId") Integer userId,OrderDetails orderDetails) {
		ResultValue rv = new ResultValue();
		try {
			boolean b = this.orderDetailsService.addShopping(userId, orderDetails);
			if(b) {
				rv.setCode(0);
				rv.setMessage("添加购物车成功！");
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("添加购物车失败！");
		return rv;
	}
	
	/**
	 * 获得指定编号用户的购物车
	 * @param userId	指定编号用户
	 * @return
	 */
	@RequestMapping(value = "/selectShopping")
	public ResultValue selectShopping(@RequestParam("userId") Integer userId) {
		ResultValue rv = new ResultValue();
		try {
			List<OrderDetails> list = this.orderDetailsService.selectShopping(userId);
			if(list != null) {
				rv.setCode(0);
				Map<String, Object> map = new HashMap<>();
				map.put("shoppingList", list);
				rv.setDataMap(map);
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("获取购物车信息失败！");
		return rv;
	}
	
	/**
	 * 移除购物车中指定的商品信息
	 * @param userId
	 * @param orderDetails
	 * @return
	 */
	@RequestMapping(value = "/removeShopping")
	public ResultValue removeShopping(@RequestParam("userId") Integer userId,OrderDetails orderDetails) {
		ResultValue rv = new ResultValue();
		try {
			boolean b = this.orderDetailsService.removeShopping(userId, orderDetails);
			if(b) {
				rv.setCode(0);
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("移除购物车中的商品失败！");
		return rv;
	}
}
