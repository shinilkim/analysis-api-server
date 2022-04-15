package analysis.api.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

@Repository
public class HttpDao {
	
	public Map<String,Object> getJsonStringMap(String url, Map<String,String> param) throws Exception{
		Map<String,Object> returnMap = new HashMap<>();
		
		// 1. 기본 변수 셋팅
		StringBuilder returnJsonString = new StringBuilder();
		StringBuilder urlBuilder = new StringBuilder(url.concat("?1=1"));
		
		// 2. 파라메터 셋팅
		if (param != null) {
			param.forEach( (key, value) -> {
				try {
					urlBuilder.append("&" + URLEncoder.encode(key,"UTF-8").concat("=").concat(URLEncoder.encode(value,"UTF-8")));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			});
		}
		// 3. URL 객체 생성.
		URL urlString = new URL(urlBuilder.toString());
        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) urlString.openConnection();
        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");
        // 6. 통신을 위한 Content-type SET. 
        conn.setRequestProperty("Content-type", "application/json");
        // 7. 통신 응답 코드 확인.
        returnMap.put("httpCode", conn.getResponseCode());
        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        String line;
        while ((line = rd.readLine()) != null) {
        	returnJsonString.append(line);
        }
        returnMap.put("data", returnJsonString.toString());
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        
        return returnMap;
	}
	
	public void main(String...args) throws Exception{
		String url = "https://openapi.gg.go.kr/Grduemplymtgenrlgdhl";
		Map<String,String> param = new HashMap<>();
		param.put("KEY", "d1e335be21894375b7b0a15146dcf761");
		param.put("pIndex", "1");
		param.put("pSize", "1");
		
		HttpDao dao = new HttpDao();
		System.out.println(dao.getJsonStringMap(url, param));
	}
	
	public List<Map<String,Object>> getRows(String moduleName, Map<String,String> param) throws Exception {
		// 1. URL을 만들기 위한 StringBuilder.
        StringBuilder urlBuilder = new StringBuilder("https://openapi.gg.go.kr/"+moduleName);                       /*URL*/
        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("KEY","UTF-8")    + "=" + param.get("KEY"));                                /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8")   + "=" + URLEncoder.encode("json", "UTF-8"));              /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("pIndex","UTF-8") + "=" + URLEncoder.encode(param.get("pIndex"), "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("pSize","UTF-8")  + "=" + URLEncoder.encode(param.get("pSize"), "UTF-8"));  /*한 페이지 결과 수*/
        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());
        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");
        // 6. 통신을 위한 Content-type SET. 
        conn.setRequestProperty("Content-type", "application/json");
        // 7. 통신 응답 코드 확인.
        System.out.println("Response code: " + conn.getResponseCode());
        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        System.out.println(sb.toString());
        
        // 1. 문자열 형태의 JSON을 파싱하기 위한 JSONParser 객체 생성.
        JSONParser parser = new JSONParser();
        // 2. 문자열을 JSON 형태로 JSONObject 객체에 저장. 	
        JSONObject obj = (JSONObject)parser.parse(sb.toString());
        // 3. 필요한 리스트 데이터 부분만 가져와 JSONArray로 저장.
        JSONArray items = (JSONArray) obj.get(moduleName);
//        System.out.println(items);
        Map<String,Object> result = new HashMap<>();
//        JSONArray head = (JSONArray) ((JSONObject)items.get(0)).get("head");
        JSONArray data = (JSONArray) ((JSONObject)items.get(1)).get("row");
        
//        result.put("list_total_count", ((JSONObject) head.get(0)).get("list_total_count"));
        result.put("data", data);
        List<Map<String,Object>> ret = new ArrayList<>();
       
//        for( int i = 0; i < data.size(); i++ ) {
//        	Map<String,Object> m = new HashMap<>();
//        	JSONObject ele = (JSONObject) parser.parse(data[i].toString());
//        }
        
        return ret;
	}
	
	
}
