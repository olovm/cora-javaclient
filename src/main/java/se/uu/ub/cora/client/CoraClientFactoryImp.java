/*
 * Copyright 2018 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.client;

public final class CoraClientFactoryImp implements CoraClientFactory {

	public static CoraClientFactoryImp usingAppTokenVerifierUrlAndBaseUrl(
			String appTokenVerifierUrl, String baseUrl) {
		return new CoraClientFactoryImp(appTokenVerifierUrl, baseUrl);
	}

	private AppTokenClientFactoryImp appTokenClientFactory;
	private RestClientFactoryImp restClientFactory;
	private String appTokenVerifierUrl;
	private String baseUrl;

	private CoraClientFactoryImp(String appTokenVerifierUrl, String baseUrl) {
		this.appTokenVerifierUrl = appTokenVerifierUrl;
		this.baseUrl = baseUrl;
		appTokenClientFactory = new AppTokenClientFactoryImp(appTokenVerifierUrl);
		restClientFactory = new RestClientFactoryImp(baseUrl);
	}

	@Override
	public CoraClient factor(String userId, String appToken) {
		return new CoraClientImp(appTokenClientFactory, restClientFactory, userId, appToken);
	}

	public String getAppTokenVerifierUrl() {
		// needed for test
		return appTokenVerifierUrl;
	}

	public String getBaseUrl() {
		// needed for test
		return baseUrl;
	}

}
