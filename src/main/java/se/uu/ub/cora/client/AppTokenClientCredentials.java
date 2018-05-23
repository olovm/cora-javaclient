package se.uu.ub.cora.client;

public class AppTokenClientCredentials {
	public String appTokenVerifierUrl;
	public String userId;
	public String appToken;

	public AppTokenClientCredentials(String appTokenVerifierUrl, String userId, String appToken) {
		this.appTokenVerifierUrl = appTokenVerifierUrl;
		this.userId = userId;
		this.appToken = appToken;
	}
}