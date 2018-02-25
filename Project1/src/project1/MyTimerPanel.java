//package project1;
//
//import java.awt.event.*;
//import javax.swing.*;
//
//public class MyTimerPanel extends JPanel {
//
//	private StopWatch StopWatchTimer;
//	private Timer javaTimer;
//	private TimerListener timer;
//
//
//	private JButton[] buttons;
//	private JButton buttonStart;
//	private JButton buttonStop;
//	private JButton buttonSub;
//	private JButton buttonAdd;
//
//	
//	
//	private abstract class ButtonListener implements ActionListener() {
//		
//	}
//	
//	
//	public MyTimerPanel() {
//		
//		StopWatchTimer = new StopWatch(0,0,0);
//		timer = new TimerListener();
//		javaTimer = new Timer(1000, timer);
//		javaTimer.start();	
//
//		
//		ButtonListener buttonListen = new ButtonListener();
//		buttonStart = new JButton("Start");
//		buttonStop = new JButton("Stop");
//		buttonAdd = new JButton("Add");
//		buttonSub = new JButton("Subtract");
//
//		
//		buttonStart.addActionListener(buttonListen);
//		buttonStop.addActionListener(buttonListen);
//		buttonSub.addActionListener(buttonListen);
//		buttonAdd.addActionListener(buttonListen);
//
//		
//		add(buttonStart);
//		add(buttonStop);
//		add(buttonSub);
//		add(buttonAdd);
//
//
//	}
//
//	private class TimerListener implements ActionListener {
//
//		public void actionPerformed(ActionEvent e) {
//			try {
//
//				
//				
//				
//				
//			}
//
//			catch ( NumberFormatException error1) {
//				
//			}
//			
//			catch ( IllegalArgumentException error2 ) {
//				
//			}
//		}
//	}
//
//
//}
