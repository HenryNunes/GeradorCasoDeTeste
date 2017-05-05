//Observação:
//Esta classe foi convertida e adaptada do projeto em C#, algoritmos-mbt

package com.gct.wp;

import com.gct.finiteStateMachine.*;

public class StatePair{
	private State si;
	private State sj;
	private String wi;
	
	public StatePair(){
		this.si = null;
		this.sj = null;
		this.wi = null;
	}
	
    public StatePair(State si, State sj, String wi) {
		super();
		this.si = si;
		this.sj = sj;
		this.wi = wi;
    }
    

    public State getSi() {
		return si;
	}


	public void setSi(State si) {
		this.si = si;
	}


	public State getSj() {
		return sj;
	}


	public void setSj(State sj) {
		this.sj = sj;
	}


	public String getWi() {
		return wi;
	}


	public void setWi(String wi) {
		this.wi = wi;
	}


	@Override
    public String toString(){
        return si.getName() + " - " + wi + " - " + sj.getName();
    }

}
