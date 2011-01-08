package org.xmpp
{
	package protocol.message
	{
		import scala.collection._
		import scala.xml._
		
		import org.xmpp.protocol._
		import org.xmpp.protocol.Protocol._
		
		object Chat
		{			
			def apply(to:Option[JID], from:Option[JID], body:Option[String]):Chat = apply(None, to, from, None, body, None, None)
			
			def apply(id:Option[String], to:Option[JID], from:Option[JID], body:Option[String]):Chat = apply(id, to, from, None, body, None, None)
				
			def apply(id:Option[String], to:Option[JID], from:Option[JID], subject:Option[String], body:Option[String]):Chat = apply(id, to, from, subject, body, None, None)
					
			def apply(id:Option[String], to:Option[JID], from:Option[JID], subject:Option[String], body:Option[String], thread:Option[String], extensions:Option[Seq[Extension]]):Chat =
			{
				val xml = Message.build(MessageTypeEnumeration.Chat, id, to, from, subject, body, thread, extensions)
				return apply(xml)
			}
						
			def apply(xml:Node):Chat = new Chat(xml)
		}
		
		class Chat(xml:Node) extends Message(xml, MessageTypeEnumeration.Chat)
		{
		}

	}
}