package com.springboot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.springboot.entity.ExcelData;
import com.springboot.entity.invoiceSample;
import com.springboot.service.DataService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DataController {
	
	@Autowired
	private DataService dataService;
	
	
	//Saving excel data to db
	@PostMapping(path = "/UploadExcelSheet/{dbName}")
	public ResponseEntity<?> importcsvtodb(@RequestParam("file") MultipartFile multipartFile, @PathVariable String dbName) throws IOException {
		InputStream stream = multipartFile.getInputStream();
		List<ExcelData> excelData = Poiji.fromExcel(stream, PoijiExcelType.XLSX, ExcelData.class);
		dataService.saveData(excelData, dbName);
		return ResponseEntity.ok(Map.of("Message", "Data Stored Successfully!!!"));
	}

	
	
	@GetMapping("/all/{dbName}")
	public ResponseEntity<List<JsonObject>> retirevEntitydata(@PathVariable String dbName){
		return new ResponseEntity<>(dataService.retrieveAll(dbName),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/db")
	public ResponseEntity<Set<String>> retirevDbCollections(){
		return new ResponseEntity<>(dataService.retrieveAllDb(),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping(path = "/UploadExcelSheetHash1/{dbName}")
	public ResponseEntity<?> valToHashmap1(
			@RequestParam("file") MultipartFile multipartFile,
			@PathVariable String dbName) throws IOException {
		Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		List<invoiceSample> invoiceSamples = new ArrayList<>();
		DataFormatter dataformatter = new DataFormatter();
		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			invoiceSample InvoiceSample = new invoiceSample();
			InvoiceSample.setId(Integer.parseInt(dataformatter.formatCellValue(row.getCell(0))));
			InvoiceSample.setProductName(dataformatter.formatCellValue(row.getCell(1)));
			InvoiceSample.setProductPrice(Double.parseDouble(dataformatter.formatCellValue(row.getCell(2))));
			InvoiceSample.setAvailability(dataformatter.formatCellValue(row.getCell(3)));
			invoiceSamples.add(InvoiceSample);
		}
		workbook.close();

		return ResponseEntity.ok(Map.of("Message", "Data Stored Successfully!!!"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping(path = "/UploadExcelSheetHash/{dbName}")
	public ResponseEntity<?> valToHashmap(@RequestParam("file") MultipartFile multipartFile,@PathVariable String dbName) throws IOException {
		 long starttime =System.currentTimeMillis();

			System.out.println("Time when data read start in list of POIJI: " + starttime );	
		List<Map<String, Object>> datalist=new ArrayList<>();    
	   Workbook workbook=WorkbookFactory.create(multipartFile.getInputStream());
	   Sheet sheet=workbook.getSheetAt(0);
	   Row headeRow=sheet.getRow(0);
	   List<String> headers=new ArrayList<>();
	   for(Cell cell:headeRow) {
		   headers.add(cell.getStringCellValue());	   
	   }
	   sheet.forEach(row->{
		   if(row.getRowNum()!=0) {
			   Map<String,Object> dataMap=new HashMap<>();
			   for(Cell cell:row) {
				   int cloumnIndex=cell.getColumnIndex();
				   if(cloumnIndex<headers.size()) {
				   String header = headers.get(cell.getColumnIndex());
				   Object value = getStringValue(cell);
				   dataMap.put(header, value);
			   }}
			   datalist.add(dataMap);
		   }
	   });
	    workbook.close();
	    long end =System.currentTimeMillis();
		System.out.println("Time when data read and stored in list of POIJI: " + end );	
		long elapsedtime=end-starttime;
		System.out.println("Total time required by poi: " + elapsedtime );
	    dataService.saveHash(datalist, dbName);
	    return ResponseEntity.ok(Map.of("Message", "Data Stored Successfully!!!"));		    
}
	
	
	
	public static LocalDate convertexcelDateToLocal(Date excelDate) {
		Instant instant=excelDate.toInstant();
		LocalDate localDate=instant.atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}
	
	private Object getStringValue(Cell cell) {
		Object value; 
		Workbook workbook=cell.getSheet().getWorkbook();
		FormulaEvaluator evaluator=workbook.getCreationHelper().createFormulaEvaluator();
		CellValue cellValue=evaluator.evaluate(cell);
		switch (cellValue.getCellType()) {

		case STRING:
			value= cell.getStringCellValue();
			break;
		case NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)) {
				value=cell.getDateCellValue();
				System.out.println(value);
			}
			else {
				BigDecimal decimal= BigDecimal.valueOf(cell.getNumericCellValue());
				BigInteger bigInteger=decimal.toBigInteger();
				value=bigInteger;	
			}
			break;
		case BOOLEAN:
			value=cell.getBooleanCellValue();
			break;
		case BLANK:
			value="";
			break;

		default:
			value=null;	
		}
		return value;
	}

	
	
	
	
	
	
}

/*	  long starttime =System.currentTimeMillis();	
  
long end =System.currentTimeMillis();
long elapsedtime=end-starttime;
System.out.println("Total time taken by POIJI: " + elapsedtime+" milliseconds" );
long end =System.currentTimeMillis();
	long elapsedtime=end-starttime;
	System.out.println("Total time taken by Apachepoi: " + elapsedtime+" milliseconds" );

*/
