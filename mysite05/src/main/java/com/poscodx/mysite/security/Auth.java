package com.poscodx.mysite.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) // 세션을 처리하기 위한 어노테이션 
@Target({TYPE, METHOD})
public @interface Auth {
	// public String value() default "";
	public String Role() default "USER"; // Role이라는 속성 정의, 어노테이션 사용시, 기본 값으로 USER라는 문자열 가짐 
	public boolean test() default false;
}
