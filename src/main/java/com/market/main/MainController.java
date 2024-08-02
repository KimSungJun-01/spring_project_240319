package com.market.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/main")
@Controller
public class MainController {
	
	// 8.2 변경 테스트
	
	@GetMapping("/main-view")
	public String mainView() {
		return "main/main";
	}
}
