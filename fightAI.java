package learnJava;

import java.util.ArrayList;
import java.util.Random;

public class fightAI {

	public static void main(String[] args) {

		// 回合计数器
		int trainingtimes = 100000;
		int i = 1;

		// 创建机器人
		Boxer boxer1 = new Boxer(100, 10, 3, 50);
		Boxer boxer2 = new Boxer(100, 10, 3, 50);

		// 初始化Q表
		ArrayList<ArrayList<qScore>> qtable = new ArrayList<ArrayList<qScore>>();
		ArrayList<qScore> roundScore = new ArrayList<qScore>();
		qScore a0 = new qScore(0, 0, 1);
		qScore a1 = new qScore(0, 1, 2);
		qScore a2 = new qScore(0, 2, 3);
		roundScore.add(a0);
		roundScore.add(a1);
		roundScore.add(a2);
		qtable.add(roundScore);

		// 初始化随机行为
		Random r1 = new Random();
		Random r2 = new Random();

		// 训练
		while ((i < trainingtimes) && (boxer1.health > 0) && (boxer2.health > 0)) {
			// while ((boxer1.health > 0) && (boxer2.health > 0)) {

			// 重置机器人状态
			boxer1.resetboxer();
			boxer2.resetboxer();
			boxer2.noobAI();

			// 聪明机器人决策
			if (a0.getpoints() > a1.getpoints()) {
				if (a0.getpoints() > a2.getpoints()) {
					boxer1.sty();
				} else {
					boxer1.dfs();
				}
			} else if (a1.getpoints() > a2.getpoints()) {
				boxer1.atk();
			} else {
				boxer1.dfs();
			}

			boolean isRandact = r1.nextInt(100) > 70;
			if (isRandact) {
				// 随机动
				boxer1.movements = r2.nextInt(3);
				System.out.println("This is a random move!");
			}

			boxer1.judge(boxer2);

			// 更新Q表
			switch (boxer1.movements) {
			case 0:
				a0.refreshq(a0.getpoints(), boxer1.health);
			case 1:
				a1.refreshq(a1.getpoints(), boxer1.health);
			case 2:
				a2.refreshq(a2.getpoints(), boxer1.health);

			// boxer1.movements = 0;
			// boxer2.movements = 0;
			}
			i++;
		}

		/*
		 * System.out.println("GAME OVER!"); if (boxer1.health > boxer2.health) {
		 * System.out.println("A Wins!"); } else { System.out.println("B Wins!"); }
		 */
		System.out.println(a0.getpoints());
		System.out.println(a1.getpoints());
		System.out.println(a2.getpoints());
	}

}

class Boxer {
	public String name;
	public int health;
	public int hitpoint;
	public int armor;
	public int endurance;
	public int position;
	public boolean isAlive = health > 0;
	// 0:STAY, 1:ATTACK, 2:DEFENSE, 3:MOVE
	public int movements;

	// public void createBoxer(String name, int health, int hitpoint, int armor, int
	// endurance) {
	public Boxer(int health, int hitpoint, int armor, int endurance) {
		this.health = health;
		this.hitpoint = hitpoint;
		this.armor = armor;
		this.endurance = endurance;
	}

	public void noobAI() {
		if (this.endurance >= 0) {
			this.atk();
			System.out.println("B attack!");
		} else {
			System.out.println("B rest!");
			this.sty();
		}

	}

	public void resetboxer() {
		this.health = 100;
		this.hitpoint = 10;
		this.armor = 3;
		this.endurance = 50;
		this.movements = 0;
	}

	public void atk() {
		this.movements = 1;
		this.endurance = this.endurance - 20;
		if (this.endurance < 0) {
			this.endurance = 0;
		}
	}

	public void sty() {
		this.movements = 0;
		this.endurance = this.endurance + 20;
		if (this.endurance < 0) {
			this.endurance = 0;
		}

	}

	public void dfs() {
		this.movements = 2;
		this.endurance = this.endurance + 0;
		if (this.endurance < 0) {
			this.endurance = 0;
		}
	}

	public void judge(Boxer enemy) {
		switch (this.movements) {
		case 0:
			System.out.println("A stay!");
			switch (enemy.movements) {
			case 0:
				break;
			case 1:
				this.health = this.health - enemy.hitpoint;
				break;
			case 2:
				break;
			}
			break;
		case 1:
			System.out.println("A attack!");
			switch (enemy.movements) {
			case 0:
				enemy.health = enemy.health - this.hitpoint;
				break;
			case 1:
				this.health = this.health - enemy.hitpoint;
				enemy.health = enemy.health - this.hitpoint;
				break;
			case 2:
				enemy.health = enemy.health - 1;
				enemy.endurance = enemy.endurance - 10;
				break;
			}
			break;
		case 2:
			System.out.println("A defense");
			switch (enemy.movements) {
			case 0:
				break;
			case 1:
				this.health = this.health - 1;
				this.endurance = this.endurance - 10;
				break;
			case 2:
				break;
			}
			break;
		}
		System.out.println("AHP: " + this.health + " BHP: " + enemy.health);
		System.out.println("AMP: " + this.endurance + " BMP: " + enemy.endurance);
		System.out.println("-------------------------");
	}
	/*
	 * if (this.movements == 0) { System.out.println("A静止！"); this.endurance =
	 * this.endurance + 20; if (enemy.movements == 1) { this.health = this.health -
	 * enemy.hitpoint; enemy.endurance = enemy.endurance - 20; } } if
	 * (this.movements == 1) { System.out.println("A攻击！"); this.endurance =
	 * this.endurance - 20; if (enemy.movements == 0) { enemy.endurance =
	 * enemy.endurance + 20; enemy.health = enemy.health - this.hitpoint; } else if
	 * (enemy.movements == 1) { enemy.endurance = enemy.endurance - 20; // 相互击中
	 * this.health = this.health - enemy.hitpoint; enemy.health = enemy.health -
	 * this.hitpoint; } } if (this.movements == 2) { System.out.println("A防御！"); //
	 * 防御 if (enemy.movements == 0) { this.endurance = this.endurance + 10;
	 * enemy.endurance = enemy.endurance + 20; } else if (enemy.movements == 1) {
	 * enemy.endurance = enemy.endurance - 20; this.endurance = this.endurance - 15;
	 * // 格挡 this.health = this.health - 1; } } if (endurance < 0) endurance = 0;
	 * System.out.println("A HP: " + this.health); System.out.println("B HP: " +
	 * enemy.health); }
	 */
}
