package analysis.api.biz.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import analysis.api.sample.service.OpenApiBizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/biz/api")
@Api(tags = {"Biz API"})
@RequiredArgsConstructor
public class ApiController {
	
	@Autowired
	OpenApiBizService openApiBizService;
	
	@PostMapping(value = "/sample")
    @ApiOperation(value = "샘플", notes="")
    public Map<String,Object> sample() {
		Map<String,Object> ret = new HashMap<>();
		ret.put("name", "shinil.kim");
		return ret;
    }
}
