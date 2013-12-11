package org.janux.spreadsheetReport;

public class Employee{
	String name;
	int payment;
	int bonus;
	int age;

	public Employee(String name, int age, int payment, int bonus) {
		super();
		this.name = name;
		this.payment = payment;
		this.bonus = bonus;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}