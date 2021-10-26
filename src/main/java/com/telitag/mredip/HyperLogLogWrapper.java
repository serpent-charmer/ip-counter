package com.telitag.mredip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HyperLogLogWrapper {

	private IHashProducer<String, Integer> hproducer;

	public HyperLogLogWrapper(IHashProducer<String, Integer> hproducer) {
		this.hproducer = hproducer;
	}

	public int calculate(String filename) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String s = null;
			while ((s = br.readLine()) != null) {
				hproducer.process(s);
			}
		}

		return hproducer.get();
	}
}
