/**
 * Created on 2007-8-30
 * Created by Sunteya
 */
package com.sunteya.flyer.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Sunteya
 *
 */
public class CsvUtils {

	public static String encodeLine(String[] sources) {
		return encodeLine(Arrays.asList(sources));
	}

	public static String encodeLine(Collection<String> sources) {
		StringWriter answer = new StringWriter();
		CSVWriter writer = new CSVWriter(answer);
		writer.writeNext(sources.toArray(new String[0]));

		return StringUtils.chomp(answer.toString());
	}

	public static String[] decodeLine(String text) {
		CSVReader reader = new CSVReader(new StringReader(text));
		try {
			return reader.readNext();
		} catch (IOException e) {
			throw new NestableRuntimeException(e);
		}
	}
}
