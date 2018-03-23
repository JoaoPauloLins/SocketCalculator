
public class CalculatorImpl implements ICalculator {
	@Override
	public float sum(float p1, float p2) {
		return p1+p2;
	}

	@Override
	public float sub(float p1, float p2) {
		return p1-p2;
	}

	@Override
	public float div(float p1, float p2) {
		return p1/p2;
	}

	@Override
	public float mul(float p1, float p2) {
		return p1*p2;
	}
}
