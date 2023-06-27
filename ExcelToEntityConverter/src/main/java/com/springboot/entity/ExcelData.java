package com.springboot.entity;

import java.util.Map;

import javax.persistence.ElementCollection;

import com.poiji.annotation.ExcelRow;
import com.poiji.annotation.ExcelUnknownCells;


public class ExcelData {
	
	@ElementCollection
	@ExcelUnknownCells
    private Map<?, Object> data;

	public Map<?, Object> getUnknownCells() {
		return data;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*@ExcelRow
	private long rowIndex;
	
	public long getRowIndex() {
		return rowIndex;
	}
	
	public void setRowIndex(long rowIndex) {
		this.rowIndex = rowIndex;
	}
	*/
	

}