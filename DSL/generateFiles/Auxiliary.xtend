package robots.tasks.generator

import robots.tasks.rDSL.State
import robots.tasks.rDSL.Automata
import robots.tasks.rDSL.Arrow
import robots.tasks.rDSL.Action
import org.eclipse.emf.ecore.resource.Resource
import java.util.List
import java.util.ArrayList

class Auxiliary {
	def static Automata getAutomata(Resource resource) {
		return resource.allContents.head as Automata 
 		// OR: resource.allContents.toIterable.filter(typeof(Planning)).head
 	}
 	
 	def static String getAutomataName(){
 		return Automata.name
 	}

	def static List<State> getStates(Resource resource) {
		return getAutomata(resource).statelist 
 	}

	//stub
	//returns name of s
	def static String getStateName(State s){}

	//return capitalized name of s (used for class names)
	def static String getBehaviorName(State s){}

	//stub
	//returns a unique int-id for state s
	def static int getStateNumber(State s){}
	
	//stub
	//returns the start state
	def static State getStartState(Resource resource) {}

	//stub
	//return a list of states, sorted on their priority
	//we need to change something in the grammar for this
	def static List<State> getSortedStates(Resource resource){}
	
 
 	def static List<Arrow> getArrows(Resource resource) {
		return getAutomata(resource).arrowlist 
 	}
	
	//je kan toch gewoon de empty list teruggeven als er geen incoming arrows zijn?
	//waarom null? 	
 	def static List<Arrow> getInArrows (Resource resource, State s){
 		var List<Arrow> arrowlist = new ArrayList<Arrow>() 
 		for (Arrow a : getArrows(resource)){ 
 			if(a.to == s){
 				arrowlist.add(a)
 			}
 		}
 		if(!arrowlist.isEmpty){	 
 			return arrowlist; 
 		} else { 
 			return null;
 		}
 	}
 	
 	def static List<Action> getActionList(State s) {
 				return s.actionlist; 
 	}
 }
