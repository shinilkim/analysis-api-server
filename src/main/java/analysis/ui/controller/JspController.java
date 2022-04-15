package analysis.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import analysis.ui.vo.SampleVo;

@Controller
public class JspController {
	
	@GetMapping("/jsp/sample/jstl/list")
	public ModelAndView sampleJstlList(ModelAndView mv) {
        SampleVo post1 = new SampleVo(1, "lee", "book1");
        SampleVo post2 = new SampleVo(2, "choi", "book2");
        SampleVo post3 = new SampleVo(3, "kim", "book3");
        List<SampleVo> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        list.add(post3);
        mv.addObject("list", list);
        mv.setViewName("sample/jstl/list");
        return mv;
	}
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
