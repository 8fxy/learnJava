package learnJava;

public class qScore {

	private int roundNum;
	private double points;
	private int action;

	public qScore() {
		super();
	}

	public qScore(int roundNum, int action, double points) {
		super();
		this.roundNum = roundNum;
		this.points = points;
		this.action = action;
	}

	public int getroundNum() {
		return roundNum;
	}

	public void setroundNum(int roundNum) {
		this.roundNum = roundNum;
	}

	public double getpoints() {
		return points;
	}

	public void setpoints(double points) {
		this.points = points;
	}

	public int getaction() {
		return action;
	}

	public void setaction(int action) {
		this.action = action;
	}

	public void refreshq(double qold, double qnew) {
		this.setpoints( (qold + qnew) * 0.5);
	}
}
