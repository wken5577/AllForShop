package com.shop.payment.service;

import java.util.UUID;

public interface PaymentService {

	boolean confirmPayment(UUID orderId, String paymentKey, int amount);

	boolean cancelPayment(UUID orderId, String paymentKey, String cancelReason);
}
