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

import se.uu.ub.cora.client.doubles.AppTokenClientFactorySpy;
import se.uu.ub.cora.client.doubles.RestClientFactorySpy;
import se.uu.ub.cora.client.doubles.RestClientSpy;

public class CoraClientTest {
	private CoraClient coraClient;
	private RestClientFactorySpy restClientFactory;
	private AppTokenClientFactorySpy appTokenClientFactory;
	private String userId = "someUserId";
	private String appToken = "someAppToken";

	@BeforeMethod
	public void BeforeMethod() {
		restClientFactory = new RestClientFactorySpy();
		appTokenClientFactory = new AppTokenClientFactorySpy();
		coraClient = new CoraClientImp(appTokenClientFactory, restClientFactory, userId, appToken);
	}

	@Test
	public void testInit() throws Exception {
		assertEquals(appTokenClientFactory.factored.size(), 1);
		String usedUserId = appTokenClientFactory.usedUserId.get(0);
		assertEquals(usedUserId, userId);
		String usedAppToken = appTokenClientFactory.usedAppToken.get(0);
		assertEquals(usedAppToken, appToken);
	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testInitErrorWithAuthToken() throws Exception {
		CoraClientImp coraClient = new CoraClientImp(appTokenClientFactory, restClientFactory,
				AppTokenClientFactorySpy.THIS_USER_ID_TRIGGERS_AN_ERROR, appToken);
		String json = "some fake json";
		coraClient.create("someType", json);
	}

	@Test
	public void testRead() throws Exception {
		String readJson = coraClient.read("someType", "someId");
		RestClientSpy restClient = restClientFactory.factored.get(0);
		assertEquals(restClientFactory.factored.size(), 1);
		assertEquals(restClientFactory.usedAuthToken, "someAuthTokenFromSpy");
		assertEquals(restClient.recordType, "someType");
		assertEquals(restClient.recordId, "someId");
		assertEquals(readJson, restClient.returnedAnswer);
	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testReadError() throws Exception {
		coraClient.read(RestClientSpy.THIS_RECORD_TYPE_TRIGGERS_AN_ERROR, "someRecordId");
	}

	@Test
	public void testCreate() throws Exception {
		String json = "some fake json";
		String createdJson = coraClient.create("someType", json);
		RestClientSpy restClient = restClientFactory.factored.get(0);
		assertEquals(restClientFactory.factored.size(), 1);
		assertEquals(restClientFactory.usedAuthToken, "someAuthTokenFromSpy");
		assertEquals(restClient.createdUsingRecordType, "someType");
		assertEquals(restClient.createdUsingJson, json);
		assertEquals(createdJson, restClient.returnedCreatedAnswer);
	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testCreateError() throws Exception {
		String json = "some fake json";
		coraClient.create(RestClientSpy.THIS_RECORD_TYPE_TRIGGERS_AN_ERROR, json);
	}

	@Test
	public void testUpdate() throws Exception {
		String json = "some fake json";
		String updatedJson = coraClient.update("someType", "someId", json);
		RestClientSpy restClient = restClientFactory.factored.get(0);
		assertEquals(restClientFactory.factored.size(), 1);
		assertEquals(restClientFactory.usedAuthToken, "someAuthTokenFromSpy");
		assertEquals(restClient.updatedUsingRecordType, "someType");
		assertEquals(restClient.updatedUsingRecordId, "someId");
		assertEquals(restClient.updatedUsingJson, json);
		assertEquals(updatedJson, restClient.returnedUpdatedAnswer);
	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testUpdateError() throws Exception {
		String json = "some fake json";
		coraClient.update(RestClientSpy.THIS_RECORD_TYPE_TRIGGERS_AN_ERROR, "someId", json);
	}

	@Test
	public void testDelete() {
		String createdJson = coraClient.delete("someType", "someId");
		RestClientSpy restClient = restClientFactory.factored.get(0);
		assertEquals(restClientFactory.factored.size(), 1);
		assertEquals(restClientFactory.usedAuthToken, "someAuthTokenFromSpy");
		assertEquals(restClient.deletedUsingRecordType, "someType");
		assertEquals(restClient.deletedUsingRecordId, "someId");
		assertEquals(createdJson, restClient.returnedDeletedAnswer);
	}

}
