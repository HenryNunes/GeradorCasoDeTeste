//Observação:
//Esta classe foi convertida e adaptada do projeto em C#, algoritmos-mbt

package com.gct.finiteStateMachine;

import java.util.List;

import com.gct.utilities.Tupla;

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
	
	public FiniteStateMachine minimization() 
	{
		FiniteStateMachine resposta = new FiniteStateMachine();
		resposta.setName(this.name);
		resposta.setInputAlphabet(this.inputAlphabet);
		resposta.setOutputAlphabet(this.outputAlphabet);
		
		List<Tupla<State, State>> tuplas = listaTuplas(this.states);
		List<Tupla<State, State>> tuplasRemover = new ArrayList<Tupla<State, State>>();
		
		// Elimina tuplas 
		for(Tupla<State, State> t : tuplas)
		{
			// Que não sejam finais com finais
			if((finalStates.contains(t.getPrimeiro()) && !finalStates.contains(t.getSegundo())) ||
					(finalStates.contains(t.getPrimeiro()) && finalStates.contains(t.getSegundo())))
			{
				tuplasRemover.add(t);
				continue;
			}
			// Com output diefernte na transição
			for(String in : this.inputAlphabet)
			{
				String sPri = outputResultante(t.getPrimeiro(), in);
				String sSeg = outputResultante(t.getSegundo(), in);		
				if((sPri == null && sSeg != null) || (sPri != null && sSeg == null) )
				{
					tuplasRemover.add(t);
					break;
				}
				else if(sPri == null && sSeg== null)
				{
					continue;
				}
				else if(!sPri.equals(sSeg))
				{
					tuplasRemover.add(t);
					break;
				}
			}
		}
		tuplas.removeAll(tuplasRemover);
		
		// Recursão que remove tuplas que apontam para tuplas previamente removidas
		removerEstadosNãofundiveis(tuplas);
		System.err.println(tuplas.toString());
		
		return resposta;
	}
	private List<Tupla<State, State>> listaTuplas(List<State> lis)
	{
		List<Tupla<State, State>> resposta = new ArrayList<Tupla<State, State>>();
		for(int i = 0; i < lis.size();i++)
		{
			for(int b = i; i < lis.size();b++)
			{
				Tupla<State,State> temp = new Tupla<State,State>(lis.get(i),lis.get(b));
				resposta.add(temp);				
			}
		}
		return resposta;
	}
	private State fundirEstados(Tupla<State, State> t)
	{
		State resposta = new State();
		resposta.setId(t.getPrimeiro().getId() + t.getSegundo().getId());
		resposta.setName(t.getPrimeiro().getName() + t.getSegundo().getName());
		
		return resposta;
	}
	private State estadoResultante(State org, String input)
	{
		State resposta = null;
		for(Transition t : transitions)
		{
			if(org.equals(t.getSourceState()) && input.equals(t.getInput()))
			{
				resposta = t.getTargetState();
			}
		}
		return resposta;
	}
	private String outputResultante(State org, String input)
	{
		String resposta = null;
		for(Transition t : transitions)
		{
			if(org.equals(t.getSourceState()) && input.equals(t.getInput()))
			{
				resposta = t.getOutput();
			}
		}
		return resposta;
	}
	private void removerEstadosNãofundiveis(List<Tupla<State, State>> lis)
	{
		Tupla<State,State> remover = null;
		Loop:
		for(Tupla<State,State> t : lis )
		{
			for(String in : inputAlphabet)
			{
				State ePri = estadoResultante(t.getPrimeiro(), in);
				State eSeg = estadoResultante(t.getSegundo(), in);
				
				if(ePri == null && eSeg == null)continue;
				else if(ePri == null || eSeg == null )
				{
					remover = t;
					break Loop;
				}
				else if(lis.contains(new Tupla<State, State>(ePri, eSeg)))
				{
					remover = t;
					break Loop;
				}
			}
		}
		if(remover != null)
		{
			lis.remove(remover);
			this.removerEstadosNãofundiveis(lis);
		}		
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