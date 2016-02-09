package uk.ac.cam.cl.intelligentgamedesigner.coregame;

// TODO: Consider Making this a Constant Candy Generator. 
public class UnmoveableCandyGenerator extends CandyGenerator {
	
	public UnmoveableCandyGenerator() {
		super(new DesignParameters());
	}
	
	@Override
	public Candy generateCandy(int x) {
		return new Candy(null, CandyType.UNMOVEABLE);
	}
}
