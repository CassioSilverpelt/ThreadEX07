package view;

import java.util.concurrent.Semaphore;

import controller.ThreadsCozinhaController;

public class ThreadsCozinhaPrincipal {
	public static void main (String[] args) {
		
		int fogoes = 3;
		Semaphore semaforo = new Semaphore(fogoes);
		Semaphore entrega = new Semaphore(1);
		
		for (int i = 0; i < 5; i++) {
			Thread cozCon = new ThreadsCozinhaController(i+1, semaforo, entrega);
			cozCon.start();
		}
		
	}
	

}
