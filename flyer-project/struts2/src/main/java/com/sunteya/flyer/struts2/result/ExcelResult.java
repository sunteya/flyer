/**
 * Created on 2007-12-15
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Sunteya
 *
 */
public class ExcelResult extends AbstractResult {

	private static final long serialVersionUID = 2936993605494641610L;

	@Override
	protected void execute() throws Exception {
		getResponse().setContentType("application/excel");

		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelView view = (ExcelView) getActionInvocation().getAction();
		view.buildExcelDocument(workbook, getRequest(), getResponse());

		OutputStream out = getResponse().getOutputStream();
		workbook.write(out);
		out.close();
	}
}
