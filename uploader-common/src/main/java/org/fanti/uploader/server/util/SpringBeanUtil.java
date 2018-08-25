package org.fanti.uploader.server.util;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */
@Service
public class SpringBeanUtil implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {

    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    private void setContext(ApplicationContext applicationContext) {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * @param name  the name of the bean to retrieve
     * @param clazz type the bean must match.
     * @return an instance of the bean
     * @see org.springframework.beans.factory.BeanFactory#getBean(String, Class)
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     * @see org.springframework.beans.factory.BeanFactory#getBean(String)
     */

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * @param name the name of the bean to query
     * @return whether a bean with the given name is present
     * @see org.springframework.beans.factory.BeanFactory#containsBean(String)
     */

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * @param name the bean name to check for aliases
     * @return the aliases, or an empty array if none
     * @see org.springframework.beans.factory.BeanFactory#getAliases(String)
     */
    public static String[] getAliases(String name) {
        return applicationContext.getAliases(name);
    }

    public static <T> Map<String, T> getBeansByClass(Class<T> c) {
        return applicationContext.getBeansOfType(c);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
