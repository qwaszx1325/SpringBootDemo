package com.competnion.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class OrderService {

	public String ecpayCheckout() {

		AllInOne all = new AllInOne("");
		

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo("ewfdsfhewtrgf123");
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setTotalAmount("50");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		// 交易結果回傳網址，只接受 https 開頭的網站，可以使用 ngrok

		 // 后端交易结果回传网址，只接受 https 开头的 URL，可以使用 ngrok
        obj.setReturnURL("https://87b1-61-222-34-1.ngrok-free.app/backendReturn");

        // 前端用户支付完成后的重定向网址
        obj.setClientBackURL("https://87b1-61-222-34-1.ngrok-free.app/frontendReturn");
		obj.setNeedExtraPaidInfo("N");
		// 商店轉跳網址 (Optional)
		String form = all.aioCheckOut(obj, null);

		return form;
	}
}
