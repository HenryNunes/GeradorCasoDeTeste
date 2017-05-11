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
		Transition T2 = new Transition(A, C, "0", "1");
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
		resultado.setName("FSM");
		resultado.setTransitions(trans);
		
		return resultado;
	}
	
	
	@Test
	public void teste1()
	{
		fsm = fsm1();
		System.err.print(fsm.toString());
		System.err.println("\nTeste 1");
		fsm = fsm.minimization();
		System.err.print(fsm.toString());
		
		
	}
	
	
	
}
