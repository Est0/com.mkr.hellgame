package com.mkr.hellgame.infrastructure.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class LoggableAnnotationBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(LoggableAnnotationBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("postProcessBeforeInitialization called for bean {}. skipping...", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        logger.debug("postProcessAfterInitialization called for bean {}", beanName);
        final Class<?> clazz = bean.getClass();
        DoWithFields(clazz, new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalAccessException {
                if (field.isAnnotationPresent(Loggable.class)) {
                    logger.debug("Loggable field was found with name {}. Injecting Log instance...", field.getName());
                    field.setAccessible(true);
                    field.set(bean, LoggerFactory.getLogger(clazz));
                }
            }
        });
        return bean;
    }

    private static interface FieldCallback {
        void doWith(Field field) throws IllegalAccessException;
    }

    private void DoWithFields(Class<?> clazz, FieldCallback fieldCallback) {
        if (clazz == null) {
            return;
        }
        for (Field field: clazz.getDeclaredFields()) {
            try {
                fieldCallback.doWith(field);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        DoWithFields(clazz.getSuperclass(), fieldCallback);
    }
}
