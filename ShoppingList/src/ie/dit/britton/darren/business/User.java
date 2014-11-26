package ie.dit.britton.darren.business;

/**
 * the user class encapsulates all the user data as a singleton so it can be
 * access in all intents to retrieve user data.
 */
public class User
{

	private static User instance = null;

	public static User getInstance()
	{
		if (instance == null)
		{
			instance = new User();
		}
		return instance;
	}

	private String name;
	private String gender;
	private String job;
	private Short age;
	private Double money;
	private String Email;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getJob()
	{
		return job;
	}

	public void setJob(String job)
	{
		this.job = job;
	}

	public Short getAge()
	{
		return age;
	}

	public void setAge(Short age)
	{
		this.age = age;
	}

	public Double getMoney()
	{
		return money;
	}

	public void setMoney(Double money)
	{
		this.money = money;
	}

	public String getEmail()
	{
		return Email;
	}

	public void setEmail(String Email)
	{
		this.Email = Email;
	}

}