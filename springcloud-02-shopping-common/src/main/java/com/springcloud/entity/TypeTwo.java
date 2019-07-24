package com.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * type_two表对应的实体类，用于保存表中一行二级类别信息
 * @author 万娟
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeTwo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9117495822231456760L;

	/**
	 * 二级类别编号
	 */
	private Integer typeTwoId;

	/**
	 * 二级类别名称
	 */
	private String typeTwoName;

	/**
	 * 一级类别编号
	 */
	private Integer typeOneId;

	/**
	 * 备注
	 */
	private String typeTwoRemark;

}