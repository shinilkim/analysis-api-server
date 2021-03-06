package analysis.api.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import analysis.api.biz.dao.HttpDao;
import analysis.api.biz.dao.MariadbJdbcDao;
import lombok.RequiredArgsConstructor;

@Repository
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:config/properties/api.properties")
public class OpenApiBizService {
	
	@Autowired
	HttpDao httpDao;
	
	@Autowired
	MariadbJdbcDao mariadbDao;
	
	private final Environment env;
	
	public static final String API_URL = "https://openapi.gg.go.kr/";
	public static Map<String,String> serviceKeyMap = new HashMap<>();
	
//	public OpenApiBizService() {
//		serviceKeyMap.put("Grduemplymtgenrlgdhl", "d1e335be21894375b7b0a15146dcf761");
//	}
	
	public Map<String, Object> get(String apiName, String pIndex, String pSize) throws Exception{
		String url = API_URL.concat(apiName);
		Map<String, String> param = new HashMap<>();
		param.put("KEY", env.getProperty("openapi.gg.go.kr.".concat(apiName)));
//		param.put("KEY", serviceKeyMap.get(apiName));
		param.put("pIndex", pIndex);
		param.put("pSize", pSize);
		
		Map<String, Object> returnMap = null;
		try {
			returnMap = httpDao.getJsonStringMap(url, param);
			if( (int) returnMap.get("httpCode") == 200 ) {
				returnMap.put("data", this.getStringToMap(apiName, (String) returnMap.get("data")));
			}
		} catch(Exception e) {
			returnMap = new HashMap<>();
			returnMap.put("httpCode", 500);
		}
		
		return returnMap;
	}
	
	public List<Map<String,Object>> getAnalysisList(String tableName, int startIdx, int endIdx) throws Exception{
		String sql = "select * from "+tableName+" limit "+startIdx+", "+endIdx;
		return mariadbDao.getRows(sql);
	}
	
	private Object getStringToMap(String apiName, String data) throws Exception {
		// 1. ????????? ????????? JSON??? ???????????? ?????? JSONParser ?????? ??????.
        JSONParser parser = new JSONParser();
        // 2. ???????????? JSON ????????? JSONObject ????????? ??????. 	
        JSONObject obj = (JSONObject)parser.parse(data);
        // 3. ????????? ????????? ????????? ????????? ????????? JSONArray??? ??????.
        JSONArray items = (JSONArray) obj.get(apiName);
        // 4. ??????
        return (JSONArray) ((JSONObject) items.get(1)).get("row");
	}
}
