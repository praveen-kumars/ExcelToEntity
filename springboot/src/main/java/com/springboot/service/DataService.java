package com.springboot.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Service
public class DataService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	

	public ArrayNode getDatas(String dbName){
				
		String query="SELECT DISTINCT * FROM "+dbName;	
		List<ObjectNode> result=  jdbcTemplate.query(query, (ResultSet rs)->{
			List<ObjectNode> userObject=new ArrayList<>();
			java.sql.ResultSetMetaData metadata=rs.getMetaData();
			int columnCount=metadata.getColumnCount();
			while(rs.next()) {
			ObjectNode userJson=objectMapper.createObjectNode();
			for(int i=1;i<=columnCount;i++) {
				String columnName=metadata.getColumnName(i);
					Object columnValue=rs.getObject(i);
					userJson.put(columnName, columnValue!=null? columnValue.toString():null);
			}
			userObject.add(userJson);
			}
			return userObject;
		});
		ArrayNode jsonArray=objectMapper.createArrayNode();
		result.forEach(jsonArray::add);
		return jsonArray;
	}
	
	public List<String> getDbTables(){
		String query="SHOW TABLES";
		return jdbcTemplate.queryForList(query,String.class);
	}
}
