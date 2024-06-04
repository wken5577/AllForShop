package com.shop.aop;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
	private final RedissonClient redissonClient;

	@Around("@annotation(preventDuplicate)")
	public Object checkDuplicateRequest(ProceedingJoinPoint joinPoint, PreventDuplicate preventDuplicate) throws
		Throwable {
		Object[] args = joinPoint.getArgs();
		if (args.length > 0 && args[0] instanceof PaymentConfirmReqDto reqDto) {
			UUID orderId = reqDto.getOrderId();
			String lockKey = REDIS_KEY_PREFIX + orderId.toString();
			RLock lock = redissonClient.getLock(lockKey);


			if (lock.tryLock(preventDuplicate.timeout(), TimeUnit.MILLISECONDS)) {
				try {
					return joinPoint.proceed();
				} finally {
					lock.unlock();
				}
			} else {
				// 이미 같은 orderId로 요청이 처리중인 경우
				return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
			}

		}

		return joinPoint.proceed();
	}
}