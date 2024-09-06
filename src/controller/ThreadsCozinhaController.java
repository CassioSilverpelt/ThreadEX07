package controller;

import java.util.concurrent.Semaphore;

public class ThreadsCozinhaController extends Thread {
	
	int tID = (int) threadId();
	int pedido;
	int tempo;
	int percent;
	private Semaphore semaforo;
	private Semaphore entrega;
	
	public ThreadsCozinhaController(int pedido, Semaphore semaforo, Semaphore entrega) {
		this.pedido = pedido;
		this.semaforo = semaforo;
		this.entrega = entrega;
	}
	
	@Override
	public void run() {
		cozinhar();
	}

	private void cozinhar() {
		try {
			semaforo.acquire();
		
			if (tID % 2 == 0) {
				System.out.println("Pedido nº " + pedido + ": Lasanha à Bolonhesa.");
				pedido (pedido, 800, 500);
			} else {
				System.out.println("Pedido nº " + pedido + ": Sopa de Cebola.");
				pedido (pedido, 1200, 600);
			}
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} finally {
			semaforo.release();
		}
	}
	
	private void pedido (int pedido, int tempomax, int tempomin) {
		tempo = (int) ((Math.random() * ((tempomax - tempomin) +1)) + tempomin);
		System.out.println("Pedido nº " + pedido + " foi para o fogão.");
		for (int i = 0; i < tempo; i += 100) {
			try {
				System.out.println("Pedido nº " + pedido + ": " + ((int)(i*100/tempo)) + "%");
				sleep(100);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("Pedido nº " + pedido + " pronto!");
		try {
			entrega.acquire();
			sleep(500);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} finally {
			if (tID % 2 == 0) {
				System.out.println("O Pedido nº " + pedido + ", de Lasanha de Bolonhesa, foi entregue!.");
			} else {
				System.out.println("O Pedido nº " + pedido + ", de Sopa de Cebola, foi entregue!.");
			}
			entrega.release();
		}
	}
	
	
}
