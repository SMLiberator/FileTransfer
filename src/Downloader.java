import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Downloader extends Thread
{	
	private ServerSocket dSocket;

	public Downloader(int port)
	{
		try
		{
			dSocket = new ServerSocket(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Socket clientSock = dSocket.accept();
				saveFile(clientSock);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void saveFile(Socket cSocket) throws IOException
	{
			System.out.println("Salvar arquivo como:");
			String fileName = new Scanner(System.in).nextLine();
			
			DataInputStream dis = new DataInputStream(cSocket.getInputStream());
			FileOutputStream fos = new FileOutputStream(fileName);
			byte[] buffer = new byte[4096];
			
			int fileSize = dis.readInt();
			System.out.println("Tamanho do arquivo recebido: " + fileSize);

			int read = 0;
			int totalRead = 0;
			int	remaining = fileSize;
			while(totalRead < fileSize)
			{
				read = dis.read(buffer, 0, Math.min(buffer.length, remaining));

				totalRead = totalRead + read;
				remaining = remaining - read;
				fos.write(buffer, 0, read);
			}
			
			System.out.println(totalRead + " bytes transferidos. Nenhum erro detectado.");
			fos.close();
			dis.close();
	}
	
	public static void main(String[] args)
	{
		Downloader fs = new Downloader(1917);
		fs.start();
	}
}