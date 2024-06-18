package com.example.ioc;

import org.springframework.stereotype.Service;

@Service
public class EntornoImpl implements Entorno {

	private int count;
	
	public EntornoImpl(int countInit) {
		this.count = countInit;
	}

	@Override
	public void write(String texto) {
		System.out.println(texto);
		this.count++;
	}
	@Override
	public int getCount() {
		return this.count;
	}
}
