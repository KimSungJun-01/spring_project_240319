package com.market.map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/map")
@Controller
public class MapController {
	
	@GetMapping("/map-view")
	public String mapView(Model model) {
		return "map/map";
	}
}
