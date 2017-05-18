package Testes;

import java.util.ArrayList;
import org.junit.Test;

import com.gct.finiteStateMachine.FiniteStateMachine;
import com.gct.finiteStateMachine.State;
import com.gct.finiteStateMachine.Transition;

public class TesteMinimização {
	FiniteStateMachine fsm;
	
	private FiniteStateMachine fsm1()
	{
		FiniteStateMachine resultado = new FiniteStateMachine();
		
		State A = new State();
		State B = new State();
		State C = new State();
		State D = new State();
		State E = new State();
		
		A.setId("1");
		B.setId("2");
		C.setId("3");
		D.setId("4");
		E.setId("5");
		
		A.setName("A");
		B.setName("B");
		C.setName("C");
		D.setName("D");
		E.setName("E");
		
		ArrayList<State> estados = new ArrayList<State>();
		estados.add(A);
		estados.add(B);
		estados.add(C);
		estados.add(D);
		estados.add(E);
		
		Transition T1 = new Transition(A, D, "1", "1");
		Transition T2 = new Transition(A, E, "0", "1");
		Transition T3 = new Transition(B, E, "0", "1");
		Transition T4 = new Transition(B, D, "1", "1");
		Transition T5 = new Transition(C, D, "1", "1");
		Transition T6 = new Transition(C, E, "0", "1");
		Transition T7 = new Transition(D, E, "0", "1");
		Transition T8 = new Transition(D, B, "1", "0");
		Transition T9 = new Transition(E, C, "1", "1");
		ArrayList<Transition> trans = new ArrayList<Transition>();
		trans.add(T1);
		trans.add(T2);
		trans.add(T3);
		trans.add(T4);
		trans.add(T5);
		trans.add(T6);
		trans.add(T7);
		trans.add(T8);
		trans.add(T9);
		
		ArrayList<String> alfabeto = new ArrayList<String>();
		alfabeto.add("0");
		alfabeto.add("1");
		
		resultado.setStates(estados);
		resultado.setFinalStates(estados);
		resultado.setInputAlphabet(alfabeto);
		resultado.setOutputAlphabet(alfabeto);
		resultado.setName("FSM 1");
		resultado.setTransitions(trans);
		
		return resultado;
	}
	private FiniteStateMachine fsm2()
	{
		FiniteStateMachine resultado = new FiniteStateMachine();
		
		ArrayList<String> alfabeto = new ArrayList<String>();
		alfabeto.add("0");
		alfabeto.add("1");
		
		resultado.setInputAlphabet(alfabeto);
		resultado.setOutputAlphabet(alfabeto);
		
		State S0 = new State();
		State S1 = new State();
		State S2 = new State();
		State S3 = new State();
		State S4 = new State();
		State S5 = new State();
		
		S0.setId("1");
		S1.setId("2");
		S2.setId("3");
		S3.setId("4");
		S4.setId("5");
		S5.setId("6");
		
		S0.setName("S0");
		S1.setName("S1");
		S2.setName("S2");
		S3.setName("S3");
		S4.setName("S4");
		S5.setName("S5");
		
		ArrayList<State> estados = new ArrayList<State>();
		estados.add(S0);
		estados.add(S1);
		estados.add(S2);
		estados.add(S3);
		estados.add(S4);
		estados.add(S5);
		
		resultado.setStates(estados);
		resultado.setFinalStates(estados);
		resultado.setInitialState(S0);
		
		
		
		Transition T1 = new Transition(S0, S1, "0", "0");
		Transition T2 = new Transition(S0, S2, "1", "0");
		Transition T3 = new Transition(S1, S1, "1", "0");
		Transition T4 = new Transition(S1, S3, "0", "0");
		Transition T5 = new Transition(S2, S2, "1", "0");
		Transition T6 = new Transition(S2, S3, "0", "0");
		Transition T7 = new Transition(S3, S1, "1", "1");
		Transition T8 = new Transition(S3, S5, "0", "1");
		Transition T9 = new Transition(S4, S5, "0", "1");
		Transition T10 = new Transition(S5, S4, "1", "0");
		Transition T11 = new Transition(S5, S0, "0", "0");
		Transition T12 = new Transition(S4, S2, "1", "1");
		
		ArrayList<Transition> trans = new ArrayList<Transition>();
		trans.add(T1);
		trans.add(T2);
		trans.add(T3);
		trans.add(T4);
		trans.add(T5);
		trans.add(T6);
		trans.add(T7);
		trans.add(T8);
		trans.add(T9);
		trans.add(T10);
		trans.add(T11);
		trans.add(T12);
		
		resultado.setTransitions(trans);
		
		return resultado;
	}
	private FiniteStateMachine fsm3()
	{
		//http://aiju.de/code/k/fsm
		FiniteStateMachine resultado = new FiniteStateMachine();
		
		ArrayList<String> alfabeto = new ArrayList<String>();
		alfabeto.add("0");
		alfabeto.add("1");
		
		resultado.setInputAlphabet(alfabeto);
		resultado.setOutputAlphabet(alfabeto);
		
		State S0 = new State();
		State S1 = new State();
		State S2 = new State();
		State S3 = new State();
		State S4 = new State();
		State S5 = new State();
		State S6 = new State();
		
		S0.setId("1");
		S1.setId("2");
		S2.setId("3");
		S3.setId("4");
		S4.setId("5");
		S5.setId("6");
		S6.setId("7");
		
		S0.setName("S0");
		S1.setName("S1");
		S2.setName("S2");
		S3.setName("S3");
		S4.setName("S4");
		S5.setName("S5");
		S6.setName("S6");
		
		ArrayList<State> estados = new ArrayList<State>();
		estados.add(S0);
		estados.add(S1);
		estados.add(S2);
		estados.add(S3);
		estados.add(S4);
		estados.add(S5);
		estados.add(S6);
		
		resultado.setStates(estados);
		resultado.setFinalStates(estados);
		resultado.setInitialState(S0);
				
		
		Transition T1 = new Transition(S0, S1, "0", "0");
		Transition T2 = new Transition(S0, S2, "1", "0");
		Transition T3 = new Transition(S1, S3, "0", "0");
		Transition T4 = new Transition(S1, S4, "1", "0");
		Transition T5 = new Transition(S2, S5, "0", "0");
		Transition T6 = new Transition(S2, S6, "1", "0");
		Transition T7 = new Transition(S2, S5, "0", "0");
		Transition T8 = new Transition(S3, S0, FiniteStateMachine.EPSILON, "0");
		Transition T9 = new Transition(S4, S0, "1", "0");
		Transition T10 = new Transition(S4, S0, "0", "1");
		Transition T11 = new Transition(S5, S0, FiniteStateMachine.EPSILON, "0");
		Transition T12 = new Transition(S6, S0, "0", "1");
		Transition T13 = new Transition(S6, S0, "1", "0");
		

		
		ArrayList<Transition> trans = new ArrayList<Transition>();
		trans.add(T1);
		trans.add(T2);
		trans.add(T3);
		trans.add(T4);
		trans.add(T5);
		trans.add(T6);
		trans.add(T7);
		trans.add(T8);
		trans.add(T9);
		trans.add(T10);
		trans.add(T11);
		trans.add(T12);
		trans.add(T13);
		
		resultado.setTransitions(trans);
		
		return resultado;
		
	}
	
	
	@Test
	public void teste1()
	{
		System.err.println("\nTeste 1");
		fsm = fsm1();
		System.err.print(fsm.toString());
		fsm = fsm.minimization();
		System.err.print(fsm.toString());
		
		
	}
	
	@Test
	public void teste2()
	{
		System.err.println("\nTeste 2");
		fsm = fsm2();
		System.err.println(fsm.toString());
		fsm = fsm.minimization();
		System.err.print(fsm.toString());
		
	}
	
	@Test
	public void teste3()
	{
		System.err.println("\nTeste 3");
		fsm = fsm3();
		System.err.println(fsm.toString());
		fsm = fsm.minimization();
		System.err.print(fsm.toString());
		
	}
	
	
}
