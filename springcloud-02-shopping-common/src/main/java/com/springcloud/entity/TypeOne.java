package com.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * type_one表对应的实体类，用于保存表中一行一级类别信息
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeOne implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2690542590151848525L;

	/**
	 * 一级类别编号
	 */
	private Integer typeOneId;

	/**
	 * 二级类名别称
	 */
	private String typeOneName;

	/**
	 * 序号
	 */
	private Integer typeOneNum;

	/**
	 * 备注
	 */
	private String typeOneRemark;

}