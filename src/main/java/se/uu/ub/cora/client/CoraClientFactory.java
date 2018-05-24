package se.uu.ub.cora.client;

public interface CoraClientFactory {
	CoraClient factor(String userId, String appToken);
}
