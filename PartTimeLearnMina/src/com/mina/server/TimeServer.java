package com.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TimeServer
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int processNum=Runtime.getRuntime().availableProcessors()+1;
		IoAcceptor ioAcceptor=new NioSocketAcceptor(processNum);
//				new NioProcessor(Executors.newCachedThreadPool()));
		//ioAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
		ioAcceptor.getFilterChain().addLast("coder", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		ioAcceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
		ioAcceptor.setHandler(new TimeServerHandler());
		IoSessionConfig sconfig= ioAcceptor.getSessionConfig();
		sconfig.setReadBufferSize(2048);
		sconfig.setIdleTime(IdleStatus.BOTH_IDLE, 10);
		ioAcceptor.setDefaultLocalAddress(new InetSocketAddress(20001));
		try
		{
			ioAcceptor.bind();
			System.out.println("系统启动监听：20001");
		}
		catch (IOException e)
		{
			System.out.println("bind error \r\n"+e.getLocalizedMessage());
			ioAcceptor.setDefaultLocalAddress(new InetSocketAddress(20002));
			try
			{
				ioAcceptor.bind();
				System.out.println("系统启动监听：20002");
			}
			catch (IOException e1)
			{
				System.out.println("second bind error \r\n"+ e1.getLocalizedMessage());
			}
		}
	}

}
