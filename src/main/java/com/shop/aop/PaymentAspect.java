package com.shop.aop;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.shop.common.annotation.PreventDuplicate;
import com.shop.payment.controller.request.PaymentConfirmReqDto;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class PaymentAspect {

	private static final String REDIS_KEY_PREFIX = "payment:";
	private static final String REDIS_VALUE = "processing";
	private final RedisTemplate<String, String> redisTemplate;

	@Around("@annotation(preventDuplicate)")
	public Object checkDuplicateRequest(ProceedingJoinPoint joinPoint, PreventDuplicate preventDuplicate) throws
		Throwable {
		Object[] args = joinPoint.getArgs();
		if (args.length > 0 && args[0] instanceof PaymentConfirmReqDto reqDto) {
			UUID orderId = reqDto.getOrderId();
			String redisKey = REDIS_KEY_PREFIX + orderId.toString();
			long timeout = preventDuplicate.timeout();

			// Try to set the key if it does not exist
			Boolean result = redisTemplate.opsForValue()
				.setIfAbsent(redisKey, REDIS_VALUE, timeout, TimeUnit.MILLISECONDS);

			if (Boolean.FALSE.equals(result)) {
				// 이미 같은 orderId로 요청이 처리중인 경우
				return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
			}

			try {
				return joinPoint.proceed();
			} finally {
				// 처리 완료 후 Redis key 삭제 (TTL을 초과하지 않은 경우에 대비)
				redisTemplate.delete(redisKey);
			}
		}

		return joinPoint.proceed();
	}
}