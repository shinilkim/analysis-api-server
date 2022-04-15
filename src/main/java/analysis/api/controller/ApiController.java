package analysis.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import analysis.api.biz.OpenApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api")
@Api(tags = {"API"})
@RequiredArgsConstructor
public class ApiController {
	
	@Autowired
	OpenApiService openApiService;
	
	@PostMapping(value = "/sample")
    @ApiOperation(value = "샘플", notes="")
    public Map<String,Object> sample() {
		Map<String,Object> ret = new HashMap<>();
		ret.put("name", "shinil.kim");
		return ret;
    }
}
