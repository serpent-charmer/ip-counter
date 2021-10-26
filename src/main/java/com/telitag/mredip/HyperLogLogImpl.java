package com.telitag.mredip;

public class HyperLogLogImpl {

	private static final long Pow2_32 = 1L << 32;
	private final int b;
	private final int m;
	private final double alphaM;
	private byte[] M;

	public HyperLogLogImpl(int b) {
		this.b = b;
		this.m = 1 << b;
		this.alphaM = b == 4 ? 0.673 // m == 16
				: b == 5 ? 0.697 // m == 32
						: b == 6 ? 0.709 // m == 64
								: 0.7213 / (1 + 1.079 / m);
		this.M = new byte[m];
	}

	public void update(String value) {
		int x = hash(value);
		int j = x >>> (Integer.SIZE - b);
		M[j] = (byte) Math.max(M[j], rank((x << b) | (1 << (b - 1)) + 1));
	}

	public int hash(String value) {
		return MurMur32.hash32(value);
	}

	public double get() {
		double Z = 0.0;
		for (int i = 0; i < m; ++i)
			Z += 1.0 / (1 << M[i]);
		double E = alphaM * m * m / Z;

		if (E <= (5.0 / 2.0) * m) {
			int V = 0;
			for (int v : M)
				if (v == 0)
					V++;
			return V == 0 ? E : m * Math.log((float) m / V);
		} else if (E <= Pow2_32 / 30.0) {
			return E;
		} else {
			return -1 * Pow2_32 * Math.log(1.0 - E / Pow2_32);
		}
	}

	int rank(int w) {
		return w == 0 ? 0 : 1 + Integer.numberOfLeadingZeros(w);
	}
}
