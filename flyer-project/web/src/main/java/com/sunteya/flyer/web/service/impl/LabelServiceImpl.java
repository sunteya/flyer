/**
 * Created on 2007-3-8
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;

import com.sunteya.flyer.web.service.LabelService;

/**
 * @author Sunteya
 *
 */
public class LabelServiceImpl implements LabelService {

	private Map<String, SortedMap<String, String>> cache = Collections.synchronizedMap(new HashMap<String, SortedMap<String, String>>());
	private Resource resource;

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List<String> getLabelList(String name) {
		List<String> answer = new ArrayList<String>(getLabelMap(name).keySet());
		return answer;
	}

	public SortedMap<String, String> getLabelMap(String name) {
		if(cache.containsKey(name)) {
			return cache.get(name);
		}

		List<Label> labels = findLabels(name);
		SortedMap<String, String> answer = convertToMap(labels);
		cache.put(name, answer);

		return answer;
	}

	private SortedMap<String, String> convertToMap(List<Label> labels) {
		Map<String, Integer> orderMap = new HashMap<String, Integer>();
		Map<String, String> valueMap = new HashMap<String, String>();
		for(Label label : labels) {
			orderMap.put(label.getKey(), orderMap.size());
			valueMap.put(label.getKey(), label.getValue());
		}

		SortedMap<String, String> answer = new TreeMap<String, String>(new OrderMapComparator<String>(orderMap));
		answer.putAll(valueMap);
		return answer;
	}

	private List<Label> findLabels(String name) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));

			List<Label> answer = new ArrayList<Label>();
			while(true) {
				String line = reader.readLine();
				if(line == null) {
					break;
				}

				String clearLine = StringUtils.trimToEmpty(line);

				if(StringUtils.isBlank(clearLine)) {
					continue;
				}

				if(clearLine.startsWith("#")) {
					continue;
				}

				if(!clearLine.startsWith(name)) {
					continue;
				}

				String path = StringUtils.substringBefore(clearLine, "=");

				String key = path.substring(name.length() + 1);
				String value = StringUtils.substringAfter(clearLine, "=");

				Label label = new Label(key, value);
				answer.add(label);
			}

			return answer;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
