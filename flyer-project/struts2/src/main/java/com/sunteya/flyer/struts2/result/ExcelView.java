/**
 * Created on 2007-12-15
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Sunteya
 *
 */
public interface ExcelView {
	void buildExcelDocument(HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
