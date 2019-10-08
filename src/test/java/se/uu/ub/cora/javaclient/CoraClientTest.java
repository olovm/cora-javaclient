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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.javaclient.CoraClient;
import se.uu.ub.cora.javaclient.CoraClientException;
import se.uu.ub.cora.javaclient.CoraClientImp;
import se.uu.ub.cora.javaclient.doubles.AppTokenClientFactorySpy;
import se.uu.ub.cora.javaclient.doubles.RestClientFactorySpy;
import se.uu.ub.cora.javaclient.doubles.RestClientSpy;

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
		assertEquals(readJson, restClient.returnedAnswer + restClient.methodCalled);
		assertEquals(restClient.methodCalled, "read");

	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testReadError() throws Exception {
		coraClient.read(RestClientSpy.THIS_RECORD_TYPE_TRIGGERS_AN_ERROR, "someRecordId");
	}

	@Test
	public void testReadList() throws Exception {
		String readListJson = coraClient.readList("someType");
		RestClientSpy restClient = restClientFactory.factored.get(0);
		assertEquals(restClientFactory.factored.size(), 1);
		assertEquals(restClientFactory.usedAuthToken, "someAuthTokenFromSpy");
		assertEquals(restClient.recordType, "someType");
		assertEquals(readListJson, restClient.returnedAnswer + restClient.methodCalled);
		assertEquals(restClient.methodCalled, "readList");
	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testReadListError() throws Exception {
		coraClient.readList(RestClientSpy.THIS_RECORD_TYPE_TRIGGERS_AN_ERROR);
	}

	@Test
	public void testCreate() throws Exception {
		String json = "some fake json";
		String createdJson = coraClient.create("someType", json);
		RestClientSpy restClient = restClientFactory.factored.get(0);
		assertEquals(restClientFactory.factored.size(), 1);
		assertEquals(restClientFactory.usedAuthToken, "someAuthTokenFromSpy");
		assertEquals(restClient.recordType, "someType");
		assertEquals(restClient.json, json);
		assertEquals(createdJson, restClient.returnedAnswer + restClient.methodCalled);
		assertEquals(restClient.methodCalled, "create");
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
		assertEquals(restClient.recordType, "someType");
		assertEquals(restClient.recordId, "someId");
		assertEquals(restClient.json, json);
		assertEquals(updatedJson, restClient.returnedAnswer + restClient.methodCalled);
		assertEquals(restClient.methodCalled, "update");
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
		assertEquals(restClient.recordType, "someType");
		assertEquals(restClient.recordId, "someId");
		assertEquals(createdJson, restClient.returnedAnswer + restClient.methodCalled);
		assertEquals(restClient.methodCalled, "delete");
	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testDeleteError() throws Exception {
		coraClient.delete(RestClientSpy.THIS_RECORD_TYPE_TRIGGERS_AN_ERROR, "someId");
	}

	@Test
	public void testReadIncomingLinks() throws Exception {
		String readLinksJson = coraClient.readIncomingLinks("someType", "someId");
		RestClientSpy restClient = restClientFactory.factored.get(0);
		assertEquals(restClientFactory.factored.size(), 1);
		assertEquals(restClientFactory.usedAuthToken, "someAuthTokenFromSpy");
		assertEquals(restClient.recordType, "someType");
		assertEquals(restClient.recordId, "someId");
		assertEquals(readLinksJson, restClient.returnedAnswer + restClient.methodCalled);
		assertEquals(restClient.methodCalled, "readincomingLinks");
	}

	@Test(expectedExceptions = CoraClientException.class)
	public void testReadincomingLInksError() throws Exception {
		coraClient.readIncomingLinks(RestClientSpy.THIS_RECORD_TYPE_TRIGGERS_AN_ERROR, "someId");
	}
}
