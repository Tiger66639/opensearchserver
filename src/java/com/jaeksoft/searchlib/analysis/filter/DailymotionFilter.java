/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2012 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of OpenSearchServer.
 *
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/
package com.jaeksoft.searchlib.analysis.filter;

import org.apache.lucene.analysis.TokenStream;

import com.jaeksoft.searchlib.Logging;
import com.jaeksoft.searchlib.SearchLibException;
import com.jaeksoft.searchlib.analysis.ClassPropertyEnum;
import com.jaeksoft.searchlib.analysis.FilterFactory;
import com.jaeksoft.searchlib.crawler.web.database.WebPropertyManager;
import com.jaeksoft.searchlib.crawler.web.spider.HttpDownloader;

public class DailymotionFilter extends FilterFactory {

	private int dailymotionData;

	@Override
	protected void initProperties() throws SearchLibException {
		super.initProperties();
		addProperty(ClassPropertyEnum.DAILYMOTION_DATA,
				ClassPropertyEnum.DAILYMOTION_DATA_LIST[0],
				ClassPropertyEnum.DAILYMOTION_DATA_LIST);
	}

	@Override
	protected void checkValue(ClassPropertyEnum prop, String value)
			throws SearchLibException {
		int i = 0;
		for (String v : ClassPropertyEnum.DAILYMOTION_DATA_LIST) {
			if (value.equals(v)) {
				dailymotionData = i;
				break;
			}
			i++;
		}
	}

	@Override
	public TokenStream create(TokenStream tokenStream) {
		WebPropertyManager propertyManager = null;
		HttpDownloader httpDownloader = null;
		try {
			propertyManager = config.getWebPropertyManager();
			httpDownloader = new HttpDownloader(propertyManager.getUserAgent()
					.getValue(), false, propertyManager.getProxyHandler());
		} catch (Exception e) {
			Logging.error(e);
		}

		return new DailymotionTokenFilter(tokenStream, dailymotionData,
				httpDownloader);
	}
}