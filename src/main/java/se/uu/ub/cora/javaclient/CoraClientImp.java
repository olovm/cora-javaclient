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

package se.uu.ub.cora.javaclient;

import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.javaclient.apptoken.AppTokenClient;
import se.uu.ub.cora.javaclient.apptoken.AppTokenClientFactory;
import se.uu.ub.cora.javaclient.cora.CoraClient;
import se.uu.ub.cora.javaclient.rest.RestClient;
import se.uu.ub.cora.javaclient.rest.RestClientFactory;

public class CoraClientImp implements CoraClient {

	private RestClientFactory restClientFactory;
	private AppTokenClient appTokenClient;
	private AppTokenClientFactory appTokenClientFactory;
	private String userId;
	private String appToken;

	public CoraClientImp(AppTokenClientFactory appTokenClientFactory,
			RestClientFactory restClientFactory, String userId, String appToken) {
		this.appTokenClientFactory = appTokenClientFactory;
		this.restClientFactory = restClientFactory;
		this.userId = userId;
		this.appToken = appToken;
		appTokenClient = appTokenClientFactory.factor(userId, appToken);
	}

	@Override
	public String create(String recordType, String json) {
		RestClient restClient = setUpRestClientWithAuthToken();
		return restClient.createRecordFromJson(recordType, json);
	}

	private RestClient setUpRestClientWithAuthToken() {
		String authToken = appTokenClient.getAuthToken();
		return restClientFactory.factorUsingAuthToken(authToken);
	}

	public AppTokenClientFactory getAppTokenClientFactory() {
		// needed for test
		return appTokenClientFactory;
	}

	public RestClientFactory getRestClientFactory() {
		// needed for test
		return restClientFactory;
	}

	public String getUserId() {
		// needed for test
		return userId;
	}

	public String getAppToken() {
		// needed for test
		return appToken;
	}

	@Override
	public String read(String recordType, String recordId) {
		RestClient restClient = setUpRestClientWithAuthToken();
		return restClient.readRecordAsJson(recordType, recordId);
	}

	@Override
	public String update(String recordType, String recordId, String json) {
		RestClient restClient = setUpRestClientWithAuthToken();
		return restClient.updateRecordFromJson(recordType, recordId, json);
	}

	@Override
	public String delete(String recordType, String recordId) {
		RestClient restClient = setUpRestClientWithAuthToken();
		return restClient.deleteRecord(recordType, recordId);
	}

	@Override
	public String readList(String recordType) {
		RestClient restClient = setUpRestClientWithAuthToken();
		return restClient.readRecordListAsJson(recordType);
	}

	@Override
	public String readIncomingLinks(String recordType, String recordId) {
		RestClient restClient = setUpRestClientWithAuthToken();
		return restClient.readIncomingLinksAsJson(recordType, recordId);
	}

	@Override
	public String create(String recordType, ClientDataGroup dataGroup) {
		// TODO Auto-generated method stub
		return null;
	}

}
