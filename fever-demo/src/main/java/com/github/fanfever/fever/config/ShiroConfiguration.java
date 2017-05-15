package com.github.fanfever.fever.config;

import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.fanfever.fever.security.SystemAuthorizingRealm;
import com.google.common.collect.Maps;

/**这里定义关于URL的规则和访问权限,通过URL规则来进行过滤和权限校验 Apache
 * Shiro核心通过Filter实现,如SpringMvc通过DispachServlet控制
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Configuration
public class ShiroConfiguration {

	/**
	 * ShiroFilterFactoryBean处理拦截资源文件问题
	 *
	 * Filter Chain定义说明 1. 一个URL可以配置多个Filter,使用逗号分隔 2. 当设置多个过滤器时,全部验证通过,才视为通过 3.
	 * 部分过滤器可指定参数,如perms,roles
	 *
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		// 拦截器,添加过滤链,从上向下执行
		// anon:不需要认证,auchc:需要认证登录才能使用,user:必须存在用户当登入操作时不做检查
		Map<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
		filterChainDefinitionMap.put("/login", "authc");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/static/**", "anon");
//		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		// 登录,成功及未授权url
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/");
		shiroFilterFactoryBean.setUnauthorizedUrl("/login");

		// Map<String, Filter> filters = Maps.newHashMap();
		// filters.put("anon", new AnonymousFilter());
		// filters.put("authc", new FormAuthenticationFilter());
		// filters.put("logout", new LogoutFilter());
		// filters.put("roles", new RolesAuthorizationFilter());
		// filters.put("user", new UserFilter());
		// filters.put("rest", new HttpMethodPermissionFilter());
		// shiroFilterFactoryBean.setFilters(filters);
		return shiroFilterFactoryBean;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm());
		return securityManager;
	}

	@Bean(name = "realm")
	// @DependsOn("lifecycleBeanPostProcessor")
	public SystemAuthorizingRealm realm() {
		return new SystemAuthorizingRealm();
	}

	// @Bean
	// public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	// return new LifecycleBeanPostProcessor();
	// }
}
