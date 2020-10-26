package com.serpest.rebuk.model.datetime;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class CreationDateTimePostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		assert (bean instanceof EditableDate);
		EditableDate datable = (EditableDate) bean;
		datable.updateCreationDateTimeToNow();
		return bean;
	}

}
