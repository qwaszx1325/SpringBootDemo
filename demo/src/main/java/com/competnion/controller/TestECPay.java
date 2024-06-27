package com.competnion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.competnion.service.OrderService;

@RestController
public class TestECPay {

	@Autowired
	OrderService orderService;

	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout() {
		String aioCheckOutALLForm = orderService.ecpayCheckout();

		return aioCheckOutALLForm;
	}

	 @PostMapping("/return")
	    public void handleECPayReturn(@RequestBody String body) {
	        // 這裡處理來自綠界的交易結果通知
	        // 解析 body 並根據交易結果更新訂單狀態
		 System.out.println(body);
	    }
}
