module se.uu.ub.cora.javaclient {
	requires transitive se.uu.ub.cora.httphandler;
	requires java.ws.rs;
	requires transitive se.uu.ub.cora.clientdata;

	exports se.uu.ub.cora.javaclient.apptoken;
	exports se.uu.ub.cora.javaclient.cora;
	exports se.uu.ub.cora.javaclient.rest;
}