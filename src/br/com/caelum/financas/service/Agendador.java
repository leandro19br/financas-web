package br.com.caelum.financas.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Stateless
public class Agendador {
	
	
	private static int totalCriado;
	
	@Resource
	private TimerService timerservice;

	@PostConstruct
	void posCosntruct(){
		System.out.println("criando o agendador");
		
	}
	
	@PreDestroy
	void preDestruicao(){
		System.out.println("destruindo o agendador");
		
	}
		
	
	public void executa() {
		System.out.printf("%d instancias criadas %n", totalCriado);

		// simulando demora de 4s na execucao
		try {
			System.out.printf("Executando %s %n", this);
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
	}
	
	public void agenda(String expressaoMinutos,String expressaoSegundos){
		
		ScheduleExpression expression = new ScheduleExpression();
		expression.hour("*");
		expression.minute(expressaoMinutos);
		expression.second(expressaoSegundos);
		
		
		TimerConfig config = new TimerConfig();
		config.setInfo(expression.toString());
		config.setPersistent(false);
		
		this.timerservice.createCalendarTimer(expression,config);
		
		System.out.println("Agendamento" + expression);
		
	}
	
	@Timeout
	public void verificacaoPeriodicaSeHaNovasContas(Timer timer) {
		
		System.out.println(timer.getInfo());
	}

}
