package se.uu.ub.cora.javaclient;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.converter.javatojson.DataToJsonConverter;
import se.uu.ub.cora.clientdata.converter.javatojson.DataToJsonConverterFactory;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;

public class DataToJsonConverterFactorySpy implements DataToJsonConverterFactory {

	public JsonBuilderFactory factory;
	public ClientDataElement clientDataElement;
	public DataToJsonConverterSpy converterSpy;

	@Override
	public DataToJsonConverter createForClientDataElement(JsonBuilderFactory factory,
			ClientDataElement clientDataElement) {
		this.factory = factory;
		this.clientDataElement = clientDataElement;
		converterSpy = new DataToJsonConverterSpy();
		return converterSpy;
	}

}
