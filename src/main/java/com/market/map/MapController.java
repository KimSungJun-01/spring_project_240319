package com.market.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/map")
@Controller
public class MapController {
	
	@GetMapping("/map-view")
	public String mapView() {
		return "map/map";
	}
	
}
