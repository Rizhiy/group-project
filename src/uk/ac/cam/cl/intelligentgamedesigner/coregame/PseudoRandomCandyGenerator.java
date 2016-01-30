package uk.ac.cam.cl.intelligentgamedesigner.coregame;

public class PseudoRandomCandyGenerator extends CandyGenerator {

	public PseudoRandomCandyGenerator(DesignParameters designParameters) {
		super(designParameters);
	}

	private static int curNum = 38, prime = 361, anotherPrime = 991;

	public static int nextPseudoRandom() {
		// System.out.println(curNum + " " + CandyColour.values().length);
		curNum = (curNum * prime) % anotherPrime;
		return curNum;
	}

	public Candy getCandy() {
		int result = nextPseudoRandom() % CandyColour.values().length;

		return new Candy(CandyColour.values()[result], CandyType.NORMAL);
	}

}