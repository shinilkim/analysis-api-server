package analysis.api.biz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/biz/crawler/api")
@Api(tags = {"Biz Crawler API"})
@RequiredArgsConstructor
public class CrawlerController {
	public String get(String url) throws Exception{
		return null;
	}
	public static void main(String...args) throws Exception{
		String URL = "";
	}
}
