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

import se.uu.ub.cora.httphandler.HttpHandler;
import se.uu.ub.cora.httphandler.HttpHandlerFactory;

public final class AppTokenClientImp implements AppTokenClient {

	private static final int CREATED = 201;
	private static final int DISTANCE_TO_START_OF_TOKEN = 21;
	private HttpHandlerFactory httpHandlerFactory;
	private String appTokenVerifierUrl;

	public static AppTokenClientImp usingHttpHandlerFactoryAndAppTokenVerifierUrl(
			HttpHandlerFactory httpHandlerFactory, String appTokenVerifierUrl) {
		return new AppTokenClientImp(httpHandlerFactory, appTokenVerifierUrl);
	}

	private AppTokenClientImp(HttpHandlerFactory httpHandlerFactory, String appTokenVerifierUrl) {
		this.httpHandlerFactory = httpHandlerFactory;
		this.appTokenVerifierUrl = appTokenVerifierUrl + "rest/apptoken/";
	}

	@Override
	public String getAuthToken(String userId, String appToken) {
		HttpHandler httpHandler = createHttpHandler(userId);
		createAuthTokenUsingHttpHandler(appToken, httpHandler);
		return possiblyGetAuthToken(httpHandler);
	}

	private HttpHandler createHttpHandler(String userId) {
		return httpHandlerFactory.factor(appTokenVerifierUrl + userId);
	}

	private void createAuthTokenUsingHttpHandler(String appToken, HttpHandler httpHandler) {
		httpHandler.setRequestMethod("POST");
		httpHandler.setOutput(appToken);
	}

	private String possiblyGetAuthToken(HttpHandler httpHandler) {
		if (CREATED == httpHandler.getResponseCode()) {
			return getAuthToken(httpHandler);
		}
		throw new CoraClientException("Could not create authToken");
	}

	private String getAuthToken(HttpHandler httpHandler) {
		String responseText = httpHandler.getResponseText();
		return extractCreatedTokenFromResponseText(responseText);
	}

	private String extractCreatedTokenFromResponseText(String responseText) {
		int idIndex = responseText.lastIndexOf("\"name\":\"id\"") + DISTANCE_TO_START_OF_TOKEN;
		return responseText.substring(idIndex, responseText.indexOf('"', idIndex));
	}

}
