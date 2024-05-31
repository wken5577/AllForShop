package com.shop.common.exception.entity;


public class OrderAlreadyPaidException extends Exception {
	public OrderAlreadyPaidException(String message) {
		super(message);
	}
}
