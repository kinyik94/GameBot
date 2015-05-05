package core;

import java.io.File;
import java.nio.file.FileSystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testUserManager {
	
	@Before
	public void setUp() throws Exception{
		UserManager.read();
		if(UserManager.consist("Test")){
			UserManager.login("Test", "TestPass");
			UserManager.remove();
		}
	}
	
	@Test
	public void testRegister() throws Exception{
		Assert.assertEquals(false, UserManager.consist("Test"));
		UserManager.register("Test", "TestPass");
		Assert.assertEquals(true, UserManager.consist("Test"));
		Assert.assertEquals("Test", UserManager.user.getName());
		Assert.assertEquals(true, new File("Users" + FileSystems.getDefault().getSeparator() + "Test").exists());
	}
	
	@Test(expected=LoginException.class)
	public void testFailLogin() throws Exception{
		UserManager.login("Test", "TestPass");
	}
	
	@Test
	public void testLogin() throws Exception{
		UserManager.register("Test", "TestPass");
		UserManager.user = null;
		UserManager.login("Test", "TestPass");
		Assert.assertEquals("Test", UserManager.user.getName() );
	}
	
	@Test
	public void testRemove() throws Exception{
		UserManager.register("Test", "TestPass");
		Assert.assertEquals(true, UserManager.consist("Test"));
		Assert.assertEquals(true, new File("Users" + FileSystems.getDefault().getSeparator() + "Test").exists());
		UserManager.remove();
		Assert.assertEquals(false, UserManager.consist("Test"));
		Assert.assertEquals(false, new File("Users" + FileSystems.getDefault().getSeparator() + "Test").exists());
	}
	
	@After
	public void delete(){
		if(UserManager.consist("Test"))
			UserManager.remove();
			
	}
	
}
