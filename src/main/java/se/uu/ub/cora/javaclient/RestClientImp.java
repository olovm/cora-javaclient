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

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpHandlerFactory;

public final class RestClientImp implements RestClient {
	private static final String RETURNED_ERROR_WAS = ". Returned error was: ";
	private static final String FROM_SERVER_USING_URL = " from server using url: ";
	private static final int CREATED = 201;
	private static final int OK = 200;
	private static final String APPLICATION_UUB_RECORD_JSON = "application/vnd.uub.record+json";
	private static final String ACCEPT = "Accept";
	private HttpHandlerFactory httpHandlerFactory;
	private String baseUrl;
	private String authToken;

	public static RestClientImp usingHttpHandlerFactoryAndBaseUrlAndAuthToken(
			HttpHandlerFactory httpHandlerFactory, String baseUrl, String authToken) {
		return new RestClientImp(httpHandlerFactory, baseUrl, authToken);
	}

	private RestClientImp(HttpHandlerFactory httpHandlerFactory, String baseUrl, String authToken) {
		this.httpHandlerFactory = httpHandlerFactory;
		this.baseUrl = baseUrl + "record/";
		this.authToken = authToken;
	}

	@Override
	public String readRecordAsJson(String recordType, String recordId) {
		String url = baseUrl + recordType + "/" + recordId;
		HttpHandler httpHandler = createHttpHandlerWithAuthTokenAndUrl(url);
		httpHandler.setRequestMethod("GET");

		Status statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		throw new CoraClientException("Could not read record of type: " + recordType + " and id: "
				+ recordId + FROM_SERVER_USING_URL + url + RETURNED_ERROR_WAS
				+ httpHandler.getErrorText());
	}

	private HttpHandler createHttpHandlerWithAuthTokenAndUrl(String url) {
		HttpHandler httpHandler = httpHandlerFactory.factor(url);
		httpHandler.setRequestProperty("authToken", authToken);
		return httpHandler;
	}

	HttpHandlerFactory getHttpHandlerFactory() {
		// needed for test
		return httpHandlerFactory;
	}

	String getBaseUrl() {
		// needed for test
		return baseUrl;
	}

	String getAuthToken() {
		// needed for test
		return authToken;
	}

	@Override
	public String createRecordFromJson(String recordType, String json) {
		String url = baseUrl + recordType;
		HttpHandler httpHandler = setUpHttpHandlerForPost(json, url);
		if (CREATED == httpHandler.getResponseCode()) {
			return httpHandler.getResponseText();
		}
		throw new CoraClientException("Could not create record of type: " + recordType
				+ FROM_SERVER_USING_URL + url + RETURNED_ERROR_WAS + httpHandler.getErrorText());
	}

	private HttpHandler setUpHttpHandlerForPost(String json, String url) {
		HttpHandler httpHandler = createHttpHandlerWithAuthTokenAndUrl(url);
		httpHandler.setRequestProperty(ACCEPT, APPLICATION_UUB_RECORD_JSON);
		httpHandler.setRequestProperty("Content-Type", APPLICATION_UUB_RECORD_JSON);
		httpHandler.setRequestMethod("POST");
		httpHandler.setOutput(json);
		return httpHandler;
	}

	@Override
	public String updateRecordFromJson(String recordType, String recordId, String json) {
		String url = baseUrl + recordType + "/" + recordId;
		HttpHandler httpHandler = setUpHttpHandlerForPost(json, url);
		if (OK == httpHandler.getResponseCode()) {
			return httpHandler.getResponseText();
		}
		throw new CoraClientException("Could not update record of type: " + recordType
				+ " with recordId: " + recordId + " on server using url: " + url
				+ RETURNED_ERROR_WAS + httpHandler.getErrorText());
	}

	@Override
	public String deleteRecord(String recordType, String recordId) {
		String url = baseUrl + recordType + "/" + recordId;
		HttpHandler httpHandler = createHttpHandlerWithAuthTokenAndUrl(url);
		httpHandler.setRequestMethod("DELETE");

		Status statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		throw new CoraClientException("Could not delete record of type: " + recordType + " and id: "
				+ recordId + FROM_SERVER_USING_URL + url + RETURNED_ERROR_WAS
				+ httpHandler.getErrorText());
	}

	@Override
	public String readRecordListAsJson(String recordType) {
		String url = baseUrl + recordType;
		HttpHandler httpHandler = createHttpHandlerWithAuthTokenAndUrl(url);
		httpHandler.setRequestMethod("GET");

		Status statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());
		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		throw new CoraClientException("Could not read records of type: " + recordType
				+ FROM_SERVER_USING_URL + url + RETURNED_ERROR_WAS + httpHandler.getErrorText());

	}

	@Override
	public String readIncomingLinksAsJson(String recordType, String recordId) {
		String url = baseUrl + recordType + "/" + recordId + "/incomingLinks";
		HttpHandler httpHandler = createHttpHandlerWithAuthTokenAndUrl(url);
		httpHandler.setRequestMethod("GET");
		Status statusType = Response.Status.fromStatusCode(httpHandler.getResponseCode());

		if (statusType.equals(Response.Status.OK)) {
			return httpHandler.getResponseText();
		}
		throw new CoraClientException("Could not read incoming links of type: " + recordType
				+ FROM_SERVER_USING_URL + url + RETURNED_ERROR_WAS + httpHandler.getErrorText());

	}

}
