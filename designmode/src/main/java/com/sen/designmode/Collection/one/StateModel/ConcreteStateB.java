package com.sen.designmode.Collection.one.StateModel;

public class ConcreteStateB implements State {

	@Override
	public void handle(String sampleParameter) {

		System.out.println("ConcreteStateB handle ：" + sampleParameter);
	}

}