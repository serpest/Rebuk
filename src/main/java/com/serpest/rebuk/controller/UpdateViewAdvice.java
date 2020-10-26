package com.serpest.rebuk.controller;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class UpdateViewAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		assert (target instanceof UpdatableView);
		UpdatableView updatable = (UpdatableView) target;
		updatable.updateView();
	}

}
