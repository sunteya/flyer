/**
 * Created on 2007-6-28
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.i18n.MessageCodeResolver;


/**
 * @author Sunteya
 *
 */
public class StringCodeResolver implements MessageCodeResolver {

	public MessageCode resolveMessageCode(Object obj) {
		List<String> codes = new ArrayList<String>();
		if(obj instanceof String) {
			codes.add(obj.toString());
		} else if (obj instanceof String[]) {
			codes.addAll(Arrays.asList((String[])obj));
		}

		return codes.isEmpty() ? null : new MessageCode(codes);
	}
}
