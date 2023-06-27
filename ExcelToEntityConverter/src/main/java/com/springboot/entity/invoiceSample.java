package com.springboot.entity;

import org.springframework.data.domain.Sort;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class invoiceSample {
	
	@ExcelRow
	private int id;
	
	 @ExcelCell(0)  
	private String productName;
	
	 @ExcelCell(1) 
	private double productPrice;
	 
	 
	 @ExcelCell(2) 
	private String availability;

}





