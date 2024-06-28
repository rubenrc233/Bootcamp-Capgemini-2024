package com.example.application.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.webservices.schemas.calculator.AddRequest;
import com.example.webservices.schemas.calculator.AddResponse;
import com.example.webservices.schemas.calculator.SubtractRequest;
import com.example.webservices.schemas.calculator.SubtractResponse;
import com.example.webservices.schemas.calculator.MultiplyRequest;
import com.example.webservices.schemas.calculator.MultiplyResponse;
import com.example.webservices.schemas.calculator.DivideRequest;
import com.example.webservices.schemas.calculator.DivideResponse;

@Endpoint
public class CalculatorEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/webservices/schemas/calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
    @ResponsePayload
    public AddResponse add(@RequestPayload AddRequest request) {
        var result = new AddResponse();
        result.setAddResult(request.getOp1() + request.getOp2());
        return result;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "subtractRequest")
    @ResponsePayload
    public SubtractResponse subtract(@RequestPayload SubtractRequest request) {
        var result = new SubtractResponse();
        result.setSubtractResult(request.getOp1() - request.getOp2());
        return result;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "multiplyRequest")
    @ResponsePayload
    public MultiplyResponse multiply(@RequestPayload MultiplyRequest request) {
        var result = new MultiplyResponse();
        result.setMultiplyResult(request.getOp1() * request.getOp2());
        return result;
    } 

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "divideRequest")
    @ResponsePayload
    public DivideResponse divide(@RequestPayload DivideRequest request) {
        var result = new DivideResponse();
        result.setDivideResult(request.getOp1() / request.getOp2());
        return result;
    }
}
