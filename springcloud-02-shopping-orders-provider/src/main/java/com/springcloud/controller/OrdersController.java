package com.springcloud.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springcloud.common.PageUtils;
import com.springcloud.entity.Orders;
import com.springcloud.service.OrdersService;
import com.springcloud.vo.ResultValue;

/**
 * 订单模块的控制层
 * 
 * @author 万娟
 *
 */
@RestController
@RequestMapping("orders")
public class OrdersController {

	@Autowired
	public OrdersService ordersService;

	/**
	 * 查询满足条件的订单信息
	 * 
	 * @param orders     查询条件
	 * @param pageNumber 页数
	 * @return
	 */
	@RequestMapping(value = "/selectOrders")
	public ResultValue selectOrders(Orders orders, @RequestParam("pageNumber") Integer pageNumber) {
		ResultValue rv = new ResultValue();
		try {
			// 查询满足条件的订单信息
			PageInfo<Orders> pageInfo = this.ordersService.selectOrders(orders, pageNumber);
			// 从分页信息中获得订单信息
			List<Orders> list = pageInfo.getList();
			// 如果查询到了满足条件的信息
			if (list != null && list.size() > 0) {
				rv.setCode(0);
				Map<String, Object> map = new HashMap<>();
				// 将订单信息以指定的建存入Map集合中
				map.put("ordersList", list);

				PageUtils pageUtils = new PageUtils(pageInfo.getPages() * PageUtils.PAGE_ROW_COUNT);
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
		rv.setMessage("订单信息查询失败!");
		return rv;
	}

	/**
	 * 修改指定订单编号的订单信息
	 * 
	 * @param orders 修改的订单信息
	 * @return
	 */
	@RequestMapping(value = "/updateOrdersStatus")
	public ResultValue updateOrdersStatus(Orders orders) {
		ResultValue rv = new ResultValue();

		try {
			// 调用service相应的方法查询订单信息，并保存添加的结果
			Integer status = this.ordersService.updateOrdersStatus(orders);
			// 如果添加成功
			if (status > 0) {
				// 设置结果的状态标记为0
				rv.setCode(0);
				rv.setMessage("订单状态修改成功!");
				// 返回ResultValue对象
				return rv;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("订单状态修改失败!");
		return rv;
	}

	/**
	 * 查询指定时间范围内的销售额
	 * 
	 * @param orders 查询条件
	 * @return
	 */
	@RequestMapping(value = "/selectGroup")
	public ResultValue selectGroup(Orders orders) {
		ResultValue rv = new ResultValue();
		try {
			List<Orders> list = this.ordersService.selectGroup(orders);
			if (list != null && list.size() > 0) {
				rv.setCode(0);
				List<String> x = new ArrayList<>();
				List<Double> y = new LinkedList<>();
				for (Orders o : list) {
					x.add(o.getOrderMonth());
					y.add(o.getOrderPrice());
				}
				Map<String, Object> map = new HashMap<>();
				map.put("x", x);
				map.put("y", y);
				rv.setDataMap(map);
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		return rv;
	}

	/**
	 * 添加订单预订单明细
	 * 
	 * @param orders 视图层传递的json字符串转换为实体类
	 * @return
	 */
	@RequestMapping(value = "/insert")
	// @RequestBody:将视图层传递的json字符串转化为实体类
	public ResultValue insert(@RequestBody Orders orders) {
		ResultValue rv = new ResultValue();
		orders.setOrderTime(new Date());
		// System.out.println(orders);
		try {
			boolean b = this.ordersService.insert(orders);
			if (b) {
				rv.setCode(0);
				Map<String, Object> map = new HashMap<>();
				map.put("orderId", orders.getOrderId());
				rv.setDataMap(map);
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("创建订单失败！");
		return rv;
	}

	/**
	 * 查询指定用户的订单信息
	 * 
	 * @param userId 用户编号
	 * @return
	 */
	@RequestMapping(value = "/selectByUserId")
	public ResultValue selectByUserId(@RequestParam("userId") Integer userId) {
		ResultValue rv = new ResultValue();
		try {
			List<Orders> list = this.ordersService.selectByUserId(userId);
			if (list != null) {
				rv.setCode(0);
				Map<String, Object> map = new HashMap<>();
				map.put("ordersList", list);
				rv.setDataMap(map);
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("获得订单信息失败！");
		return rv;
	}
}
