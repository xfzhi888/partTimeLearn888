package com.mina.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TimeClient
{
	
	public boolean isConnected;

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args)
	{
		 for(int i=0;i<500;i++)
		 {
			 TimeClient client=new TimeClient();
				client.createClient();
		 }
	}
	
	public void createClient(){
		SocketConnector connector=new NioSocketConnector();
		//connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		SocketAddress address=new InetSocketAddress("127.0.0.1",20001);
		connector.setHandler(new TimeClientHandler());
		ConnectFuture future= connector.connect(address);
		future.addListener(new IoFutureListener<IoFuture>()
		{
			public void operationComplete(IoFuture arg0) {
				
				System.out.println("operationComplete:"+arg0.isDone());
				isConnected=true;
			};
		});
		IoSession session=null;
		if(!future.isConnected())
		{
			while(true)
			{
				System.out.println("系统正在连接中。。。。。。");
				try
				{
					Thread.sleep(4);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(isConnected)
				{
					break;
				}
			}
			System.out.println("连接到服务器");
		}
		 
		session= future.getSession();
		
		int count=0;
		while (true)
		{
			session.write("send:message"+count);
			try
			{
				Thread.currentThread().sleep(20);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			if(count>20)
			{
				break;
			}
		}
		CloseFuture closeFuture= session.close(false);
		closeFuture.awaitUninterruptibly(200);
	}

}
