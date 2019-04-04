package br.com.caelum.leilao.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LeilaoTeste {
	
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Smart TV 32");
		
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Rafael"), 200.0));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(200, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void recebeVariosLances() {
		Leilao leilao = new Leilao("Smartphone SS");
		
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("carlos");
		Usuario thais = new Usuario("thais");
		
		leilao.propoe(new Lance(carlos, 200.00));
		leilao.propoe(new Lance(rafael, 100.00));
		leilao.propoe(new Lance(thais, 500.00));
		leilao.propoe(new Lance(carlos, 250.00));
		
		assertEquals(4, leilao.getLances().size());
		assertEquals(200.0, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(100.0, leilao.getLances().get(1).getValor(), 0.00001);
		assertEquals(500.0, leilao.getLances().get(2).getValor(), 0.00001);
		assertEquals(250.0, leilao.getLances().get(3).getValor(), 0.00001);
		
	}
	
	@Test
	public void naoDeveAceitarLancesDuplicados() {
		
		Leilao leilao = new Leilao("Cafeteira");
		Usuario rafael = new Usuario("Rafael");
		
		leilao.propoe(new Lance(rafael, 200.0));
		leilao.propoe(new Lance(rafael, 550.0));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(200.0, leilao.getLances().get(0).getValor(), 0.00001);
		
	}
	
	@Test
	public void naoDeveAceitarMaisDe5LancesDeUmMesmoUsuario() {
		
		Leilao leilao = new Leilao("Notebook Dell");
		
		Usuario rafael = new Usuario("Rafael");
		Usuario carlos = new Usuario("Carlos");
		
		leilao.propoe(new Lance(rafael, 2000.0));
		leilao.propoe(new Lance(carlos, 1800.0));
		
		leilao.propoe(new Lance(rafael, 2500.0));
		leilao.propoe(new Lance(carlos, 3000.0));
		
		leilao.propoe(new Lance(rafael, 2900.0));
		leilao.propoe(new Lance(carlos, 3200.0));
		
		leilao.propoe(new Lance(rafael, 3250.0));
		leilao.propoe(new Lance(carlos, 3300.0));
		
		leilao.propoe(new Lance(rafael, 4000.0));
		leilao.propoe(new Lance(carlos, 3999.0));
		
		// esse lance devera ser desconsiderado
		leilao.propoe(new Lance(rafael, 6000.0));
		
		assertEquals(10, leilao.getLances().size());
		assertEquals(3999.0, leilao.getLances().get(leilao.getLances().size()-1).getValor(), 0.00001);
		
	}
	
}
