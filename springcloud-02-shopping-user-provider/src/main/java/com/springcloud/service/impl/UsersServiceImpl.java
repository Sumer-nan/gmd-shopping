package com.springcloud.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springcloud.common.PageUtils;
import com.springcloud.entity.Users;
import com.springcloud.repository.UsersRepository;
import com.springcloud.service.UsersService;

/**
 * 模型层的实现类：实现用户模块的方法
 * 
 * @author 万娟
 *
 */
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public Users login(Integer userId, String userPsw, Integer permissionId) {
		return this.usersRepository.login(userId, userPsw, permissionId);
	}

	@Transactional
	@Override
	public Users insert(Users users) {
		return this.usersRepository.save(users);
	}

	@Override
	public Page<Users> select(Users users, Integer pageNumber) {
		// 根据查询数据，创建动态条件
		@SuppressWarnings("serial")
		Specification<Users> specificatio = new Specification<Users>() {

			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> whereList = new ArrayList<>();

				// 根据Users中的查询数据，动态创建查询条件
				if (users.getUserName() != null && !users.getUserName().trim().equals("")) {
					whereList.add(criteriaBuilder.like(root.get("userName"), "%" + users.getUserName() + "%"));
				}

				if (users.getUserStatus() != -1) {
					whereList.add(criteriaBuilder.equal(root.get("userStatus"), users.getUserStatus()));
				}

				// 将所有的条件以and关系连接在一起，并返回
				return criteriaBuilder.and(whereList.toArray(new Predicate[whereList.size()]));
			}
		};
		// 创建jpa的分页信息
		PageRequest of = PageRequest.of(pageNumber, PageUtils.PAGE_ROW_COUNT);

		return this.usersRepository.findAll(specificatio, of);
	}

	// 增删改均需要事务@Transactional
	@Transactional
	@Override
	public Integer updateStatus(Integer userId, Integer userStatus) {
		return this.usersRepository.updateStatus(userId, userStatus);
	}

	@Transactional
	@Override
	public Users selectById(Integer userId) {
		return this.usersRepository.findById(userId).get();
	}

	@Transactional
	@Override
	public Integer update(Users users) {
		return this.usersRepository.update(users);
	}

	@Transactional
	@Override
	public Integer updateMessage(Users users) {
		if (users.getUserImage() != null && !users.getUserImage().trim().equals("")) {
			return this.usersRepository.updateImage(users);
		} else if (users.getUserPsw() != null && !users.getUserPsw().trim().equals("")) {
			return this.usersRepository.updatePassword(users);
		} else if (users.getUserName() != null && !users.getUserName().trim().equals("")) {
			return this.usersRepository.updateName(users);
		}
		return 0;
	}

	@Override
	public Long countByUserName(String userName) {
		return this.usersRepository.countByUserName(userName);
	}

	@Override
	public Users userLogin(String userName, String userPsw, Integer userStatus, Integer permissionId) {
		return this.usersRepository.findByUserNameAndUserPswAndUserStatusAndPermissionId(userName, userPsw, userStatus,
				permissionId);
	}

	@Override
	public Users selectById(Users users) {
		return this.usersRepository.getOne(users.getUserId());
	}
}
