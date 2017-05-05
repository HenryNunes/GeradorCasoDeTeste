//Observação:
//Esta classe foi convertida e adaptada do projeto em C#, algoritmos-m

package com.gct.finiteStateMachine;

public class Transition{
	private State sourceState;
	private State targetState;
	private String input;
	private String output;
	
	public Transition(){
	}
	
	public Transition(State sourceState, State targetState, String input, String output){
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.input = input;
        this.output = output;
    }

	public State getSourceState() {
		return sourceState;
	}

	public void setSourceState(State sourceState) {
		this.sourceState = sourceState;
	}

	public State getTargetState() {
		return targetState;
	}

	public void setTargetState(State targetState) {
		this.targetState = targetState;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public String toString(){
		return sourceState.getName() + " -> " + targetState.getName();
	}
}
