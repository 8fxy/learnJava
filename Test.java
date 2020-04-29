package learnJava;

import java.util.ArrayList;
import java.util.Random;

public class Test {
	public static void main(String[] args) {

		// 创建Q表
		ArrayList<ArrayList<qScore>> qtable = new ArrayList<ArrayList<qScore>>();

		// 创建Q表每回合的记录
		ArrayList<qScore> roundScore = new ArrayList<qScore>();

		// 第一回合
		qScore r10 = new qScore(1, 0, 1);
		qScore r11 = new qScore(2, 0, 1);
		qScore r12 = new qScore(3, 0, 1);

		// 把第一回合加入表中
		roundScore.add(r10);
		roundScore.add(r11);
		roundScore.add(r12);

		// 把第一回合加入Q表
		qtable.add(roundScore);
		
		Random rr = new Random();
		for (int i=0; i<10; i++) {
			System.out.println(rr.nextInt(3));
		}
		
		r10.setpoints(1);
		System.out.println("R   A   P");
		System.out.println("---------");
		for (ArrayList<qScore> array : qtable) {
			for (qScore s : array) {
				System.out.println(s.getroundNum() + "   " + s.getaction() + "   " + s.getpoints());
			}
		}
	}
}
