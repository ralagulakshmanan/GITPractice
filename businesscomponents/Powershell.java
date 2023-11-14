package businesscomponents;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Powershell {
 
	public void executepowershell() throws IOException
	{
		String command="powershell.exe Get-Process";
		Process powershellProcess=Runtime.getRuntime().exec(command);
		powershellProcess.getOutputStream().close();
		String line;
		System.out.println("Standard output:");
		BufferedReader stdout=new BufferedReader(new InputStreamReader(powershellProcess.getInputStream()));
		while((line=stdout.readLine())!=null)
		{
			System.out.println(line);
		}
		stdout.close();
		System.out.println("Standard error:");
		BufferedReader stderr=new BufferedReader(new InputStreamReader(powershellProcess.getInputStream()));
		while((line=stderr.readLine())!=null)
		{
			System.out.println(line);
		}
		stderr.close();
	}
}
