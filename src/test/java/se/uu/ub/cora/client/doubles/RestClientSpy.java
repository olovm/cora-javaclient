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
package se.uu.ub.cora.client.doubles;

import se.uu.ub.cora.client.CoraClientException;
import se.uu.ub.cora.client.RestClient;

public class RestClientSpy implements RestClient {

	public static final String THIS_RECORD_TYPE_TRIGGERS_AN_ERROR = "thisRecordTypeTriggersAnError";
	public String recordType;
	public String recordId;
	public String returnedAnswer = "Answer from CoraRestClientSpy";

	public String readListUsingRecordType;
	public String readListUsingJson;
	public String returnedListAnswer = "List answer from RestClientSpy";

	public String createdUsingRecordType;
	public String createdUsingJson;
	public String returnedCreatedAnswer = "Created from RestClientSpy";

	public String updatedUsingRecordType;
	public String updatedUsingRecordId;
	public String updatedUsingJson;
	public String returnedUpdatedAnswer = "Updated from RestClientSpy";

	public String deletedUsingRecordType;
	public String deletedUsingRecordId;
	public String returnedDeletedAnswer = "Deleted from RestClientSpy";

	@Override
	public String readRecordAsJson(String recordType, String recordId) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.recordType = recordType;
		this.recordId = recordId;
		return returnedAnswer;
	}

	@Override
	public String createRecordFromJson(String recordType, String json) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		createdUsingRecordType = recordType;
		createdUsingJson = json;
		return returnedCreatedAnswer;
	}

	@Override
	public String updateRecordFromJson(String recordType, String recordId, String json) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		updatedUsingRecordType = recordType;
		updatedUsingRecordId = recordId;
		updatedUsingJson = json;
		return returnedUpdatedAnswer;
	}

	@Override
	public String deleteRecord(String recordType, String recordId) {
		deletedUsingRecordType = recordType;
		deletedUsingRecordId = recordId;
		return returnedDeletedAnswer;
	}

	@Override
	public String readRecordListAsJson(String recordType) {
		if (THIS_RECORD_TYPE_TRIGGERS_AN_ERROR.equals(recordType)) {
			throw new CoraClientException("Error from RestClientSpy");
		}
		this.readListUsingRecordType = recordType;
		return returnedListAnswer;
	}

}
