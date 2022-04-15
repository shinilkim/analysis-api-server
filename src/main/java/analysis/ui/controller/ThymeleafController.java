package analysis.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
	
	@GetMapping("/test")
	public String html(Model model) {
		model.addAttribute("name", "shinil.kim");
		return "thymeleaf/test";
	}
	
	@GetMapping("/page/{page}")
	public String page(Model model, @PathVariable("page") String page) {
		return "thymeleaf/".concat(page);
	}
}
