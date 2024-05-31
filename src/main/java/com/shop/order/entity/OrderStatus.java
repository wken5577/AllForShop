package com.shop.order.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    AWAITING_PAYMENT_CONFIRMATION("결제 대기 중"),
    PAYMENT_FAILED("결제 실패"),
    CANCEL("주문 취소"),
    PAYMENT_COMPLETE("결제 완료");

    private final String info;

}
