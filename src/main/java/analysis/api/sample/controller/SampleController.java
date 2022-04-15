package analysis.api.sample.controller;

import java.rmi.ServerError;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import analysis.api.sample.service.OpenApiBizService;
import analysis.ui.vo.sample.SampleResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/sample")
@Api(tags = {"Sample API"})
@RequiredArgsConstructor
public class SampleController {
	
	@Autowired
	OpenApiBizService openApiBizService;
	
	@RequestMapping(value="/openapi/{apiName}/{pIndex}/{pSize}", method={RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value="경기도 공공데이터 개발포털 API 조회", notes="https://data.gg.go.kr")
	public Map<String, Object> getOpenApi(
			@ApiParam(value="API명", example="Grduemplymtgenrlgdhl", required=true) @PathVariable String apiName, 
			@ApiParam(value="페이지번호", example="1", required=true) @PathVariable String pIndex, 
			@ApiParam(value="목록갯수", example="1", required=true) @PathVariable String pSize) throws Exception{
		return openApiBizService.get(apiName, pIndex, pSize);
	}
	
	@PostMapping(value="/analysis/{tableName}/{startIdx}/{endIdx}", headers = { "Content-type=application/json" })
	@ApiOperation(value="분석 테이블 조회(List)", notes="Java 데이터 타입으로 리턴")
	public List<Map<String,Object>> getAnalysisTableSearch(
			@ApiParam(value="테이블", example="api_dtl", required=true) @PathVariable String tableName, 
			@ApiParam(value="시작번호", example="1", required=true) @PathVariable int startIdx, 
			@ApiParam(value="종료번호", example="10", required=true) @PathVariable int endIdx) throws Exception{
		return openApiBizService.getAnalysisList(tableName, startIdx, endIdx);
	}
	
	@GetMapping(value="/analysis/{tableName}/{startIdx}/{endIdx}/vo", headers = { "Content-type=application/json" })
	@ApiOperation(value="분석 테이블 조회(Vo)", notes="Vo 타입으로 리턴")
	@ApiResponses({
	    @ApiResponse(code = 200, message = "성공", response = SampleResponseVo.class),
	    @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
	    @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
	  })
	public SampleResponseVo<List<Map<String,Object>>> getAnalysisTableSearchVo(
			@ApiParam(value="테이블", example="api_dtl", required=true) @PathVariable String tableName, 
			@ApiParam(value="시작번호", example="1", required=true) @PathVariable int startIdx, 
			@ApiParam(value="종료번호", example="2", required=true) @PathVariable int endIdx) throws Exception{
		return new SampleResponseVo<List<Map<String, Object>>>(openApiBizService.getAnalysisList(tableName, startIdx, endIdx));
	}
}
