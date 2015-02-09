package main;

public class PersonTest
{
	public static void main(String[] args)
	{
		Person a = new Person(42, Gender.MALE, "Bob");
		Person b = new Person(24, Gender.FEMALE, "Carmen");
		System.out.println(a.getAge());
		a.ageOneYear();
		b.ageOneYear();
		b.ageOneYear();
		System.out.println(a);
		System.out.println(b);
	}
}
