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
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o endereço do Host.");
		String hostName = scanner.nextLine();
		
		File file;
		System.out.println("Digite o nome do arquivo a ser enviado.");
		String fileName = scanner.nextLine();
		file = new File(fileName);
		
		scanner.close();
		
		if(!file.exists())
		{
			System.out.println("Erro - Arquivo " + " não existe!");
		}

		new Uploader(hostName, 1917, file);
	}
}