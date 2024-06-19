package com.example.testing;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {

	private double redondea(double value) {
		return new BigDecimal(value).setScale(16,RoundingMode.HALF_UP).doubleValue();
	}
	double add(double a, double b) {
		return redondea(a + b);
	}
	double div(double a, double b) {
		if(b == 0) {
			throw new ArithmeticException(" / by 0");
		}
		return redondea(a / b);
	}
	double div(int a, int b) {
		if(b == 0) {
			throw new ArithmeticException(" / by 0");
		}
		return a / b;
	}
}
