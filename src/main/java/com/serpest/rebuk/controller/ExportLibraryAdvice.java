package com.serpest.rebuk.controller;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class ExportLibraryAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		assert (target instanceof LibraryController);
		LibraryController libraryController = (LibraryController) target;
		libraryController.exportLibrary();
	}

}
