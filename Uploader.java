import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Uploader
{
	private Socket uSocket;
	
	public Uploader(String host, int port, File file)
	{
		try
		{
			uSocket = new Socket(host, port);
			sendFile(file);
		}
		catch (IOException e)
		{
			System.out.println("Erro - Arquivo " + file + " não encontrado!");
		}
		catch (Exception e)
		{
			System.out.println("Erro - Destinatário inexistente!");
		}
	}

	public void sendFile(File file) throws IOException
	{
			DataOutputStream dos = new DataOutputStream(uSocket.getOutputStream());
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			
			dos.writeInt((int)file.length());
			while(fis.read(buffer) > 0)
			{
				dos.write(buffer);
			}

			fis.close();
			dos.close();
	}

	public static void main(String[] args)
	{
		File file;
		System.out.println("Digite o nome do arquivo a ser enviado.");
		String fileName = new Scanner(System.in).nextLine();
		file = new File(fileName);
		
		if(!file.exists())
		{
			System.out.println("Erro - Arquivo " + " não existe!");
		}
		else new Uploader("localhost", 1917, file);
	}
}