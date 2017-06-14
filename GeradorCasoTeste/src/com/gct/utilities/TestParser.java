package com.gct.utilities;

import java.util.ArrayList;

import com.gct.finiteStateMachine.FiniteStateMachine;
import com.gct.wp.Wp;

public class TestParser {

	//Singleton
	private static TestParser singleton = null;
	public static TestParser getInstance()
	{
		if(singleton == null)
		{
			singleton = new TestParser();
		}
		return singleton;
	}
	private TestParser()
	{

	}
	
	//Parser
	public String transformation(FiniteStateMachine fsm)
	{
		StringBuilder resposta = new StringBuilder();
		resposta.append("import org.junit.Test;\n");
		resposta.append("import static org.junit.Assert.assertEquals;\n");
		resposta.append("\n");
		
		resposta.append("public class TestJunit{\n");
		for(String s: fsm.getInputAlphabet())
		{
			resposta.append("\t@Test\n");
			resposta.append("\tpublic static _" + s + "()\n\t{\n");
			resposta.append("\t\t--TO DO\n");
			resposta.append("\t\tassertEquals(" + "true, _" + s + "());\n");
			resposta.append("\t}\n\n");
		}
		resposta.append("}");
		return resposta.toString();
	}
	public String testeCaminhoWp(FiniteStateMachine fsm)
	{
		if(fsm == null) return "";
		
		Wp wp = new Wp(fsm);
		ArrayList<ArrayList<String>> sequencias = wp.methodWp();
		
		
		StringBuilder resposta = new StringBuilder();
		resposta.append("import org.junit.Test;\n");
		resposta.append("import static org.junit.Assert.assertEquals;\n");
		resposta.append("\n");
		
		resposta.append("public class TestJunit{\n");
		int contador = 0;
		for(ArrayList<String> al: sequencias)
		{
			resposta.append("\t@Test\n");
			resposta.append("\tpublic static _" + Integer.toString(contador) + "()\n\t{\n");
			contador++;
			
			for(String s : al)
			{
				resposta.append("\t\tassertEquals(" + "true, _" + s + "());\n");	
			}
			resposta.append("\t}\n\n");
		}
		resposta.append("}");
		return resposta.toString();
	}
}

