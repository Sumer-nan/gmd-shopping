package com.springcloud.entity;

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
public class OrderDetails implements java.io.Serializable {
	private static final long serialVersionUID = -6464103781453419950L;

	/**
	 * 订单明细编号
	 */
	private Integer orderDetailId;

	/**
	 * 订单编号
	 */
	private Integer orderId;

	/**
	 * 商品编号
	 */
	private Integer goodsId;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 成交价
	 */
	private Double goodsFinalPrice;

	/**
	 * 成交数量
	 */
	private Integer goodsFinalNum;

	/**
	 * 用于保存订单明细的商品信息
	 */
	public Goods goods;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetails other = (OrderDetails) obj;
		if (goodsId == null) {
			if (other.goodsId != null)
				return false;
		} else if (!goodsId.equals(other.goodsId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
		return result;
	}

}