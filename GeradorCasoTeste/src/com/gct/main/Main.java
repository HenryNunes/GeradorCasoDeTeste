//INSTRUÇÕES PARA UTILIZAR O PROGRAMA DENTRO E FORA DA IDE

//SIGA OS PASSOS ABAIXO PARA UTILIZAR O PROGRAMA NA IDE ECLIPSE:
//1. Clique na seta preta do botão "Run Main" (Botão verde de play).
//2. Clique em "Run Configurations".
//3. Clique na aba "Arguments".
//4. No campo "Program Arguments", informe o nome do arquivo .jff que será utilizado.
//Exemplo: teste1.jff
//
//Observação: O arquivo que deve ser informado para o programa deve ser gerado através do programa JFLAP.
//link: http://www.jflap.org/jflaptmp/
//A versão utilizada foi a 7.0
//Você pode baixar o arquivo "JFLAP_Thin.jar" do site.


//SIGA OS PASSOS ABAIXO PARA UTILIZAR O PROGRAMA FORA DA IDE:
//
//1. Com o console, vá até o diretório onde o executável do programa (GeradorWp.jar) encontra-se
//(caso o mesmo não tenha sido gerado, você pode gerá-lo a partir do Eclipse).
//
//2. Execute o seguinte comando:
//					
//		java -jar GeradorWp.jar "<nome do arquivo>"
//		
//	Onde <nome do arquivo> deve ser subtituido pelo arquivo .jff, por exemplo:
//	
//		java -jar GeradorWp.jar "teste1.jff"
//
//3. Uma mensagem informando que as sequências de teste foram geradas com sucesso irá aparecer na tela e o
//arquivo .txt contendo essas sequências será criado no mesmo diretório onde o executável encontra-se.
//
//Observação: O arquivo que deve ser informado para o programa deve ser gerado através do programa JFLAP.
//link: http://www.jflap.org/jflaptmp/
//A versão utilizada foi a 7.0
//Você pode baixar o arquivo "JFLAP_Thin.jar" do site.

package com.gct.main;

import java.util.ArrayList;

import com.gct.finiteStateMachine.FiniteStateMachine;
import com.gct.wp.Wp;
import com.gct.utilities.*;

public class Main{	
	public static void main(String args[]){
		FiniteStateMachine fsm = new FiniteStateMachine();
		
		//Invoca método do DomParser que lê o arquivo .xml e popula uma fsm com as informações lidas
		DomParser.parse(fsm, args[0]);
		
		//Inicia método Wp
		Wp wp = new Wp(fsm);
		ArrayList<ArrayList<String>> testSequences = null;
		
		try{
			testSequences =  wp.methodWp();
			String formattedTestSequences = Writer.testSequenceFormatter(testSequences);
			
			//verifica se o método wp gerou alguma sequencia
			if(!testSequences.get(0).get(0).equals("")){
				//extrai apenas o nome do arquivo informado para o programa
				String fileName = args[0].substring(0, args[0].lastIndexOf('.'));
				
				String text = "Método Wp:" + System.lineSeparator() + System.lineSeparator() +
							"Transition cover (conjunto P):" + System.lineSeparator() +
							Writer.sequenceFormatter(wp.getTransitionCover()) + System.lineSeparator() +
							"State cover (conjunto Q):" + System.lineSeparator() +
							Writer.sequenceFormatter(wp.getStateCover()) + System.lineSeparator() +
							"Conjunto R (P - Q):" + System.lineSeparator() +
							Writer.sequenceFormatter(wp.getListSequenceR()) + System.lineSeparator() +
							"Conjuntos Wi:" + System.lineSeparator() +  
							Writer.mapFormatter(wp.getWiMap()) +
							"Conjunto W:" + System.lineSeparator() + 
							Writer.sequenceFormatter(fsm.getWiSet()) + System.lineSeparator() +
							"Sequências de Teste:" + System.lineSeparator() +
							formattedTestSequences;
						
				
				//escreve informações no arquivo .txt
				Writer.writeToFile(text, "GeradorWp-" + fileName);
				
				System.out.println("Geradas Sequências de Teste e informações adicionais utilizando o Método Wp!");
			}
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		
	}
}