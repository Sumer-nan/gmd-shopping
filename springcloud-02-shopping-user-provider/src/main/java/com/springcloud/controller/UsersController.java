package com.springcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springcloud.common.PageUtils;
import com.springcloud.entity.Users;
import com.springcloud.service.UsersService;
import com.springcloud.vo.ResultValue;

/**
 * 控制层：接收视图层的数据，并调用模型层相应的方法，将数据返回视图层中
 * 
 * @author 万娟
 *
 */
@RestController
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * 验证用户登录是否成功
	 * 
	 * @param userId       用户编号
	 * @param userPsw      用户密码
	 * @param permissionId 权限编号
	 * @return 成功返回登录用户的完整信息，失败返回null
	 */
	@RequestMapping(value = "/login")
	public ResultValue login(@RequestParam("userId") Integer userId, @RequestParam("userPsw") String userPsw,
			@RequestParam("permissionId") Integer permissionId) {
		ResultValue rv = new ResultValue();

		try {
			Users login = this.usersService.login(userId, userPsw, permissionId);
			if (login != null) {
				rv.setCode(0);

				Map<String, Object> map = new HashMap<>();
				map.put("loginUser", login);
				rv.setDataMap(map);

				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("登录信息不正确，请重新输入!");
		return rv;
	}

	/**
	 * 添加新的用户信息
	 * 
	 * @param users 新用户信息
	 * @return 添加成功返回com.springcloud.entity.Users类型的实例，否则返回null
	 */
	@RequestMapping(value = "/insert")
	public ResultValue insert(Users users) {
		ResultValue rv = new ResultValue();

		try {
			Users insert = this.usersService.insert(users);
			if (insert != null) {
				rv.setCode(0);
				rv.setMessage("录入用户信息成功!");
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		rv.setCode(1);
		rv.setMessage("录入用户失败!");
		return rv;
	}

	/**
	 * 查找满足条件的用户信息
	 * 
	 * @param users      查询条件
	 * @param pageNumber 查询页数
	 * @return 添加成功返回org.springframework.data.domain.Page类型的实例，否则返回null
	 */
	@RequestMapping(value = "select")
	public ResultValue select(Users users, @RequestParam("pageNumber") Integer pageNumber) {
		ResultValue rv = new ResultValue();

		try {
			Page<Users> page = this.usersService.select(users, pageNumber);
			// 获得分页的数据
			List<Users> list = page.getContent();
			// 判断是否查询到了数据
			if (list != null && list.size() > 0) {
				rv.setCode(0);

				Map<String, Object> map = new HashMap<>();
				// 将分页的数据添加到Map中
				map.put("userList", list);

				PageUtils pageUtils = new PageUtils((int) page.getTotalElements());
				pageUtils.setPageNumber(pageNumber);
				// 将分页信息添加到Map中
				map.put("pageUtils", pageUtils);

				// 将Map添加到ResultValue对象中
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
	 * 修改指定编号的用户状态
	 * 
	 * @param userId     用户编号
	 * @param userStatus 用户状态
	 * @return 修改成返回大于0的整数，否则返回0
	 */
	@RequestMapping(value = "/updateStatus")
	public ResultValue updateStatus(@RequestParam("userId") Integer userId,
			@RequestParam("userStatus") Integer userStatus) {
		ResultValue rv = new ResultValue();
		try {
			Integer status = this.usersService.updateStatus(userId, userStatus);
			if (status > 0) {
				rv.setCode(0);
				rv.setMessage("用户状态修改成功!");
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		rv.setCode(1);
		rv.setMessage("用户状态修改失败!");
		return rv;
	}

	/**
	 * 查询指定编号的用户信息
	 * 
	 * @param userId 用户编号
	 * @return 成功返回com.springcloud.entity.Users类型的实例，否则返回null
	 */
	@RequestMapping(value = "/select/{userId}")
	public ResultValue selectById(@PathVariable("userId") Integer userId) {
		ResultValue rv = new ResultValue();
		try {
			// 调用service中的方法，并获得查询的结果
			Users users = this.usersService.selectById(userId);
			// 如果成功
			if (users != null) {
				// 设置结果的状态为0
				rv.setCode(0);
				// 创建Map集合保存查询结果
				Map<String, Object> map = new HashMap<>();
				// 将查询结果保存到Map集合中
				map.put("users", users);
				// 将Map集合添加到ResultValue对象中
				rv.setDataMap(map);
				// 返回ResultValue对象
				return rv;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("获取用户信息失败!");
		return rv;
	}

	/**
	 * 修改指定编号的用户信息
	 * 
	 * @param users 修改用户的信息
	 * @return 修改成功返回大于0的整数，否则返回0
	 */
	@RequestMapping(value = "/update")
	public ResultValue update(Users users) {
		ResultValue rv = new ResultValue();
		try {
			// 调用service中相应的方法修改用户信息，并获得修改是否成功
			Integer update = this.usersService.update(users);
			// 如果修改成功
			if (update > 0) {
				// 设置结果的状态为0
				rv.setCode(0);
				// 返回ResultValue的对象
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("用户信息修改失败!");
		return rv;
	}

	/**
	 * 根据参数修改指定编号的用户头像或密码或昵称
	 * 
	 * @param users 修改用户的信息
	 * @return 修改成功返回大于0的整数，否则返回0
	 */
	@RequestMapping(value = "/updateMessage")
	public ResultValue updateMessage(Users users) {
		ResultValue rv = new ResultValue();
		try {
			Integer message = this.usersService.updateMessage(users);
			if (message > 0) {
				rv.setCode(0);
				rv.setMessage("用户信息修改成功!");
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("用户信息修改失败!");
		return rv;
	}

	/**
	 * 判断用户名出现的次数
	 * 
	 * @param userName 需要判断出现次数的用户名
	 * @return 返回java.lang.Long类型的实例
	 */
	@RequestMapping(value = "/countByUserName")
	public ResultValue countByUserName(@RequestParam("userName") String userName) {
		ResultValue rv = new ResultValue();
		try {
			Long countByUserName = this.usersService.countByUserName(userName);
			rv.setCode(0);
			Map<String, Object> map = new HashMap<>();
			map.put("count", countByUserName);

			rv.setDataMap(map);
			return rv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("服务器正忙，请稍后再试！");
		return rv;
	}

	/**
	 * 普通用户登录的方法
	 * 
	 * @param userName     用户名
	 * @param userPsw      用户密码
	 * @param userStatus   用户状态
	 * @param permissionId 用户权限
	 * @return 成功返回com.springcloud.entity.Users类型的实例，否则返回null
	 */
	@RequestMapping(value = "/userLogin")
	public ResultValue userLogin(@RequestParam String userName, @RequestParam String userPsw,
			@RequestParam Integer userStatus, @RequestParam Integer permissionId) {
		ResultValue rv = new ResultValue();
		try {
			Users login = this.usersService.userLogin(userName, userPsw, userStatus, permissionId);
			if (login != null) {
				rv.setCode(0);
				Map<String, Object> map = new HashMap<>();
				map.put("userMessage", login);
				rv.setDataMap(map);
				return rv;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("登录信息不正确，请重新输入！");
		return rv;
	}
	
	/**
	 * 查询指定编号用户的邮箱
	 * @param users	用户编号
	 * @return
	 */
	@RequestMapping(value = "/selectEmail")
	public ResultValue selectEmail(Users users) {
		ResultValue rv = new ResultValue();
		try {
			//根据用户编号获得用户信息
			Users id = this.usersService.selectById(users);
			//获得当前用户的邮箱
			String userEmail = id.getUserEmail();
			
			MimeMessage message = this.javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			
			//设置发送邮件的邮箱地址
			helper.setFrom("1105673695@qq.com");
			//设置接收邮件的邮箱id
			helper.setTo(userEmail);
			//设置主题
			helper.setSubject("重置密码");
			//设置正文
			StringBuilder emailMsg = new StringBuilder();
			emailMsg.append("<p> " + id.getUserName() + " ,您好:</p>");
			emailMsg.append("<p>&nbsp;&nbsp;&nbsp;请点击此url重置您的密码:");
			emailMsg.append("<a href=\"http://127.0.0.1:8020/demo/resetPassword.html?"+id.getUserId()+"\">http://127.0.0.1:8020/demo/resetPassword.html</a></p>");
			helper.setText(emailMsg.toString(),true);
			//发送附件
			FileSystemResource file = new FileSystemResource("C:/Users/Administrator/Desktop/图片/m.jpg");
			helper.addAttachment("测试附件.jpg", file);
			//发送邮件
			this.javaMailSender.send(message);
			
			rv.setCode(0);
			return rv;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setCode(1);
		rv.setMessage("您输入的邮箱不正确，请重新输入！");
		return rv;
	}
}
