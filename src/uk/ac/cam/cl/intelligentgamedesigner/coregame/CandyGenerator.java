package uk.ac.cam.cl.intelligentgamedesigner.coregame;

import java.io.Serializable;

public abstract class CandyGenerator implements Cloneable, Serializable {
    protected DesignParameters designParameters;

    public CandyGenerator (DesignParameters designParameters) {
    	this.designParameters = designParameters;
    }
    
    public abstract Candy generateCandy(int x);
}
