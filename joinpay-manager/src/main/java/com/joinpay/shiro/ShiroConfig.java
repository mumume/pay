package com.joinpay.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.joinpay.common.BDSessionListener;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import net.sf.ehcache.CacheManager;

@Configuration
@PropertySource("classpath:config/shiro.properties")
public class ShiroConfig {

	@Value("${shiro.cacheType}")
	private String cacheType;
	@Value("${shiro.tomcatTimeout}")
	private int tomcatTimeout;
	private int interval;

	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
	 *
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	@Bean
	ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/docs/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/upload/**", "anon");
		filterChainDefinitionMap.put("/files/**", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/blog", "anon");
		filterChainDefinitionMap.put("/blog/open/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(userRealm());
		// 自定义缓存实现 使用redis
//		if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
//			securityManager.setCacheManager(cacheManager());
//		} else {
			securityManager.setCacheManager(ehCacheManager());
//		}
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		return userRealm;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * cacheManager 缓存 redis实现 使用的是shiro-redis开源插件
	 *
	 * @return
	 */
//	public RedisCacheManager cacheManager() {
//		RedisCacheManager redisCacheManager = new RedisCacheManager();
//		return redisCacheManager;
//	}

	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis 使用的是shiro-redis开源插件
	 */
//	@Bean
//	public RedisSessionDAO redisSessionDAO() {
//		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//		return redisSessionDAO;
//	}

	@Bean
	public SessionDAO sessionDAO() {
//		if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
//			return redisSessionDAO();
//		} else {
			return new MemorySessionDAO();
//		}
	}

	/**
	 * shiro session的管理
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		System.out.println("tomcatTimeout---"+tomcatTimeout);
		sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
		sessionManager.setSessionDAO(sessionDAO());
		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new BDSessionListener());
		sessionManager.setSessionListeners(listeners);
		return sessionManager;
	}


	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManager(CacheManager.create());
		return em;
	}

}
