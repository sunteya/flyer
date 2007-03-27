/**
 * Created on 2007-3-23
 * Created by Sunteya
 */
package com.sunteya.flyer.test.unitils;

import static org.unitils.thirdparty.org.apache.commons.io.IOUtils.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.unitils.core.UnitilsException;
import org.unitils.dbmaintainer.structure.impl.FlatXmlDataSetDtdGenerator;
import org.unitils.thirdparty.org.apache.commons.io.IOUtils;
import org.unitils.util.PropertyUtils;

/**
 * @author Sunteya
 *
 */
public class DefaultSchemaFlatXmlDataSetDtdGenerator extends FlatXmlDataSetDtdGenerator {

	private File dtdFile;

	public void generateDtd() {
		// generate content as a string
		String dtdContent = generateDtdContent();
		// make all elements optional
		dtdContent = StringUtils.replace(dtdContent, "#REQUIRED\n",	"#IMPLIED\n");
		writeDtdIfNeed(dtdContent);
	}

	protected void writeDtdIfNeed(String dtdContent) {
		Reader reader = null;
		Writer writer = null;
		try {

			if(isChangeDtd(dtdContent)) {
				writer = new FileWriter(dtdFile);
				write(dtdContent, writer);
			}
		} catch (Exception e) {
			throw new UnitilsException("Error generating DTD file: " + dtdFile.getName(), e);
		} finally {
			closeQuietly(writer);
			closeQuietly(reader);
		}
	}

	private boolean isChangeDtd(String dtdContent) {
		Reader reader = null;
		try {
			String oldContent = null;
			if(dtdFile.exists()) {
				reader = new FileReader(dtdFile);
				oldContent = IOUtils.toString(reader);
			}
			return !dtdContent.equals(oldContent);
		} catch (Exception e) {
			throw new UnitilsException("Error reader old DTD file: " + dtdFile.getName(), e);
		} finally {
			closeQuietly(reader);
		}
	}

	@Override
	protected void doInit(Properties configuration) {
		super.doInit(configuration);

		String dtdFileName = PropertyUtils.getString(PROPKEY_DTD_FILENAME, configuration);
		dtdFile = new File(dtdFileName);
		File parentDirectory = dtdFile.getParentFile();
		if (parentDirectory == null) {
			throw new UnitilsException("Error generating DTD file. Could not find parent directory for DTD file: " + dtdFileName);
		}
		parentDirectory.mkdirs();

	}
}
