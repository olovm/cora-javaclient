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
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CoraClientFactoryTest {
	private CoraClientImp coraClient;
	private String appTokenVerifierUrl;
	private String baseUrl;

	@BeforeMethod
	public void beforeMethod() {
		appTokenVerifierUrl = "someVerifierUrl";
		baseUrl = "someBaseUrl";
		CoraClientFactory clientFactory = new CoraClientFactory(appTokenVerifierUrl, baseUrl);
		String userId = "someUserId";
		String appToken = "someAppToken";
		coraClient = (CoraClientImp) clientFactory.factor(userId, appToken);

	}

	@Test
	public void testFactor() throws Exception {
		AppTokenClientFactory appTokenClientFactory = coraClient.getAppTokenClientFactory();
		AppTokenClientImp appTokenClient = (AppTokenClientImp) appTokenClientFactory.factor("", "");
		assertEquals(appTokenClient.getAppTokenVerifierUrl(),
				appTokenVerifierUrl + "rest/apptoken/");

		RestClientFactory restClientFactory = coraClient.getRestClientFactory();
		assertTrue(restClientFactory instanceof RestClientFactory);
		RestClientImp restClient = (RestClientImp) restClientFactory.factorUsingAuthToken("");
		assertEquals(restClient.getBaseUrl(), baseUrl);
	}

	@Test
	public void testFactorParametersSentAlong() throws Exception {
		assertEquals(coraClient.getUserId(), "someUserId");
		assertEquals(coraClient.getAppToken(), "someAppToken");
	}
}
