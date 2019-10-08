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
package se.uu.ub.cora.javaclient.cora;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.javaclient.AppTokenClientImp;
import se.uu.ub.cora.javaclient.CoraClientImp;
import se.uu.ub.cora.javaclient.RestClientImp;
import se.uu.ub.cora.javaclient.apptoken.AppTokenClientFactory;
import se.uu.ub.cora.javaclient.rest.RestClientFactory;

public class CoraClientFactoryTest {
	private CoraClientImp coraClient;
	private String appTokenVerifierUrl;
	private String baseUrl;
	private CoraClientFactoryImp clientFactory;

	@BeforeMethod
	public void beforeMethod() {
		appTokenVerifierUrl = "someVerifierUrl";
		baseUrl = "someBaseUrl";
		clientFactory = CoraClientFactoryImp.usingAppTokenVerifierUrlAndBaseUrl(appTokenVerifierUrl,
				baseUrl);
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
		assertEquals(restClient.getBaseUrl(), baseUrl + "record/");
	}

	@Test
	public void testFactorParametersSentAlong() throws Exception {
		assertEquals(coraClient.getUserId(), "someUserId");
		assertEquals(coraClient.getAppToken(), "someAppToken");
	}

	@Test
	public void testGetAppTokenVerifierUrl() throws Exception {
		assertEquals(clientFactory.getAppTokenVerifierUrl(), appTokenVerifierUrl);
	}

	@Test
	public void testGetBaseUrl() throws Exception {
		assertEquals(clientFactory.getBaseUrl(), baseUrl);
	}
}
