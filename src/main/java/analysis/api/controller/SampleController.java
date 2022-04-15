package analysis.api.controller;

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
import analysis.ui.vo.sample.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/sample")
@Api(tags = {"Sample API"})
@RequiredArgsConstructor
public class SampleController {
	
	@Autowired
	OpenApiService openApiService;
	
	@RequestMapping(value="/openapi/{apiName}/{pIndex}/{pSize}", method={RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value="경기도 공공데이터 개발포털 API 조회", notes="https://data.gg.go.kr")
	public Map<String, Object> getOpenApi(
			@ApiParam(value="API명", example="Grduemplymtgenrlgdhl", required=true) @PathVariable String apiName, 
			@ApiParam(value="페이지번호", example="1", required=true) @PathVariable String pIndex, 
			@ApiParam(value="목록갯수", example="1", required=true) @PathVariable String pSize) throws Exception{
		return openApiService.get(apiName, pIndex, pSize);
	}
	
	@PostMapping(value="/analysis/{tableName}/{startIdx}/{endIdx}", headers = { "Content-type=application/json" })
	@ApiOperation(value="분석 테이블 조회(List)", notes="Java 데이터 타입으로 리턴")
	public List<Map<String,Object>> getAnalysisTableSearch(
			@ApiParam(value="테이블", example="api_dtl", required=true) @PathVariable String tableName, 
			@ApiParam(value="시작번호", example="1", required=true) @PathVariable int startIdx, 
			@ApiParam(value="종료번호", example="10", required=true) @PathVariable int endIdx) throws Exception{
		return openApiService.getAnalysisList(tableName, startIdx, endIdx);
	}
	
	@GetMapping(value="/analysis/{tableName}/{startIdx}/{endIdx}/vo", headers = { "Content-type=application/json" })
	@ApiOperation(value="분석 테이블 조회(Vo)", notes="Vo 타입으로 리턴")
	public ResponseVo<List<Map<String,Object>>> getAnalysisTableSearchVo(
			@ApiParam(value="테이블", example="api_dtl", required=true) @PathVariable String tableName, 
			@ApiParam(value="시작번호", example="1", required=true) @PathVariable int startIdx, 
			@ApiParam(value="종료번호", example="2", required=true) @PathVariable int endIdx) throws Exception{
		return new ResponseVo<List<Map<String, Object>>>(openApiService.getAnalysisList(tableName, startIdx, endIdx));
	}
}
