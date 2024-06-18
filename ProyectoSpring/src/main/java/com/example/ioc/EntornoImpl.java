package com.example.ioc;


public class EntornoImpl implements Entorno {

	private int count;
	
	public EntornoImpl(int count) {
		this.count = count;
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
