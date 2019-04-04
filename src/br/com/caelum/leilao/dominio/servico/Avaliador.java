package br.com.caelum.leilao.dominio.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {
	
	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private double valorMedio;
	private List<Lance> maiores;

	public void avalia(Leilao leilao) {
		
		Double soma = 0.0;
		
		for(Lance lance: leilao.getLances()) {
			soma += lance.getValor();
			
			if(lance.getValor() > maiorDeTodos) {
				maiorDeTodos  = lance.getValor();
			};
			
			if(lance.getValor() < menorDeTodos) {
				menorDeTodos  = lance.getValor();
			}
			
			
		}
		
		pegaOsMaioresNoLeilao(leilao);
		
		if(soma == 0) {
			valorMedio = 0;
			return;
		}
		valorMedio = soma/leilao.getLances().size();
		
	}
	
	private void pegaOsMaioresNoLeilao(Leilao leilao) {
		maiores = new ArrayList<>(leilao.getLances());
		
		Collections.sort(maiores, (l1, l2) -> {
			if(l1.getValor() < l2.getValor()) return 1;
			if(l1.getValor() > l2.getValor()) return -1;
			return 0;
		});
		
		maiores = maiores.subList(0, (maiores.size() > 3 ? 3 : maiores.size()));

	}
	
	public List<Lance> getTresMaiores(){
		return maiores;
	}
	
	public double getValorMedio() {
		return valorMedio ;
	}
	
	public double getMaiorLance() {
		return maiorDeTodos;
	}
	
	public double getMenorLance() {
		return menorDeTodos;
	}
	
}
