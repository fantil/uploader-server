package org.fanti.uploader.server.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */
public class PropertyConfigure extends PropertyPlaceholderConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConfigure.class);
    private static Map<String, Object> propertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        //spring载入配置文件(具体原理还在研究中)
        super.processProperties(beanFactoryToProcess, props);
        propertiesMap = new HashMap<String, Object>();
        //便利加载的配置文件的键值，并根据键值获取到value值，然后一同保存进map对象
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertiesMap.put(keyStr, value);
            LOGGER.debug("key : {}, value : {}", keyStr, value);
        }
    }

    //此方法根据map对象的键获取其value的值
    public static String getContextProperty(String name) {
        return (String) propertiesMap.get(name);
    }
}
