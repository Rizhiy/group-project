package uk.ac.cam.cl.intelligentgamedesigner.coregame;

public class PseudoRandomCandyGenerator extends CandyGenerator {

	public PseudoRandomCandyGenerator(DesignParameters designParameters) {
		super(designParameters);
	}

	private int curNum = 38, prime = 361, anotherPrime = 991;

	public int nextPseudoRandom() {
		// System.out.println(curNum + " " + CandyColour.values().length);
		curNum = (curNum * prime) % anotherPrime;
		return curNum;
	}

	public Candy getCandy() {
		int result = nextPseudoRandom() % CandyColour.values().length;
		/* if (CandyColour.values()[result] == CandyColour.GREEN)
			return new Candy(CandyColour.values()[result], CandyType.HORIZONTALLY_STRIPPED); */
		return new Candy(CandyColour.values()[result], CandyType.NORMAL);
	}

}
