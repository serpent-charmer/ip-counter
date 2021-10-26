package com.telitag.mredip;

import java.io.File;
import java.io.IOException;

public class App  {



	public static void main(String[] args) {
		
		
		if(args.length < 1) {
			System.out.println("ipcount <file>");
		}
		
		File f = new File(args[0]);
		
		
		HyperLogLogWrapper hwrap = new HyperLogLogWrapper(new IHashProducer<String, Integer>() {

			private final HyperLogLogImpl impl = new HyperLogLogImpl(30);

			@Override
			public void process(String in) {
				impl.update(in);
			}

			@Override
			public Integer get() {
				return (int) impl.get();
			}

		});
		
		long card = 0;
		long l = System.currentTimeMillis();
		
		try {
			card = hwrap.calculate(f.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Error calculating cardinality");
			e.printStackTrace();
			System.exit(1);
		}
		
		long time = System.currentTimeMillis() - l;
		if (card == 0) {
			System.out.printf("No ips found in %s\n", f);
		} else {
			System.out.printf("TIME took to count all ips %dms\nFILE %s contains ~%d ips\n", time, f, card);
		}

		System.exit(0);

	}
}
