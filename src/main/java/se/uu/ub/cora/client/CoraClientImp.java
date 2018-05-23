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

public class CoraClientImp implements CoraClient {

	private RestClientFactory restClientFactory;
	private AppTokenClient appTokenClient;

	public CoraClientImp(AppTokenClientFactory appTokenClientFactory,
			RestClientFactory restClientFactory, String userId, String appToken) {
		this.restClientFactory = restClientFactory;
		appTokenClient = appTokenClientFactory.factor(userId, appToken);
	}

	@Override
	public String create(String recordType, String json) {
		String authToken = appTokenClient.getAuthToken();

		RestClient restClient = restClientFactory.factorUsingAuthToken(authToken);
		return restClient.createRecordFromJson(recordType, json);
	}

}