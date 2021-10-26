package com.telitag.mredip;

public interface IHashProducer<I, O> {
	public void process(I in);
	public O get();
}
