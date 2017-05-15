package com.github.fanfever.fever.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;


/**
 * 身份校验核心类
 * 
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		return null;
	}

//	/**
//	 * 认证回调函数, 登录时调用.
//	 */
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//
//		// User user = userQueryMapper.findByUsername(token.getUsername());
////		User user = UserManager.getByUsername(token.getUsername());
//		if (user != null) {
//			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
//			return new SimpleAuthenticationInfo(new Principal(user), user.getPassword().substring(0, 16), ByteSource.Util.bytes(salt), getName());
//		} else {
//			return null;
//		}
//	}
//
//	/**
//	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
//	 */
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		Principal principal = (Principal) getAvailablePrincipal(principals);
//		// User user = userQueryMapper.findByUsername(principal.getUsername());
//		User user = UserManager.getByUsername(principal.getUsername());
//		if (null != user) {
//			SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//			// 添加基于Permission的权限信息
//			// List<Menu> menuList = menuQueryMapper.findByUserId(user.getId());
//			List<Menu> menuList = UserManager.getMenuList();
//			List<String> permissionList = menuList.stream().filter(i -> StringUtils.isNotBlank(i.getPermission()))
//					.flatMap(i -> Arrays.asList(StringUtils.split(i.getPermission(), ",")).stream()).collect(Collectors.toList());
//			simpleAuthorizationInfo.addStringPermissions(permissionList);
//			// 添加用户权限
//			simpleAuthorizationInfo.addStringPermission("user");
//			// 添加用户角色信息
//			// List<Role> roleList = roleQueryMapper.findByUser(user);
//			List<Role> roleList = UserManager.getRoleList();
//			simpleAuthorizationInfo.addRoles(roleList.stream().map(Role::getName).collect(Collectors.toList()));
//			return simpleAuthorizationInfo;
//		} else {
//			return null;
//			// return 401;
//		}
//	}
//
//	/**
//	 * 授权用户信息
//	 */
//	public static class Principal implements Serializable {
//
//		private static final long serialVersionUID = 1L;
//
//		private Integer id;
//		private String username;
//
//		public Principal(User user) {
//			this.id = user.getId();
//			this.username = user.getUsername();
//		}
//
//		public Integer getId() {
//			return id;
//		}
//
//		public void setId(Integer id) {
//			this.id = id;
//		}
//
//		public String getUsername() {
//			return username;
//		}
//
//		public void setUsername(String username) {
//			this.username = username;
//		}
//
//	}

}
