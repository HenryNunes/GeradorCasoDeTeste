//Observação:
//Esta classe foi convertida e adaptada do projeto em C#, algoritmos-mbt

package com.gct.wp;

import com.gct.finiteStateMachine.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//Classe que recebe uma FSM e gera sequencia de teste utilizando o metodo Wp
public class Wp{
    //maquina onde serão armazenados os estados 
    private FiniteStateMachine fsm = null;
    //lista de sequencias final
    private ArrayList<String> listSequence;
    //transition cover (conjunto P)
    private ArrayList<ArrayList<String>> transitionCover;
    //state cover (conjunto Q)
    private ArrayList<ArrayList<String>> stateCover;
    //lista onde será concatenado as sequencias do W da máquina com as sequencias do StateCover
    private ArrayList<ArrayList<String>> listSequenceStateCoverAndWMachine;
    //conjunto R (Transition Cover - State Cover)
    private ArrayList<ArrayList<String>> listSequenceR;
    //dicionário que contém o conjunto Wi de cada estado da máquina
    private Map<String, ArrayList<ArrayList<String>>> wiMap;
    

    public Wp(FiniteStateMachine fsm){
    	this.listSequence = new ArrayList<>();
    	this.listSequenceStateCoverAndWMachine = new ArrayList<ArrayList<String>>();
    	this.listSequenceR = new ArrayList<ArrayList<String>>();
    	this.wiMap = new HashMap<>();
    	this.fsm = fsm;
    	this.stateCover = getSequenceStateCover(fsm);
    	this.transitionCover = getSequenceTransitionCover(fsm);
    }
    
    public Wp(){
    	this.listSequence = new ArrayList<>();
    	this.listSequenceStateCoverAndWMachine = new ArrayList<ArrayList<String>>();
    	this.listSequenceR = new ArrayList<ArrayList<String>>();
    	this.wiMap = new HashMap<>();
    }
    
	public ArrayList<String> getListSequence() {
		return listSequence;
	}

	public void setListSequence(ArrayList<String> listSequence) {
		this.listSequence = listSequence;
	}

	public ArrayList<ArrayList<String>> getListSequenceStateCoverAndWMachine() {
		return listSequenceStateCoverAndWMachine;
	}

	public void setListSequenceStateCoverAndWMachine(ArrayList<ArrayList<String>> listSequenceStateCoverAndWMachine) {
		this.listSequenceStateCoverAndWMachine = listSequenceStateCoverAndWMachine;
	}

	public ArrayList<ArrayList<String>> getListSequenceR() {
		return listSequenceR;
	}

	public void setListSequenceR(ArrayList<ArrayList<String>> listSequenceR) {
		this.listSequenceR = listSequenceR;
	}
    
    public ArrayList<ArrayList<String>> getTransitionCover() {
		return transitionCover;
	}

	public ArrayList<ArrayList<String>> getStateCover() {
		return stateCover;
	}

	public Map<String, ArrayList<ArrayList<String>>> getWiMap() {
		return wiMap;
	}

	//metodo Wp
    public ArrayList<ArrayList<String>> methodWp(){
    	//Primeira fase do metodo Wp.
        //adicionando nos estados wi e na máquina os conjunto W.
        //metodo que adicina Wi da máquina.
        setWMachine(fsm);
        //metodo que retorna uma lista de sequencia concatenada entre as sequencia de cada estado com W da maquina.
        //conjunto Q concatena W da FSM.
        listSequenceStateCoverAndWMachine = concatenatesSequenceStateCoverAndWMachine(fsm);
        //remove os valores préfixados
        removePreFixed(listSequenceStateCoverAndWMachine);

        //Segunda fase do metodo Wp
        //Lista das sequencias final.
        ArrayList<ArrayList<String>> rw = new ArrayList<ArrayList<String>>();
        //R (X) W ou R=  conjunto TransitionCover (P) - conjunto StateCover (Q);
        listSequenceR = transitionCoverLessStateCover(fsm);

        //método que concatena as sequencias  R com as sequencia Wi de cada estado.
        concatenateSequencesRandWStates(listSequenceR, fsm, rw);
        
        
        //revisar, não está removendo todas as sequencias
        removePreFixed(rw);
        return rw;
    }

    // concatena as sequencia R com as sequencia W de cada estado.
    private void concatenateSequencesRandWStates(ArrayList<ArrayList<String>> listSequenceR, FiniteStateMachine fsm, ArrayList<ArrayList<String>> rw){
        //para cada linha da sequencia R 
        for(ArrayList<String> line : listSequenceR){
            //caminha sobre a maquina com as entradas R par verificar se as sequencias estão corretas
            machineWalkthrough(line, fsm, rw);
        }
    }

    // metodo que caminha sobre a maquina com as sequencias R
    private void machineWalkthrough(ArrayList<String> seqLine, FiniteStateMachine fsm, ArrayList<ArrayList<String>> rw){
        //pega o estado inicial da maquina FSM.
        String s = fsm.getInitialState().getName();
        //Estado que guardará o ultimo estado visitado
        State lastState = null;
        //para cada sequencia R
        for(String seq : seqLine){
            //cria um estado com o nome
            State state = new State(s);
            //pega a transição onde o estado passado por parametro é origem e a sequencia (seq) é a mesma.
            Transition t = getTransitionStateSource(state, seq, fsm.getTransitions()).get(0);
            //caso a transição não e encontrada  retorna uma excessão.
            if (t == null)
            {
                throw new NullPointerException("Sequence null");
            }
            //pega o target da transição
            s = t.getTargetState().getName();
            //guarda o ultimo estado visitado.
            lastState = t.getTargetState();
        }
        //pega o Wi do estado final correspondente
        ArrayList<ArrayList<String>> wi = this.wiMap.get(lastState.getName());
        //concatena cada um dos valores do Wi com a transição que leva até este estado
        for(ArrayList<String> seq : wi){
        	ArrayList<String> aux = new ArrayList<>();
        	aux.addAll(seqLine);
        	aux.addAll(seq);
        	rw.add(aux);
        }
        
    }

    // método retorna a diferença entre os conj StateCover e conj TransitionCover.
    private ArrayList<ArrayList<String>> transitionCoverLessStateCover(FiniteStateMachine fsm){
        //lista de sequencia do transitionCover para todos os estados da FSM.
    	ArrayList<ArrayList<String>> listSequenceTransitionCover = new ArrayList<ArrayList<String>>();
    	for(ArrayList<String> sequence : this.transitionCover){
    		listSequenceTransitionCover.add(sequence);
    	}
        //lista de sequencia do StateCover para todos os estados da FSM.
        ArrayList<ArrayList<String>> listStateCover = getSequenceStateCover(fsm);

        //lista do resultado R =  TransitionCover - StateCover.
        ArrayList<ArrayList<String>> resultR = new ArrayList<ArrayList<String>>();

        //para cada sequencia da lista do Transitin Cover.
        for(int i = 0; i < listSequenceTransitionCover.size(); i++){
            //pega uma sequencia na posição i.
            ArrayList<String> line = listSequenceTransitionCover.get(i);
            //concatena as sequenciaS.
            String seq = "";
            //para cada linha.
            for (int j = 0; j < line.size(); j++){
                //concatena as sequencia na posição j.
                seq += line.get(j);
            }
            boolean contem = false;
            //para cada elemento (sequencia) 
            for (int l = 0; l < listStateCover.size(); l++){
                //pega a linha na lista na posição l.
                ArrayList<String> line2 = listStateCover.get(l);
                //String onde será concatenado as  sequencia de cada posição l.
                String seq2 = "";
                //para cada sequencia  da lista de sequencia do transitionCover.
                for (int k = 0; k < line2.size(); k++){
                    //pega a sequencia na posição k e concatena.
                    seq2 += line2.get(k);
                }
                if (seq.equals(seq2) && contem != true){
                    contem = true;
                }
            }
            if (!contem){
                resultR.add(line);
            }
        }
        //retorna a lista 
        return resultR;
    }

    // metodo que retorna uma lista de sequencia utilizando transitionCover. (ORIGINAL projeto C#)
    /*private ArrayList<ArrayList<String>> getSequenceTransitionCoverORIGINAL(FiniteStateMachine fsm){
        fsm = getTransition(fsm);
        //lista final do transitionCover;.
        ArrayList<ArrayList<String>> listSequenceTransitionCover = new ArrayList<ArrayList<String>>();
        //lista com sequencia VAZIA para ser adicionado na lista final.
        ArrayList<String> sequenceEmpty = new ArrayList<>();
        //adicona  na lista uma String vazia.
        sequenceEmpty.add("");
        //adiciona na lista final a lista com a string vazia.
        listSequenceTransitionCover.add(sequenceEmpty);
        //para cada estado da FSM
        for(State state : fsm.getStates()){
            //pega todas as sequencia de entrada que leve até o estado passado por paramentro
            //concatenado com os valores de entradas(s) da transição onde o estado, que é passado por paramentro
            //é origem.
            String[][] arr = transitionCover(state);
            //para cada sequencia.
            for(int i = 0; i < arr.length; i++){   //instaciando uma lista onde será adicionado as sequencia do TransitionCover.
                ArrayList<String> listAux = new ArrayList<String>();
                //para cada sequencia na pos (i)
                for(int j = 0; j < arr[i].length; j++){
                    //adicionan o elemento na pos i e j.
                    listAux.add(arr[i][j]);
                }
                //adiciona as sequencia na lista final do TrnasitionCover.
                listSequenceTransitionCover.add(listAux);
            }
        }
        //retorna a lista final com todas as sequencia da TransitionCover.
        return listSequenceTransitionCover;
    }

    private FiniteStateMachine getTransition(FiniteStateMachine fsm){
        for (int i = 0; i < fsm.getTransitions().size(); i++){
            Transition t = fsm.getTransitions().get(i);
            if (t.getOutput().equalsIgnoreCase("Falha")){
                fsm.getTransitions().remove(t);
                i--;
            }
        }
        return fsm;
    }*/
    
    // nova versão do gerador de transition cover (baseado em BFS [busca em largura])
    private ArrayList<ArrayList<String>> getSequenceTransitionCover(FiniteStateMachine fsm){
    	//lista que armazena os estados visitados pelo algoritmo
    	ArrayList<State> visitedStates = new ArrayList<>();
    	//mapa utilizado para guardar o caminho necessário para chegar em cada estado (dicionário de preâmbulos)
    	Map<State, ArrayList<String>> preambleMap = new HashMap<>();
    	//lista utilizada pelo algoritmo para fazer a pesquisa em largura
    	ArrayList<State> bfsList = new ArrayList<>();
    	//estado inicial da fsm
    	State initialState = fsm.getInitialState();
    	//lista de transições da fsm
    	ArrayList<Transition> transitions = fsm.getTransitions();
    	//lista onde será armazenado o transition cover
    	ArrayList<ArrayList<String>> transitionCoverList = new ArrayList<ArrayList<String>>();
    	
    	//estado inicial começa dentro das listas visitedStates e bfsList
    	visitedStates.add(initialState);
    	bfsList.add(initialState);
    	
    	//caminho necessário para chegar (preâmbulo) ao estado inicial sempre é vazio
    	ArrayList<String> initialPreamble = new ArrayList<>();
    	initialPreamble.add("");    	
    	preambleMap.put(initialState, initialPreamble );
    	
    	//coloca a transição vazia na lista do transition cover
    	transitionCoverList.add(initialPreamble);
    	
    	//enquanto a lista de pesquisa em largura não estiver vazia
    	while(bfsList.size() > 0){
    		//obtém o primeiro estado de bfsList e suas respectivas transições
    		State currentState = bfsList.remove(0);
    		ArrayList<Transition> currentStateTransitions = getTransitionStateSource(currentState, transitions);
    		
    		//itera sobre todas as transições deste estado (target)
    		for(Transition transition : currentStateTransitions){
    			State targetState = transition.getTargetState();
    			
    			//cria uma lista que armazenará o preâmbulo deste estado
    			ArrayList<String> targetStatePreamble = new ArrayList<>();
    			
    			//verifica se o target da transição está na lista de estados visitados
    			if(!visitedStates.contains(targetState)){
    				//caso não esteja, adiciona na lista de estados visitados e na lista bfs
    				visitedStates.add(targetState);
    				bfsList.add(targetState);
    				//copia o preâmbulo do estado source da transição que levou até o target para o preâmbulo do target
    				for(String entry : preambleMap.get(currentState)){
    					if(!entry.equals("")){
    						targetStatePreamble.add(entry);
    					}
    				}
    				//adiciona o input da transição no preâmbulo do target
    				targetStatePreamble.add(transition.getInput());
    				//coloca o preâmbulo do target no dicionário de preâmbulos
    				preambleMap.put(targetState, targetStatePreamble);
    				//adiciona o preâmbulo na lista do transition cover
    				transitionCoverList.add(targetStatePreamble);
    			} else{
    				//caso esteja, copia o preâmbulo do estado source da transição que levou até o target para o preâmbulo do target
	    			for(String entry : preambleMap.get(currentState)){
	    				if(!entry.equals("")){
    						targetStatePreamble.add(entry);
    					}
	    			}
	    			//adiciona o input da transição no preâmbulo do target
	    			targetStatePreamble.add(transition.getInput());
	    			//adiciona o preâmbulo na lista do transition cover
	    			transitionCoverList.add(targetStatePreamble);
	    			
	    			//OBSERVAÇÃO IMPORTANTE: aqui no "else", não adicionamos o preâmbulo do target no dicionário de preâmbulos,
	    			//pois o mesmo iria sobrescrever o preâmbulo que já existe no dicionário
    			}
    		}
    	}
    	return transitionCoverList;
    }
    
    // metodo que concatena as sequencia do StateCover com as sequencia W da máquina.
    private ArrayList<ArrayList<String>> concatenatesSequenceStateCoverAndWMachine(FiniteStateMachine fsm){
        //pega a sequencia do StateCover da máquina
        ArrayList<ArrayList<String>> list = getSequenceStateCover(fsm);
        //concatena as sequencia StateCover com W da máquina
        ArrayList<ArrayList<String>> listSequence = concatenateSequences(list, fsm.getWiSet());
        //retona a lista com as sequencia gerada do metodo acima
        return listSequence;
    }

    // metodo que recebe duas lista de sequencia e concatena
    private ArrayList<ArrayList<String>> concatenateSequences(ArrayList<ArrayList<String>> listStateCover, ArrayList<ArrayList<String>> listFsm){
        //lista final da concatenação das sequencia
        ArrayList<ArrayList<String>> listConcatenate = new ArrayList<ArrayList<String>>();
        //para cada sequencia da lista
        for(ArrayList<String> item : listStateCover){
            //cria uma lista de  sequencia
            ArrayList<String> seq = new ArrayList<String>();
            //para cada sequencia
            for(String s : item){
                //se a sequncia for diferente de vazio
                if(!s.equals("")){
                    //adiona na lista de sequencia
                    seq.add(s);
                }
            }
            //para cada sequencia da lista da FSM
            for(ArrayList<String> line : listFsm){
                //cria uma copia da lista
                ArrayList<String> listAux = copyList(seq);
                //para cada lista de sequencia
                for(String s1 : line){
                    //adiciona na lista
                    listAux.add(s1);
                }
                //concatena a sequncia na lista.
                listConcatenate.add(listAux);
            }
        }
        //retorna a sequencia final da concatenação da sequencia STATECOVER e W da máquina
        return listConcatenate;
    }
    
    // metodo para criar uma copia de uma lista.
    private ArrayList<String> copyList(ArrayList<String> seq){
        //cria uma lista de string
        ArrayList<String> list = new ArrayList<>();
        //para cada sequncia na lista
        for(String item : seq){
            //adiciona na lista
            list.add(item);
        }
        //retorna a lista
        return list;
    }

    // metodo que gera as sequencia do STATECOVER (original do projeto C#)
    /*private ArrayList<ArrayList<String>> getSequenceStateCoverORIGINAL(FiniteStateMachine fsm){
        //cria um a lista de string
        ArrayList<ArrayList<String>> listStateCover = new ArrayList<ArrayList<String>>();
        //cria uma lista de string par o conjunto VAZIO do primeiro estado
        ArrayList<String> stateCoverInitial = new ArrayList<>();
        //adiciona um string vazia na lista
        stateCoverInitial.add("");
        //adicona na lista do StateCover.
        listStateCover.add(stateCoverInitial);
        //para ccada State da FSM
        for(State state : fsm.getStates()){
            //cria uma lista de string
            ArrayList<String> list = new ArrayList<>();
            //busca o preambulo do estado passado por paramentro.
            String[] arr = preambulo(state);
            //para cada sequencia 
            for(int i = 0; i < arr.length; i++){
                //adiciona na lista 
                list.add(arr[i]);
            }
            //caso a lista for diferente de 0.
            if (list.size() != 0){
                //adicina na lista
                listStateCover.add(list);
            }
        }
        //retona a lista do STATECOVER.
        return listStateCover;
    }*/
    
    //nova versão do gerador de state cover (baseado em BFS [busca em largura])
    private ArrayList<ArrayList<String>> getSequenceStateCover(FiniteStateMachine fsm){
    	//lista que armazena os estados visitados pelo algoritmo
    	ArrayList<State> visitedStates = new ArrayList<>();
    	//mapa utilizado para guardar o "state cover" de cada estado
    	Map<State, ArrayList<String>> stateCoverMap = new HashMap<>();
    	//lista utilizada pelo algoritmo para fazer a pesquisa em largura
    	ArrayList<State> bfsList = new ArrayList<>();
    	//estado inicial da fsm
    	State initialState = fsm.getInitialState();
    	//lista de transições da fsm
    	ArrayList<Transition> transitions = fsm.getTransitions();
    	
    	//estado inicial começa dentro das listas visitedStates e bfsList
    	visitedStates.add(initialState);
    	bfsList.add(initialState);
    	
    	//"state cover" do estado inicial sempre é vazio
    	ArrayList<String> initialStateCover = new ArrayList<>();
    	initialStateCover.add("");    	
    	stateCoverMap.put(initialState, initialStateCover );
    	
    	//calcula "state cover" dos estados até que bfsList esteja vazia ou que todos os estados da FSM tenham sido visitados
    	while(bfsList.size() > 0 && visitedStates.size() != fsm.getStates().size()){
    		//obtém o primeiro estado de bfsList e suas respectivas transições
    		State currentState = bfsList.remove(0);
        	

    		ArrayList<Transition> currentStateTransitions = getTransitionStateSource(currentState, transitions);
    		
    		//itera sobre todas as transições deste estado
    		for(Transition transition : currentStateTransitions){
    			State targetState = transition.getTargetState();
    			//verifica se o target da transição está na lista de estados visitados
    			if(!visitedStates.contains(targetState)){
    				//caso não esteja, adiciona na lista de estados visitados e na lista bfs
    				visitedStates.add(targetState);
    				bfsList.add(targetState);
    				//cria uma lista que conterá o "state cover" do target state
    				ArrayList<String> targetStateCover = new ArrayList<>();
    				//obtém o "state cover" do estado source que levou ao target e adiciona
    				//ao "state cover" do target
    				for(String entry : stateCoverMap.get(currentState)){
    					if(!entry.equals("")){
    						targetStateCover.add(entry);
    					}
    				}
    				//coloca o "state cover" do target no dicionario de "state covers"
    				targetStateCover.add(transition.getInput());
    				stateCoverMap.put(targetState, targetStateCover);
    			}
    		}
    	}
    	//transforma o dicionário para o formato esperado pela FSM (Map<State, ArrayList<String>> PARA ArrayList<ArrayList<String>>)
    	ArrayList<ArrayList<String>> stateCoverList = new ArrayList<ArrayList<String>>();
    	//estou iterando sobre o visitedStates ao invés do dicionário para garantir que as menores sequências sejam mostradas antes
    	for(State state : visitedStates){
    		stateCoverList.add(stateCoverMap.get(state));
    	}
    	return stateCoverList;
    }

    // Este método obtem uma lista de pares de estado e uma lista com elementos do conjunto W.
    private void setWMachine(FiniteStateMachine fsm){
        //lista de pares de estado.
        ArrayList<StatePair> listStatePair = new ArrayList<>();
        //método que obtem os pares de estados.
        getListTransitionWi(fsm, listStatePair);     
        //metodo que remove os  pares iguais.
        removeEqualsState(listStatePair);
        //para cada par de estado da FSM.
        
        for(StatePair statePair : listStatePair){//adicionado posteriormente
            addMissingTransitions(statePair, fsm);
        }
        
        for(StatePair statePair : listStatePair){
            //obtem o conjunto W de cada par de estado.
            getWiStatePair(statePair, fsm);
        }
        //remove as sequências Wi repetidas.
        removeEqualsSequence(fsm);
    }

    // método que remove sequencia repitidas do conjunto W da Máquina
    private void removeEqualsSequence(FiniteStateMachine fsm)
    {

        for(int i = 0; i < fsm.getWiSet().size(); i++){
            ArrayList<String> line = fsm.getWiSet().get(i);
            String column = "";
            for (int j = 0; j < line.size(); j++){
                column += line.get(j);
            }
            for(int k = i + 1; k < fsm.getWiSet().size(); k++){
                ArrayList<String> line2 = fsm.getWiSet().get(k);
                String column2 = "";
                for(int l = 0; l < line2.size(); l++){
                    column2 += line2.get(l);
                }
                if (column.equals(column2)){
                    line2.remove(column2);
                    k--;
                    if (line2.size() == 0){
                        fsm.getWiSet().remove(line2);
                    }
                }
            }

        }
    }

    // remove duplicate String
    private void removePreFixed(ArrayList<ArrayList<String>> rw){
        for(int i = 0; i < rw.size(); i++){
            ArrayList<String> line = rw.get(i);
            for(int j = 0; j < rw.size(); j++){
            	if(j == i) continue;
                ArrayList<String> line2 = rw.get(j);
                if(line.size() <= line2.size()){
                    String lineAux1 = "";
                    String lineAux2 = "";
                    for(int k = 0; k < line.size(); k++){
                        lineAux1 += line.get(k);
                        lineAux2 += line2.get(k);
                    }
                    if (lineAux1.equals(lineAux2))
                    {
                        rw.remove(i);
                        i = 0;
                        line = rw.get(i);
                    }
                }
            }
        }
    }

	// método que popula a lista de SetW da FSM com conjunto W.
	private void getWiStatePair(StatePair statePair, FiniteStateMachine fsm) {
		//adiciona transições que faltam em cada estado (transições vazias para o próprio estado)
		addMissingTransitions(statePair, fsm);
		// gera Wi de cada par de estados e joga p/ fsm
		getListOutoutStatePair(statePair, fsm);
	}
    
    private void addMissingTransitions(StatePair statePair, FiniteStateMachine fsm){
    	//obtém as entradas dos dois estados
    	//System.err.println("Par de Estados: " + statePair);
    	ArrayList<String> entradasSi = new ArrayList<>(Arrays.asList(getAllowedInputs(statePair.getSi())));
    	//System.err.println("Si " + entradasSi);
    	ArrayList<String> entradasSj = new ArrayList<>(Arrays.asList(getAllowedInputs(statePair.getSj())));
    	//System.err.println("Sj " +entradasSi);
    	for(String entrada : fsm.getInputAlphabet()){
        	if(!entradasSi.contains(entrada)){
        		Transition transition = new Transition(statePair.getSi(), statePair.getSi(), entrada, FiniteStateMachine.EPSILON);
        		fsm.addTransition(transition);
        	}
        	if(!entradasSj.contains(entrada)){
        		Transition transition = new Transition(statePair.getSj(), statePair.getSj(), entrada, FiniteStateMachine.EPSILON);
        		fsm.addTransition(transition);
        	}
        }
    	//System.err.println("Trans: " + fsm.getTransitions());
    }
    
    private void getListOutoutStatePair(StatePair statePair, FiniteStateMachine fsm){
    	//formatações entre tipos de estruturas
    	ArrayList<ArrayList<String>> inputs = new ArrayList<ArrayList<String>>();
    	
    	String[] inputsAux = getAllowedInputs(statePair.getSi());
    	System.err.println("getAllowedInputs: " + inputsAux);
    	for(int i = 0; i < inputsAux.length; i++){
    		ArrayList<String> aux = new ArrayList<>();
    		aux.add(inputsAux[i]);
    		inputs.add(aux);
    	}

    	//neste momento, temos um ArrayList de ArrayList com as entradas, por exemplo:
    	//as entradas eram [x, y]
    	//agora são [[x], [y]]
    	
    	ArrayList<String> wi = new ArrayList<String>();

    	while(inputs.size() != 0){
    		System.err.println("Estados: " + statePair);
    		System.err.println("Tamanho Inputs: " + inputs.size());
    		ArrayList<String> input = inputs.get(0);
    		inputs.remove(0);
    		//input.remove(FiniteStateMachine.EPSILON);
    		System.err.println("tamanho input: " + input.size());
    		//System.err.println("input: " + input);
    		Transition t1 = stateWalk(statePair.getSi(), input);
		    Transition t2 = stateWalk(statePair.getSj(), input);
		    System.err.println("Comparação de Output: " + t1.getOutput().equals(t2.getOutput()));
		    System.err.println("Output1: " + t1.getOutput());
		    System.err.println("Output2: " + t2.getOutput());
		    //se a sequencia é aceita, coloca-a no wiSet da fsm e no dic de cada estado do par de estado
		    //if (!t1.getOutput().equals(t2.getOutput())){
		    if (!t1.equals(t2)){ 
		    	wi = input;
		        
		        //joga input no dic do estado Si do statePair
		        ArrayList<ArrayList<String>> wStateList = wiMap.get(statePair.getSi().getName());
		        if(wStateList == null){
		        	System.err.println("Parou 1");
		        	wStateList = new ArrayList<ArrayList<String>>();
		        	wiMap.put(statePair.getSi().getName(), wStateList);
		        }
		        //se o conjunto W do estado Si não possui input, adiciona
		        if(!wStateList.contains(input)){
		        	//precisa fazer essa cópia
		        	//caso contrario, o metodo removeEqualsSequence(fsm) apaga informações do hashmap
		        	//já que os itens que estão dentro deles são referências
		        	System.err.println("Parou 2");
		        	ArrayList<String> aux = new ArrayList<>(input);
		        	wStateList.add(aux);
		        }
	        	
	        	//joga input no dic do estado Sj do statePair
	        	wStateList = wiMap.get(statePair.getSj().getName());
		        if(wStateList == null){
		        	System.err.println("Parou 3");
		        	wStateList = new ArrayList<ArrayList<String>>();
		        	wiMap.put(statePair.getSj().getName(), wStateList);
		        }
		        //se o conjunto W do estado Sj não possui input, adiciona
		        if(!wStateList.contains(input)){
		        	//mesma coisa do trecho de cima
		        	System.err.println("Parou 4");
		        	ArrayList<String> aux = new ArrayList<>(input);
		        	wStateList.add(aux);
		        }	 
		        
		        break;
		    }
		    //caso a entrada não seja aceita, gera novas entradas baseadas nesta que não foi aceita
		    //e nas entradas padrão do estado. Ex: rejeita [x], gera [x, x] e [x, y]
		    else {
		    	System.err.println("Parou 5");
		    	for(String initialInput : inputsAux){
		    		System.err.println(initialInput);
		    		ArrayList<String> novaSeq = new ArrayList<>();
		    		novaSeq.addAll(input);
		    		novaSeq.add(initialInput);
		    		inputs.add(novaSeq);
		    	}
		    }
    	}  
    	
    	System.err.println("Fim do Loop");
    	fsm.getWiSet().add(wi);
    }
    
    
    private void getListTransitionWi(FiniteStateMachine fsm, List<StatePair> listStatePair){
        for(int i = 0; i < fsm.getStates().size(); i++){
            State s1 = fsm.getStates().get(i);
            for (int j = i + 1; j < fsm.getStates().size(); j++){
                State s2 = fsm.getStates().get(j);
                ArrayList<Transition> list = getTransitionStateSource(s1, fsm.getTransitions());
                for (int k = 0; k < list.size(); k++) {
					StatePair statePair = new StatePair();
                    statePair.setSi(s1);
                    statePair.setSj(s2);
                    listStatePair.add(statePair);
				}
            }
        }
    }

    // remove os pares de estados iguais.
    private void removeEqualsState(ArrayList<StatePair> listStatePair){
        for (int i = 0; i < listStatePair.size(); i++){
            StatePair s1 = listStatePair.get(i);
            for (int j = i + 1; j < listStatePair.size(); j++){
                StatePair s2 = listStatePair.get(j);
                if(isEquals(s1, s2)){
                    listStatePair.remove(s2);
                    j--;
                }
            }

        }
    }

    // método que verifica se os pares de estados são iguais.
    private boolean isEquals(StatePair s1, StatePair s2){
        if (s1.getSi().getName().equals(s2.getSi().getName()) && s1.getSj().getName().equals(s2.getSj().getName())){
            return true;
        }
        if (s1.getSi().getName().equals(s1.getSj().getName())){
            return true;
        }
        if (s2.getSi().getName().equals(s2.getSj().getName())){
            return true;
        }
        else
            return false;
    }
    
    // metodo que retorna uma lista de transição 
    // da FSM onde o estado passado por parametro é orgim na transição.
    private ArrayList<Transition> getTransitionStateSource(State s1, ArrayList<Transition> list){
        ArrayList<Transition> listTransition = new ArrayList<>();
        for(Transition t : list){
            if (t.getSourceState().getName().equals(s1.getName())){
                listTransition.add(t);
            }
        }
        return listTransition;
    }

    // método que retona uma lista de transição onde o estado for igual ao estado 
    // de origem na transição e o input for igual ao input da transição.
    private ArrayList<Transition> getTransitionStateSource(State s1, String input, ArrayList<Transition> list){
        ArrayList<Transition> listTransition = new ArrayList<Transition>();
        for(Transition t : list){
            if(t.getSourceState().getName().equals(s1.getName()) && t.getInput().equals(input)){
                listTransition.add(t);
            }
        }
		//System.err.println("getTransitionStateSource: " + listTransition.size());
		//System.err.println("getTransitionStateSource: " + listTransition.get(0).toString());
		//System.err.println("getTransitionStateSource: " + list.toString());

        return listTransition;
    }
    
    private Transition stateWalk(State initial, ArrayList<String> inputs){
    	State next = initial;
    	Transition transition = null;
    	System.err.println("WALK");
		System.err.println("estado: " +initial);
		System.err.println("inputs: " +inputs);
    	for(String input : inputs){
    		//System.err.println("error: " + next + " - " + input);
    		//System.err.println("error: " + getTransitionStateSource(next, input, fsm.getTransitions()).size() );

    		transition = getTransitionStateSource(next, input, fsm.getTransitions()).get(0);
    		next = transition.getTargetState();
    		//System.err.println("proximo: " + next);
    	}
    	System.err.println("transition: " + transition);
    	return transition;
    }

    // Shortcut for StateCover(State s, List-of-State visited) method.
    public String[] preambulo(State s){
        ArrayList<State> visitedStates = new ArrayList<>();
        // System.Diagnostics.Debug.WriteLineIf(debug, "Strinting in " + s.Name);
        visitedStates.add(s); //cannot walk through S
        return stateCover(s, visitedStates);
    }

    // Gets a preamble of a given state S.
    private String[] stateCover(State s, ArrayList<State> visited){
        //Initial State´s preamble is EPSILON
        if (s.getName().equals(fsm.getInitialState().getName()))
            return new String[] { };

        String[] shortestPreamble = null;
        String[] currentPreamble = null;
        String lastInput = "";

        //Get ancestors´ preambles
        //IEnumerable<Transition> filteredTransitions = fsm.Transitions.Where(x => x.TargetState.Name.Equals(s.Name));
        List<Transition> filteredTransitions = fsm.getTransitions().stream().filter(x -> x.getTargetState().getName().equals(s.getName())).collect(Collectors.toList());
        
        List<Transition> initialTransitions = fsm.getTransitions().stream().filter(x -> x.getSourceState().getName().equals(fsm.getInitialState().getName()) && x.getTargetState().getName().equals(s.getName())).collect(Collectors.toList());

        if (initialTransitions.size() > 0){
            currentPreamble = new String[1];
            lastInput = initialTransitions.get(0).getInput();
            currentPreamble[0] = lastInput;
            return currentPreamble;
        }
        else{
            for(Transition transition : filteredTransitions){

                if(!(visited.contains(transition.getSourceState())) && !(transition.getSourceState().getName().equals(transition.getTargetState().getName()))){
                    visited.add(transition.getSourceState());
                    currentPreamble = stateCover(transition.getSourceState(), visited);
                    if (currentPreamble != null && shortestPreamble == null){
                        shortestPreamble = currentPreamble;
                        lastInput = transition.getInput();
                    }
                }
            }
        }
        if (shortestPreamble == null)
            return null;

        //Creates a new preamble with size + 1
        String[] preamble = new String[shortestPreamble.length + 1];

        //Copy antecessor preamble to the new preable
        //shortestPreamble.copyTo(preamble, 0);
        for(int i = 0; i < shortestPreamble.length; i++){
        	preamble[i] = shortestPreamble[i];
        }
        
        //adds current transition input to current state S
        preamble[preamble.length - 1] = lastInput;

        return preamble;
    }
    
    // Gets the transition cover of given state S.
    /*private String[][] transitionCover(State s){
        //Concatenate preamble with allowed inputs.
        // debug = s.Name.Equals("4");
        String[] preamble = preambulo(s);
        String[] allowedInputs = getAllowedInputs(s);

        String[][] transitionCover = new String[allowedInputs.length][];

        for(int i = 0; i < allowedInputs.length; i++){
            String currentInput = allowedInputs[i];
            transitionCover[i] = new String[preamble.length + 1];

            for(int j = 0; j < preamble.length; j++)
                transitionCover[i][j] = preamble[j];

            transitionCover[i][preamble.length] = currentInput;
        }

        return transitionCover;
    }*/
    
    // Gets the allowed inputs list of a given state S.
    private String[] getAllowedInputs(State s){
    	List<String> inputs = fsm.getTransitions().stream().filter(x -> x.getSourceState().equals(s)).map(x -> x.getInput()).distinct().collect(Collectors.toList());
    	int size = inputs.size();
        String[] inputsArray = new String[size];
        for(int i = 0; i < size; i++){
        	inputsArray[i] = inputs.get(i);
        }
        return inputsArray;
    }
}