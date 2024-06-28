package com.example.application.proxies;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.domains.contracts.proxies.CalculatorProxy;
import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;
import com.example.webservice.schema.DivideRequest;
import com.example.webservice.schema.DivideResponse;
import com.example.webservice.schema.MultiplyRequest;
import com.example.webservice.schema.MultiplyResponse;
import com.example.webservice.schema.SubtractRequest;
import com.example.webservice.schema.SubtractResponse;

public class CalculatorProxyImpl extends WebServiceGatewaySupport implements CalculatorProxy {
	 public double add(double a, double b) {
	        var request = new AddRequest();
	        request.setOp1(a);
	        request.setOp2(b);
	        var response = (AddResponse) getWebServiceTemplate().marshalSendAndReceive(request,
	                new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
	        return response.getAddResult();
	    }

	    public double subtract(double a, double b) {
	        var request = new SubtractRequest();
	        request.setOp1(a);
	        request.setOp2(b);
	        var response = (SubtractResponse) getWebServiceTemplate().marshalSendAndReceive(request,
	                new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
	        return response.getSubtractResult();
	    }

	    public double multiply(double a, double b) {
	        var request = new MultiplyRequest();
	        request.setOp1(a);
	        request.setOp2(b);
	        var response = (MultiplyResponse) getWebServiceTemplate().marshalSendAndReceive(request,
	                new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
	        return response.getMultiplyResult();
	    }

	    public double divide(double a, double b) {
	        var request = new DivideRequest();
	        request.setOp1(a);
	        request.setOp2(b);
	        var response = (DivideResponse) getWebServiceTemplate().marshalSendAndReceive(request,
	                new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
	        return response.getDivideResult();
	    }

}