package it.polito.tdp.borders.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private Map <Integer, Country> idMap;
	private Graph<Country, DefaultEdge> graph;
	List <Country> result;

	public Model() {
		dao = new BordersDAO();
		idMap = new HashMap <> ();
		dao.loadAllCountries(idMap);
	}
	
	public void creaGrafo(int anno) {
		graph = new SimpleGraph<>(DefaultEdge.class);
		List <Border> borders = dao.getCountryPairs(anno);
		
		for (Border b : borders) {
			Country c1 = idMap.get(b.getId1());
			Country c2 = idMap.get(b.getId2());
			
			if (!graph.containsVertex(c1))
				graph.addVertex(c1);
			if (!graph.containsVertex(c2))
				graph.addVertex(c2);
			
			if (!graph.containsEdge(c1, c2))
				graph.addEdge(c1, c2);
		}
	}
	
	public String vertexSetToString() {
		String str = "";
		for (Country c : graph.vertexSet()) {
			str += c.getName()+" "+graph.degreeOf(c)+"\n";
		}
		return str;
	}
 	
	public String getComponentiConnesse() {
		ConnectivityInspector ci = new ConnectivityInspector<Country, DefaultEdge>(graph);
		return "# componenti connessi: " + ci.connectedSets().size()+"\n";
	}
	
	public Collection <Country> getVertexSet () {
		return graph.vertexSet();
	}
	
	public List <Country> getConfinanti (Country c) {
		
		result = new ArrayList <> ();
		
		if (!graph.containsVertex(c))
			return null;
		
		
		result.add(c);
		BreadthFirstIterator <Country, DefaultEdge> bfi = new BreadthFirstIterator<Country, DefaultEdge>(graph, c);
		bfi.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				result.add(e.getVertex());
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		while (bfi.hasNext()) {
			bfi.next();
		}
		return result;
	}
	

}
