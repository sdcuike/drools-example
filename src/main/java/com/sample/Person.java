package com.sample;

/**
 * Created by beaver on 2017/3/9.
 */
public class Person {
	private int minAge;
	private int maxAge;
	private boolean enable = false;

	public Person(int minAge, int maxAge) {
		this.minAge = minAge;
		this.maxAge = maxAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "Person [minAge=" + minAge + ", maxAge=" + maxAge + ", enable=" + enable + "]";
	}

}
