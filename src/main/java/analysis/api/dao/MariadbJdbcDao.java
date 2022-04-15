package analysis.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author shini
 *
 */
@Repository
@Slf4j
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:config/properties/db.properties")
public class MariadbJdbcDao {
	
	private final Environment env;
	
	/**
	 * [A01] 목록 조회
	 * @param sql 쿼리
	 * @param param 쿼리 파라메터
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getRows(String sql) throws Exception {
		return this.getRows(sql, null);
	}
	public List<Map<String,Object>> getRows(String sql, Map<String,String> param) throws Exception {
		// 01. DB 연결
		Connection conn = this.getConnection();
		// 02. DB 연결 > 쿼리문장
		PreparedStatement pstmt = conn.prepareStatement(this.getQuery(sql, param));
		// 03. DB 연결 > 쿼리문장 > 쿼리수행 결과
		ResultSet rs = pstmt.executeQuery();
		// 04. DB 연결 > 쿼리수행 > 쿼리수행 결과 > 결과에 대한 메타데이터(컬럼명:key)
		ResultSetMetaData rsmd = rs.getMetaData();
		// 05. 최종 결과 리턴 객체
		List<Map<String,Object>> rows = Collections.emptyList();

		// 06. 결과 데이터를 value 에서 key, value 형태의 배열로 변환
		if ( rs != null ) {
			rows = this.getMetaData(rsmd, rs, rows);
		}
		
		// 07. 자원 close
		this.close(conn, pstmt, rs);
		
		return rows;
	}
	
	private void close(Connection conn, PreparedStatement pstmt) throws Exception {
		this.close(pstmt);this.close(conn);
	}
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) throws Exception {
		this.close(rs);this.close(pstmt);this.close(conn);
	}
	private void close(Connection conn) throws Exception{if( conn == null ) conn.close();}
	private void close(PreparedStatement pstmt) throws Exception{if( pstmt == null ) pstmt.close();}
	private void close(ResultSet rs) throws Exception{if( rs == null ) rs.close();}
	
	private List<Map<String,Object>> getMetaData(ResultSetMetaData rsmd, ResultSet rs, List<Map<String,Object>> rows) throws Exception {
		
		// 01. resultset 객체에서 컬럼명을 추출한다.
		int count = rsmd.getColumnCount();
		String [] columns = new String[count];
		List<String> keys = new ArrayList<>();
		for( int i=0; i < count; i++ ) {
			columns[i] = rsmd.getColumnLabel(i+1);
			keys.add(columns[i]);// key 추가
		}
		
		// 02. key, value 합쳐서 배열을 만든다.
		rows = new ArrayList<>();
		while(rs.next()) {
			LinkedHashMap<String, Object> row = new LinkedHashMap<>();
			for(String key : keys) {// 컬럼의 갯수만큼 반복 수행
				row.put(key, rs.getObject(key));
			}
			rows.add(row);
		}
		
		return rows;
	}
	
	/**
	 * [A02] 단건 조회
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getRow(String sql, Map<String,String> param) throws Exception {
		Map<String,Object> row = new HashMap<>();
		return row;
	}
	
	/**
	 * [A03] 단일 컬럼값 조회
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String getField(String sql, Map<String,String> param) throws Exception {
		return null;
	}
	
	/**
	 * [A04] 등록
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insert(String sql, Map<String,String> param) throws Exception {
		int affected = 0;
		return affected;
	}
	
	/**
	 * [A05] 업데이트
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int update(String sql, Map<String,String> param) throws Exception {
		int affected = 0;
		return affected;
	}
	
	/**
	 * [A06] 삭제
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int delete(String sql, Map<String,String> param) throws Exception {
		int affected = 0;
		return affected;
	}
	
	private String getQuery(String sql) throws Exception {
		return this.getQuery(sql, null);
	}
	private String getQuery(String sql, Map<String,String> param) throws Exception {
		if ( param != null ) {
			Iterator<Entry<String, String>> it = param.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> set = (Map.Entry<String, String>) it.next();
				sql = sql.replace("#{"+set.getKey()+"}", "'"+set.getValue()+"'").replace("${"+set.getKey()+"}", set.getValue());
			}
			String [] sql2 = sql.split("\n");
			if( sql.contains("#{") || sql.contains("${")) {
				sql = "";
				for(int i=0; i < sql2.length; i++) {
					if( !sql2[i].contains("#{") && !sql2[i].contains("${") ) {
						sql += sql2[i]+"\n";
					}
				}
			}
		}
		
		return sql;
	}
	

	
	public List<Map<String,Object>> getList() throws Exception {	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		List<Map<String,Object>> items = null; 
				
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement("select * from chl_acdt_info");
			rs = pstmt.executeQuery();
			
			
			rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			String [] columns = new String[count];
			List<String> names = new ArrayList<>();
			for( int i=0; i < count; i++ ) {
				columns[i] = rsmd.getColumnLabel(i+1);
				names.add(columns[i]);
			}
			
			
			items = new ArrayList<>();
			while(rs.next()) {
				LinkedHashMap<String, Object> row = new LinkedHashMap<>();
				for(String column : names) {
					row.put(column, rs.getObject(column));
				}
				items.add(row);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
            if(rs != null) {
                rs.close();
            }
            
            if(pstmt != null) {
                pstmt.close();
            }
        
            if(conn != null) {
                conn.close();
            }
		}
		return items;
	}
	
	/**
	 * [B01] 
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception{
		Class.forName(env.getProperty("common.mariadb.driver"));
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(
					env.getProperty("local.mariadb.url"), 
					env.getProperty("local.mariadb.username"), 
					env.getProperty("local.mariadb.password"));
		} catch(Exception e) {
			System.out.println("driver: "+env.getProperty("common.mariadb.driver"));
			System.out.println("url: "+env.getProperty("local.mariadb.url"));
			System.out.println("user: "+env.getProperty("local.mariadb.username"));
			System.out.println("password: "+env.getProperty("local.mariadb.password"));
			e.printStackTrace();
		}
		return conn;
	}
	
	public String test() throws Exception {
		Connection conn = this.getConnection();
		log.info(conn.toString());
		String ret = conn.toString();
		this.close(conn);
		return ret;
	}
	
	public static void main(String...args) throws Exception{
//		MariadbJdbcDao dao = new MariadbJdbcDao();
//		
//		// [테스트-A01] 파라메터 N
//		String sql = "select * from chl_acdt_info";
//		sql = dao.getQuery(sql);
//		List<Map<String,Object>> items = dao.getRows(sql);
//		if( items == null ) {
//			System.out.println("---");
//		} else {
//			for( Map<String,Object> item : items ) {
//				System.out.println(item);
//			}
//		}
		
		// [테스트-A02] 파라메터 Y
//		String sql = "SELECT 1 from dual where name=#{name} and age=${age} \n and test=#{test} \n and test1=${test1}";
//		Map<String,String> param = new HashMap<>();
//		param.put("name", "shinil.kim");
//		param.put("age", "10");
//		sql = dao.getQuery(sql, param);
//		System.out.println(sql);
		
//		List<Map<String,Object>> items = dao.getList();
//		if( items == null ) {
//			System.out.println("---");
//		} else {
//			for( Map<String,Object> item : items ) {
//				System.out.println(item);
//			}
//		}
		
	}
}
