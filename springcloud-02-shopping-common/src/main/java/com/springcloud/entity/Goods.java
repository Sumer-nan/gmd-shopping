package com.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * goods表对应的实体类，用于保存表
 * 
 * @author 万娟
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
	/**
	 * 商品编号
	 */
	private Integer goodsId;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 商品价格
	 */
	private Double goodsPrice;

	/**
	 * 商品折扣价
	 */
	private Double goodsDiscount;

	/**
	 * 商品状态:0预售，1热卖中，1不是热卖
	 */
	private Integer goodsStatus;

	/**
	 * 商品数量
	 */
	private Integer goodsNum;

	/**
	 * 是否是新品:0是新品，1不是新品
	 */
	private Integer goodsNew;

	/**
	 * 商品热卖:0是热卖，1不是热卖
	 */
	private Integer goodsHot;

	/**
	 * 商品级别:0是一星，1是二星，2是三星，3是四星，4是五星(共5个级别)
	 */
	private Integer goodsLevel;

	/**
	 * 商品简介
	 */
	private String goodsDesc;

	/**
	 * 商品详情
	 */
	private String goodsDetail;

	/**
	 * 商品图片
	 */
	private String goodsImg;

	/**
	 * 二级类别编号
	 */
	private Integer typeTwoId;

	/**
	 * 查询条件，商品价格的下限
	 */
	private Double goodsPriceMin;

	/**
	 * 查询条件，商品价格的上限
	 */
	private Double goodsPriceMax;

	/**
	 * 查询条件，一级类别编号
	 */
	private Double typeOneId;

	/**
	 * 商品销售：用于统计分组的结果
	 */
	private Integer goodsSum;

}