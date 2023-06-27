package com.springboot.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.entity.ExcelData;



@Service
public class DataService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void createUniqueIndex(List<ExcelData> excelData,String dbName) {
		if(excelData.isEmpty()) {
			throw new IllegalArgumentException("null");
		}
		ExcelData data=excelData.get(0);
		Map<?, Object> value=data.getUnknownCells();
		String[] keys=value.keySet().toArray(new String[0]);
		IndexOperations indexOperations=mongoTemplate.indexOps(dbName);
	    Index index=new Index().named("unique_index")
	    .on(keys[0], Sort.Direction.ASC)
	    .on(keys[1],Sort.Direction.ASC)
	    .unique();
	    indexOperations.ensureIndex(index);    
	}
	
	
	
	public void saveData(List<ExcelData> excelData,String dbName) throws JsonProcessingException {
		try {
		createUniqueIndex(excelData,dbName);
		}
		catch (Exception e) {
		}
		for(ExcelData data:excelData) {
			Map<?, Object> value=data.getUnknownCells();
			try {
			mongoTemplate.insert(value,dbName);
		}		
			catch (Exception e) {
			}
	}
	}
	
	
	public List<JsonObject> retrieveAll(String dbName){
		ProjectionOperation project=Aggregation.project().andExclude("_id");
		Aggregation aggregation=newAggregation(project);		
		AggregationResults<JsonObject> aggregationResults=mongoTemplate.aggregate(aggregation,dbName,JsonObject.class);
		List<JsonObject> results=aggregationResults.getMappedResults();

		return results;
		
	}
	
	public Set<String> retrieveAllDb(){
		return mongoTemplate.getCollectionNames();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void saveHash(List<Map<String, Object>> datlist,String dbName)  throws JsonProcessingException {
		ObjectMapper objectMapper=new ObjectMapper();
		for(Map<String, Object> data :datlist) {
			Map<String, Object> value=data;
			String jsoString= objectMapper.writeValueAsString(value);
			mongoTemplate.save(jsoString,dbName);
		}		
	}
	//String jsoString= objectMapper.writeValueAsString(value);
}
