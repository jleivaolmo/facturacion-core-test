package com.echevarne.sap.cloud.facturacion.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

/**
 * Class for the {@link ContextProvider}.
 *
 * <p>. . .</p>
 * <p>This is the container for Spring IoC references and beans </p>
 *
 */
@Component
@AllArgsConstructor
public class ContextProvider implements ApplicationContextAware {

	public static ApplicationContext CONTEXT;
	public static Repositories repositories;

	private final Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
        String[] profiles = this.environment.getActiveProfiles();
        if (!"test".equals( profiles[0])) {
        	repositories = new Repositories(applicationContext);
        }
    }

    /**
     * Get a Spring bean by type.
     **/
    public static <T> T getBean(Class<T> beanClass) {
        return CONTEXT.getBean(beanClass);
    }

    /**
     * Get a Spring bean by name.
     **/
    public static Object getBean(String beanName) {
        if (CONTEXT == null) return null;
        return CONTEXT.getBean(beanName);
    }

    /**
     *
     * @param entity
     * @return
     */
    public static Object getRepository(Object entity) {
    	return repositories.getRepositoryFor(entity.getClass()).orElse(null);
    }

    /**
     *
     * @param beanClass
     * @return
     * @return
     */
    public static Object getRepository(Class<?> beanClass) {
    	return repositories.getRepositoryFor(beanClass).orElse(null);
    }
    

}
