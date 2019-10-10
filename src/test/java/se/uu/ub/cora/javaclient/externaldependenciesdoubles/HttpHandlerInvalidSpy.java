/*
 * Copyright 2017 Uppsala University Library
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

package se.uu.ub.cora.javaclient.externaldependenciesdoubles;

import java.io.InputStream;
import java.net.HttpURLConnection;

import se.uu.ub.cora.httphandler.HttpHandler;

public class HttpHandlerInvalidSpy implements HttpHandler {

	public HttpURLConnection urlConnection;

	public HttpHandlerInvalidSpy(HttpURLConnection urlConnection) {
		this.urlConnection = urlConnection;

	}

	public static HttpHandlerInvalidSpy usingURLConnection(HttpURLConnection urlConnection) {
		return new HttpHandlerInvalidSpy(urlConnection);
	}

	@Override
	public void setRequestMethod(String requestMetod) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getResponseText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResponseCode() {
		return 400;
	}

	@Override
	public void setOutput(String outputString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRequestProperty(String key, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getErrorText() {
		return "bad things happened";
	}

	@Override
	public void setStreamOutput(InputStream stream) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHeaderField(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBasicAuthorization(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

}
