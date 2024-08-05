package com.market.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/main")
@Controller
public class MainController {
	
	// 학원 수정
	
	@GetMapping("/main-view")
	public String mainView() {
		return "main/main";
	}
}
