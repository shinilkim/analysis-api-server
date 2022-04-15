package analysis.api.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import analysis.api.biz.dao.HttpDao;
import analysis.api.biz.dao.MariadbJdbcDao;

@Repository
public class OpenApiBizService {
	
	@Autowired
	HttpDao httpDao;
	
	@Autowired
	MariadbJdbcDao mariadbDao;
	
	public static final String API_URL = "https://openapi.gg.go.kr/";
	public static Map<String,String> serviceKeyMap = new HashMap<>();
	
	public OpenApiBizService() {
		serviceKeyMap.put("Grduemplymtgenrlgdhl", "d1e335be21894375b7b0a15146dcf761");
	}
	
	public Map<String, Object> get(String apiName, String pIndex, String pSize) throws Exception{
		String url = API_URL.concat(apiName);
		Map<String, String> param = new HashMap<>();
		param.put("KEY", serviceKeyMap.get(apiName));
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
		// 1. 문자열 형태의 JSON을 파싱하기 위한 JSONParser 객체 생성.
        JSONParser parser = new JSONParser();
        // 2. 문자열을 JSON 형태로 JSONObject 객체에 저장. 	
        JSONObject obj = (JSONObject)parser.parse(data);
        // 3. 필요한 리스트 데이터 부분만 가져와 JSONArray로 저장.
        JSONArray items = (JSONArray) obj.get(apiName);
        // 4. 리턴
        return (JSONArray) ((JSONObject) items.get(1)).get("row");
	}
}
