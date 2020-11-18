package com.duco.tutorials.datageneration;

public class ModuloSkippingLineGenerator implements LineGenerator {

	private final int modulus;
	private final LineGenerator baseGenerator;
	private final int skipOffset;
	
	public ModuloSkippingLineGenerator(int modulus, int skipOffset, LineGenerator baseGenerator) {
		this.modulus = modulus;
		this.skipOffset = skipOffset;
		this.baseGenerator = baseGenerator;
	}

	@Override
	public String generateLine(int rowIndex) {
		if((rowIndex + skipOffset) % modulus == 0) {
			baseGenerator.generateLine(rowIndex);
		}
		return baseGenerator.generateLine(rowIndex);
	}

}
