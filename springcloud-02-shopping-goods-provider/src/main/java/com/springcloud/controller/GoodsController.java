package com.springcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.springcloud.common.PageUtils;
import com.springcloud.entity.Goods;
import com.springcloud.service.GoodsService;
import com.springcloud.vo.ResultValue;

/**
 * 商品控制层
 * 
 * @author 万娟
 *
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	/**
	 * 添加新商品信息
	 * 
	 * @param goods 新商品信息
	 * @return
	 */
	@RequestMapping(value = "/insert")
	public ResultValue insert(Goods goods) {
		ResultValue rv = new ResultValue();

		try {
			// 调用service相应的方法查询商品信息，并保存添加的结果
			Integer insert = this.goodsService.insert(goods);
			// 如果添加成功
			if (insert > 0) {
				// 设置结果的状态标记为0
				rv.setCode(0);
				rv.setMessage("商品信息录入成功!");
				// 返回ResultValue对象
				return rv;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		rv.setCode(1);
		rv.setMessage("商品信息录入失败!");
		return rv;
	}

	/**
	 * 查询满足条件的商品信息
	 * 
	 * @param goods      查询条件
	 * @param pageNumber 页数
	 * @return
	 */
	@RequestMapping(value = "/select")
	public ResultValue select(Goods goods, @RequestParam("pageNumber") Integer pageNumber) {
		ResultValue rv = new ResultValue();

		try {
			// 查询满足条件的商品信息
			PageInfo<Goods> pageInfo = this.goodsService.select(goods, pageNumber);
			// 从分页信息中获得商品信息
			List<Goods> list = pageInfo.getList();
			// 如果查询到了满足条件的信息
			if (list != null && list.size() > 0) {
				rv.setCode(0);

				Map<String, Object> map = new HashMap<>();
				// 将商品信息以指定的建存入Map集合中
				map.put("goodsList", list);

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
		rv.setMessage("没有找到满足条件的商品信息!");
		return rv;
	}

	/**
	 * 根据条件修改商品信息
	 * 
	 * @param goods 修改商品信息
	 * @return
	 */
	@RequestMapping(value = "/updateById")
	public ResultValue updateById(Goods goods) {
		ResultValue rv = new ResultValue();

		try {
			// 调用service相应的方法查询商品信息，并保存添加的结果
			Integer update = this.goodsService.updateGoodsById(goods);
			// 如果添加成功
			if (update > 0) {
				// 设置结果的状态标记为0
				rv.setCode(0);
				rv.setMessage("商品信息修改成功!");
				// 返回ResultValue对象
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		rv.setCode(1);
		rv.setMessage("商品信息修改失败!");
		return rv;
	}

	/**
	 * 修改商品指定编号的商品信息
	 * 
	 * @param goods 修改的商品信息
	 * @return
	 */
	@RequestMapping(value = "/update")
	public ResultValue update(Goods goods) {
		ResultValue rv = new ResultValue();

		try {
			// 调用service相应的方法查询商品信息，并保存添加的结果
			Integer update = this.goodsService.update(goods);
			// 如果添加成功
			if (update > 0) {
				// 设置结果的状态标记为0
				rv.setCode(0);
				rv.setMessage("修改商品信息成功!");
				// 返回ResultValue对象
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		rv.setCode(1);
		rv.setMessage("修改商品信息失败!");
		return rv;
	}

	/**
	 * 查询销量前10的商品信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectGroup")
	public ResultValue selectGroup() {
		ResultValue rv = new ResultValue();
		try {
			List<Goods> list = this.goodsService.selectGroup();
			if (list != null && list.size() > 0) {
				rv.setCode(0);
				// 创建两个集合用于保存柱状图x轴于y轴的数据
				List<String> x = new ArrayList<>();
				List<Integer> y = new ArrayList<>();
				// 将查询结果种商品名称存入x集合，销量存入y集合
				for (Goods goods : list) {
					x.add(goods.getGoodsName());
					y.add(goods.getGoodsSum());
				}
				// 创建一个Map
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
	 * 获得index页面初始化的商品信息（目前仅有热卖商品与新品两组商品信息）
	 * @return
	 */
	@RequestMapping(value = "/indexGoodsMessage")
	public ResultValue indexGoodsMessage() {
		ResultValue rv = new ResultValue();
		try {
			List<Goods> hot = this.goodsService.selectGoodsHot();
			List<Goods> new1 = this.goodsService.selectGoodsNew();
			if((hot != null && hot.size() > 0) && (new1 != null && new1.size() > 0)){
				rv.setCode(0);
				
				Map<String, Object> map = new HashMap<>();
				map.put("goodsHotList", hot);
				map.put("goodsNewList", new1);
				rv.setDataMap(map);
				
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("");
		return rv;
	}
}
