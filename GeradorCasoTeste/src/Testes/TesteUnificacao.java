package Testes;

import java.util.ArrayList;
import org.junit.Test;

import com.gct.finiteStateMachine.FiniteStateMachine;
import com.gct.finiteStateMachine.State;
import com.gct.finiteStateMachine.Transition;

public class TesteUnificacao {
	FiniteStateMachine fsm;
	
	private FiniteStateMachine fsm1()
	{
		FiniteStateMachine resultado = new FiniteStateMachine();
		
		State A = new State();
		State B = new State();
		State C = new State();
		State D = new State();
		State E = new State();
		State F = new State();
		
		A.setId("1");
		B.setId("2");
		C.setId("3");
		D.setId("4");
		E.setId("5");
		F.setId("6");
		
		A.setName("A");
		B.setName("B");
		C.setName("C");
		D.setName("D");
		E.setName("E");
		F.setName("F");
		
		ArrayList<State> estados = new ArrayList<State>();
		estados.add(A);
		estados.add(B);
		estados.add(C);
		estados.add(D);
		estados.add(E);
		estados.add(F);
		
		Transition T1 = new Transition(A, B, FiniteStateMachine.EPSILON, FiniteStateMachine.EPSILON);
		Transition T2 = new Transition(A, E, FiniteStateMachine.EPSILON, FiniteStateMachine.EPSILON);
		Transition T3 = new Transition(B, C, "Add 50", "True");
		Transition T4 = new Transition(C, D, "Verifica 50", "True");
		Transition T5 = new Transition(E, F, "Verifica 50", "True");
		ArrayList<Transition> trans = new ArrayList<Transition>();
		trans.add(T1);
		trans.add(T2);
		trans.add(T3);
		trans.add(T4);
		trans.add(T5);
		
		ArrayList<String> alfabetoEntrada = new ArrayList<String>();
		alfabetoEntrada.add("Add 50");
		alfabetoEntrada.add("Verifica 50");
		
		ArrayList<String> alfabetoSaida = new ArrayList<String>();
		alfabetoSaida.add("True");
		alfabetoSaida.add("False");
		
		resultado.setStates(estados);
		resultado.setFinalStates(estados);
		resultado.setInputAlphabet(alfabetoEntrada);
		resultado.setOutputAlphabet(alfabetoSaida);
		resultado.setName("FSM 1");
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
}
