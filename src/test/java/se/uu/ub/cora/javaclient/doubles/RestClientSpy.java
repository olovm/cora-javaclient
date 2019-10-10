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
package se.uu.ub.cora.javaclient.doubles;

import se.uu.ub.cora.javaclient.cora.CoraClientException;
import se.uu.ub.cora.javaclient.rest.RestClient;

public class RestClientSpy implements RestClient {

	public String returnedAnswer = "Answer from CoraRestClientSpy ";
	public static final String THIS_RECORD_TYPE_TRIGGERS_AN_ERROR = "thisRecordTypeTriggersAnError";
	public String recordType;
	public String recordId;
	public String json;
	public String methodCalled;

	@Override
	public String readRecordAsJson(String recordType, String recordId) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.recordType = recordType;
		this.recordId = recordId;
		methodCalled = "read";
		return returnedAnswer + methodCalled;
	}

	@Override
	public String createRecordFromJson(String recordType, String json) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.recordType = recordType;
		this.json = json;
		methodCalled = "create";
		return returnedAnswer + methodCalled;
	}

	@Override
	public String updateRecordFromJson(String recordType, String recordId, String json) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.recordType = recordType;
		this.recordId = recordId;
		this.json = json;
		methodCalled = "update";
		return returnedAnswer + methodCalled;
	}

	@Override
	public String deleteRecord(String recordType, String recordId) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.recordType = recordType;
		this.recordId = recordId;
		methodCalled = "delete";
		return returnedAnswer + methodCalled;
	}

	@Override
	public String readRecordListAsJson(String recordType) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.recordType = recordType;
		methodCalled = "readList";
		return returnedAnswer + methodCalled;
	}

	@Override
	public String readIncomingLinksAsJson(String recordType, String recordId) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.recordType = recordType;
		this.recordId = recordId;
		methodCalled = "readincomingLinks";
		return returnedAnswer + methodCalled;
	}

}
