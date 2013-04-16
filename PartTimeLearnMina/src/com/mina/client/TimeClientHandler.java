package com.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter
{

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		// TODO Auto-generated method stub
		//super.messageReceived(session, message);
		System.out.println("received:"+message.toString());
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception
	{
		// TODO Auto-generated method stub
		super.messageSent(session, message);
		//System.out.println("sent:"+message.toString());
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception
	{
		cause.printStackTrace();
	}
}
