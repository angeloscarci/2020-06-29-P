package it.polito.tdp.PremierLeague.model;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Match, DefaultWeightedEdge> grafo;
	Map<Integer, Match> idMap;
	List<Adiacenze> archi;
	List<Match> percorso;

	public Model() {
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(int minutiGiocati, int mese) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer, Match>();
		Graphs.addAllVertices(this.grafo, dao.listaVertici(mese, idMap));
		System.out.println(this.grafo.vertexSet().size());
		archi = dao.getArchi(minutiGiocati, mese, idMap);
		for(Adiacenze a: archi) {
			Graphs.addEdge(this.grafo, a.getM1(), a.getM2(), a.getPeso());
		}
		System.out.println(this.grafo.edgeSet().size());
 	}
	
	
	
	public List<Match> listaMatchs(){
		List<Match> lista = new LinkedList<>();
		for(Match match : this.grafo.vertexSet()) {
			lista.add(match);
		}
		return lista;
	}
	
	public List<Adiacenze> getConnessioniMax(int minutiGiocati, int mese){
		double peso = 0;
		List<Adiacenze> lista = new LinkedList<>();
		for(Adiacenze a:archi) {
			if(a.getPeso()>peso) {
				peso = a.getPeso();
			}
		}
		for(Adiacenze a:archi) {
			if(a.getPeso() == peso) {
				lista.add(a);
			}
		}
		return lista;
	}
	
	public int getVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Match> getPercorso(Match partenza, Match arrivo){
		percorso = new ArrayList<Match>();
		List<Match> parziale = new ArrayList<Match>();
		parziale.add(partenza);
		cerca(parziale, arrivo, 0.0);
		return percorso;
		
	}

	private void cerca(List<Match> parziale, Match arrivo, double peso) {
		//condizione di terminazione
		if(parziale.get(parziale.size()-1).equals(arrivo)) {
			double pesoNuovo = this.calcolaPeso(parziale);
			if(pesoNuovo>peso) {
				peso = pesoNuovo;
				percorso.clear();
				percorso.addAll(parziale);
			}
			return;
		}else {
		//ricorsione
			for(Match vicino : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
				if(!parziale.contains(vicino)) {
					parziale.add(vicino);
					cerca(parziale, arrivo, peso);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}
	
	private double calcolaPeso(List<Match> parziale) {
		double peso = 0.0;
		for(int i = 0; i<parziale.size()-1; i++) {
			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i), parziale.get(i+1));
			peso = peso+this.grafo.getEdgeWeight(e);
		}
		return peso;
	}
}
