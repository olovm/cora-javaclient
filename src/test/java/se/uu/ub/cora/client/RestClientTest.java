/*
 * Copyright 2015, 2016, 2018 Uppsala University Library
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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.externaldependenciesdoubles.HttpHandlerFactorySpy;

public class RestClientTest {
	private HttpHandlerFactorySpy httpHandlerFactorySpy;
	private RestClient restClient;

	@BeforeMethod
	public void setUp() {
		httpHandlerFactorySpy = new HttpHandlerFactorySpy();
		String baseUrl = "http://localhost:8080/therest/rest/";
		String authToken = "someToken";
		restClient = RestClientImp.usingHttpHandlerFactoryAndBaseUrlAndAuthToken(
				httpHandlerFactorySpy, baseUrl, authToken);
	}

	@Test
	public void testReadRecordHttpHandlerSetupCorrectly() {
		restClient.readRecordAsJson("someType", "someId");
		assertEquals(getRequestMethod(), "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId");
		assertEquals(getRequestProperty("authToken"), "someToken");
		assertEquals(getNumberOfRequestProperties(), 1);
	}

	@Test
	public void testReadRecordOk() {
		String json = restClient.readRecordAsJson("someType", "someId");
		assertEquals(json, "Everything ok");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = ""
			+ "Could not read record of type: someType and id: someId from server using "
			+ "url: http://localhost:8080/therest/rest/record/someType/someId. Returned error was: "
			+ "bad things happened")
	public void testReadRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		restClient.readRecordAsJson("someType", "someId");
	}

	@Test
	public void testReadRecordListHttpHandlerSetupCorrectly() {
		restClient.readRecordListAsJson("someType");
		assertEquals(getRequestMethod(), "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType");
		assertEquals(getRequestProperty("authToken"), "someToken");
		assertEquals(getNumberOfRequestProperties(), 1);
	}

	@Test
	public void testReadRecordListOk() {
		String json = restClient.readRecordListAsJson("someType");
		assertEquals(json, "Everything ok");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = ""
			+ "Could not read records of type: someType from server using "
			+ "url: http://localhost:8080/therest/rest/record/someType. Returned error was: "
			+ "bad things happened")
	public void testReadRecordListNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		restClient.readRecordListAsJson("someType");
	}

	@Test
	public void testCreateRecordHttpHandlerSetupCorrectly() throws Exception {
		httpHandlerFactorySpy.setResponseCode(201);

		String json = "{\"name\":\"value\"}";
		restClient.createRecordFromJson("someType", json);

		assertEquals(getRequestMethod(), "POST");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType");
		assertEquals(getRequestProperty("authToken"), "someToken");
		assertEquals(getRequestProperty("Accept"), "application/vnd.uub.record+json");
		assertEquals(getRequestProperty("Content-Type"), "application/vnd.uub.record+json");
		assertEquals(getNumberOfRequestProperties(), 3);

		assertEquals(getOutputString(), "{\"name\":\"value\"}");
	}

	@Test
	public void testCreateRecordOk() throws Exception {
		httpHandlerFactorySpy.setResponseCode(201);
		String json = "{\"name\":\"value\"}";
		String returnedJson = restClient.createRecordFromJson("someType", json);
		assertEquals(returnedJson, "Everything ok");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = ""
			+ "Could not create record of type: someType from server using "
			+ "url: http://localhost:8080/therest/rest/record/someType. Returned error was: "
			+ "bad things happened")
	public void testCreateRecordNotOk() throws Exception {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		String json = "{\"name\":\"value\"}";
		restClient.createRecordFromJson("someType", json);
	}

	@Test
	public void testUpdateRecordHttpHandlerSetupCorrectly() throws Exception {
		httpHandlerFactorySpy.setResponseCode(200);

		String json = "{\"name\":\"value\"}";
		restClient.updateRecordFromJson("someType", "someId", json);

		assertEquals(getRequestMethod(), "POST");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId");
		assertEquals(getRequestProperty("authToken"), "someToken");
		assertEquals(getRequestProperty("Accept"), "application/vnd.uub.record+json");
		assertEquals(getRequestProperty("Content-Type"), "application/vnd.uub.record+json");
		assertEquals(getNumberOfRequestProperties(), 3);

		assertEquals(getOutputString(), "{\"name\":\"value\"}");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = ""
			+ "Could not update record of type: someType with recordId: someId on server using "
			+ "url: http://localhost:8080/therest/rest/record/someType/someId. Returned error was: "
			+ "bad things happened")
	public void testUpdateRecordNotOk() throws Exception {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		String json = "{\"name\":\"value\"}";
		restClient.updateRecordFromJson("someType", "someId", json);
	}

	@Test
	public void testDeleteRecordHttpHandlerSetupCorrectly() throws Exception {
		httpHandlerFactorySpy.setResponseCode(200);

		restClient.deleteRecord("someType", "someId");

		assertEquals(getRequestMethod(), "DELETE");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId");
		assertEquals(getRequestProperty("authToken"), "someToken");
		assertEquals(getNumberOfRequestProperties(), 1);

	}

	@Test
	public void testDeleteRecordOk() throws Exception {
		httpHandlerFactorySpy.setResponseCode(200);
		String returnedJson = restClient.deleteRecord("someType", "someId");
		assertEquals(returnedJson, "Everything ok");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = ""
			+ "Could not delete record of type: someType and id: someId from server using "
			+ "url: http://localhost:8080/therest/rest/record/someType/someId. Returned error was: "
			+ "bad things happened")
	public void testDeleteRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		restClient.deleteRecord("someType", "someId");
	}

	@Test
	public void testReadIncomingLinksHttpHandlerSetupCorrectly() {
		restClient.readIncomingLinksAsJson("someType", "someId");
		assertEquals(getRequestMethod(), "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId/incomingLinks");
		assertEquals(getRequestProperty("authToken"), "someToken");
		assertEquals(getNumberOfRequestProperties(), 1);
	}

	@Test
	public void testReadIncomingLinksOk() {
		String json = restClient.readIncomingLinksAsJson("someType", "someId");
		assertEquals(json, "Everything ok");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = "Could not read "
			+ "incoming links of type: someType from server using "
			+ "url: http://localhost:8080/therest/rest/record/someType/someId/incomingLinks. "
			+ "Returned error was: bad things happened")
	public void testReadIncomingLinksNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		restClient.readIncomingLinksAsJson("someType", "someId");
	}

	private String getOutputString() {
		return httpHandlerFactorySpy.httpHandlerSpy.outputString;
	}

	private String getRequestMethod() {
		return httpHandlerFactorySpy.httpHandlerSpy.requestMetod;
	}

	private int getNumberOfRequestProperties() {
		return httpHandlerFactorySpy.httpHandlerSpy.requestProperties.size();
	}

	private String getRequestProperty(String key) {
		return httpHandlerFactorySpy.httpHandlerSpy.requestProperties.get(key);
	}
}
