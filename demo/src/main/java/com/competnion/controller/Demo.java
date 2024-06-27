package com.competnion.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.competnion.service.OrderService;
import com.competnion.utils.ECPayUtils;
import com.competnion.utils.StendMail;

@Controller
public class Demo {
	@Autowired
	private StendMail stendMail;

	@GetMapping("/hello")
	public String demo() {
		stendMail.sendSimpleEmail("qwaszx1325@gmail.com", "Test Subject", "This is a test email.");

		return "Hello";
	}
	
	@Autowired
    OrderService orderService;

    @GetMapping("/ecpayCheckout")
    public String ecpayCheckout(Model model) {
        String aioCheckOutALLForm = orderService.ecpayCheckout();
        model.addAttribute("aioCheckOutALLForm", aioCheckOutALLForm);
        return "ecpayCheckout"; // 返回一个包含支付表单的 Thymeleaf 模板
    }
    
    @PostMapping("/backendReturn")
    public void handleBackendReturn(@RequestParam Map<String, String> params) {
        // 处理来自绿界的后台交易结果通知
        System.out.println("Backend return: " + params);
        
        // 检查 CheckMacValue 是否正确，以验证数据完整性
        boolean isValid = ECPayUtils.verifyCheckMacValue(params, "你的HashKey", "你的HashIV");

        if (isValid) {
            // 根据交易结果进行后续处理，例如更新订单状态
            if ("1".equals(params.get("RtnCode"))) {
                // 交易成功，更新订单状态
            } else {
                // 交易失败，处理相应逻辑
            }
        } else {
            // 签章验证失败，处理相应逻辑
        }
    }

    @GetMapping("/frontendReturn")
    public String handleFrontendReturn() {
        // 处理来自绿界的前端重定向
        return "frontendReturn"; // 返回一个显示支付结果的页面
    }
}
