package com.gct.utilities;

import com.gct.finiteStateMachine.FiniteStateMachine;

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
		
		resposta.append("public class TestJunit {\n\n");
		for(String s: fsm.getInputAlphabet())
		{
			resposta.append("@Test\n");
			resposta.append("\t" + s + "\n\t{\n");
			resposta.append("--TO DO");
			resposta.append("\tassertEquals(" + "true, " + s + ");\n");
			resposta.append("\t}\n\n");
		}
		resposta.append("}");
		return resposta.toString();
	}
	
}

