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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.testhelper.HttpHandlerFactorySpy;

public class AppTokenClientTest {
	private HttpHandlerFactorySpy httpHandlerFactorySpy;
	private String appTokenVerifierUrl = "http://localhost:8080/apptokenverifier/";
	private String appToken = "02a89fd5-c768-4209-9ecc-d80bd793b01e";
	private AppTokenClient appTokenClient;

	@BeforeMethod
	public void setUp() {
		httpHandlerFactorySpy = new HttpHandlerFactorySpy();
		httpHandlerFactorySpy.setResponseCode(201);
		appTokenClient = AppTokenClientImp.usingHttpHandlerFactoryAndAppTokenVerifierUrl(
				httpHandlerFactorySpy, appTokenVerifierUrl);
	}

	@Test
	public void testHttpHandlerSetupCorrectly() throws Exception {
		appTokenClient.getAuthToken("someUserId", appToken);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/apptokenverifier/rest/apptoken/someUserId");
		assertEquals(getRequestMethod(), "POST");
		assertEquals(getNumberOfRequestProperties(), 0);
		assertEquals(getOutputString(), appToken);
	}

	@Test
	public void testGetAuthToken() throws Exception {
		String authToken = appTokenClient.getAuthToken("someUserId", appToken);
		assertEquals(authToken, "a1acff95-5849-4e10-9ee9-4b192aef17fd");
	}

	@Test(expectedExceptions = CoraClientException.class, expectedExceptionsMessageRegExp = ""
			+ "Could not create authToken")
	public void testGetAuthTokenNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		appTokenClient.getAuthToken("someUserId", appToken);
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
	// @Test
	// public void testGetAuthTokenForAppToken() {
	// httpHandlerFactorySpy.setResponseCode(201);
	// fixture.setUserId("someUserId");
	// fixture.setAppToken("02a89fd5-c768-4209-9ecc-d80bd793b01e");
	// String json = fixture.getAuthTokenForAppToken();
	// HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
	// assertEquals(httpHandlerSpy.requestMetod, "POST");
	// assertEquals(httpHandlerSpy.outputString,
	// "02a89fd5-c768-4209-9ecc-d80bd793b01e");
	// assertEquals(httpHandlerFactorySpy.urlString,
	// "http://localhost:8080/apptokenverifier/rest/apptoken/someUserId");
	// assertEquals(json,
	// "{\"children\":[{\"name\":\"id\",\"value\":\"a1acff95-5849-4e10-9ee9-4b192aef17fd\"}"
	// +
	// ",{\"name\":\"validForNoSeconds\",\"value\":\"600\"}],\"name\":\"authToken\"}");
	// assertEquals(fixture.getAuthToken(), "a1acff95-5849-4e10-9ee9-4b192aef17fd");
	// assertEquals(fixture.getStatusType(), Response.Status.CREATED);
	// }
}
