/**
 * 
 */
package com.mina.server;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @author Administrator
 *
 */
public class TimeServerHandler extends IoHandlerAdapter
{

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception
	{
		//super.exceptionCaught(session, cause);
		cause.printStackTrace();
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception
	{
		super.sessionIdle(session, status);
		System.out.println("Seesion Idle..."+session.getIdleCount(status));
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		super.sessionOpened(session);
		System.out.println("session opened...");
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		// TODO Auto-generated method stub
		System.out.println("session  Id:"+session.getId()+"³ÖÐø£º"+(session.getLastIoTime()-session.getCreationTime())/1000+"s");
		super.sessionClosed(session);
		System.out.println("session Closed...");
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		String s=message.toString();
		if(s.equalsIgnoreCase("quit"))
		{
			session.close(true);
			return;
		}
		
		Date date=new Date();
		session.write(date);
		System.out.println(session.getId()+"  session receive:"+s);
	}
	
}
