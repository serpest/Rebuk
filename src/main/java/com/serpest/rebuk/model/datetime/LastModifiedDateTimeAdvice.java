package com.serpest.rebuk.model.datetime;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class LastModifiedDateTimeAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		assert (target instanceof EditableDate);
		assert (method.getName().startsWith("set") || method.getName().startsWith("add") || method.getName().startsWith("remove"));
		EditableDate datable = (EditableDate) target;
		datable.updateLastModifiedDateTimeToNow();
	}

}