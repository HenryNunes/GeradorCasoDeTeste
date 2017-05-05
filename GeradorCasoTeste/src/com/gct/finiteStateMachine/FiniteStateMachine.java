//Observação:
//Esta classe foi convertida e adaptada do projeto em C#, algoritmos-mbt

package com.gct.finiteStateMachine;

import java.util.List;
import java.util.ArrayList;

public class FiniteStateMachine{
    /// EPSILON constant. Denotes empty sets.
    public static final String EPSILON = "ε";
    private ArrayList<State> finals;
    private ArrayList<String> inputAlphabet;
    private String nameUseCase;
    private ArrayList<String> outputAlphabet;
    private ArrayList<State> states;
    private String name;
    private State initialState;
    private ArrayList<Transition> transitions;
    private ArrayList<ArrayList<String>> wiSet;

    private List<State> finalStates;
   
    public FiniteStateMachine(String name){
        this.name = name;
        this.states = new ArrayList<State>();
        this.transitions = new ArrayList<Transition>();
        this.inputAlphabet = new ArrayList<String>();
        this.outputAlphabet = new ArrayList<String>();
        this.finals = new ArrayList<State>();
        this.wiSet = new ArrayList<ArrayList<String>>();
    }
    
    public FiniteStateMachine(){
    	this("");
    }

	public ArrayList<State> getFinals() {
		return finals;
	}

	public void setFinals(ArrayList<State> finals) {
		this.finals = finals;
	}

	public ArrayList<String> getInputAlphabet() {
		return inputAlphabet;
	}

	public void setInputAlphabet(ArrayList<String> inputAlphabet) {
		this.inputAlphabet = inputAlphabet;
	}

	public String getNameUseCase() {
		return nameUseCase;
	}

	public void setNameUseCase(String nameUseCase) {
		this.nameUseCase = nameUseCase;
	}

	public ArrayList<String> getOutputAlphabet() {
		return outputAlphabet;
	}

	public void setOutputAlphabet(ArrayList<String> outputAlphabet) {
		this.outputAlphabet = outputAlphabet;
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	public ArrayList<ArrayList<String>> getWiSet() {
		return wiSet;
	}

	public void setWiSet(ArrayList<ArrayList<String>> wiSet) {
		this.wiSet = wiSet;
	}

	public List<State> getFinalStates() {
		return finalStates;
	}

	public void setFinalStates(List<State> finalStates) {
		this.finalStates = finalStates;
	}

	public void addTransition(Transition transition){
		transitions.add(transition);
	}
	
    @Override
    public String toString()
    {
        String msg = "";
        if (!name.equals(""))
            msg += "\n" + name + ":\n";

        for(Transition t : transitions)
        {
            msg += "(" + t.getSourceState().getName() + ":" + t.getTargetState().getName() +
                "[" + t.getInput().toString() + ":" + t.getOutput().toString() + "])\n";
        }

        return msg;
    }
}