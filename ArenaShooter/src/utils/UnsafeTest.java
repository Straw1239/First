package utils;

import java.security.Permission;

public class UnsafeTest
{
	public static void main(String[] args)
	{
		System.setSecurityManager(new SecurityManager()
		{
			public void checkPermission(Permission p)
			{
				return;
			}
		});
		//Unsafe u = Unsafe.getUnsafe();
		
	}
}
