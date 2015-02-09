package main;

public class Person
{
	private int age;
	private Gender gender;
	private String name;
	private boolean isAlive;
	
	public Person(int age, Gender gender, String name)
	{
		if(age < 0) throw new IllegalArgumentException("Age must be positive: " + age);
		this.age = age;
		this.gender = gender;
		this.name = name;
	}
	
	public void ageOneYear()
	{
		age++;
	}
	
	public String toString()
	{
		return name + ", " + gender + ", " + age;
	}
	
	public int getAge()
	{
		return age;
	}
}
