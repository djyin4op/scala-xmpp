package org.xmpp
{
	package protocol.iq
	{
		import scala.collection._
		import scala.xml._
		
		import org.xmpp.protocol._
		import org.xmpp.protocol.Protocol._
		
		object Error
		{
			def apply(id:Option[String], to:Option[JID], from:Option[JID], condition:ErrorCondition.Value, description:Option[String]=None):Error =
			{
				val xml = IQ.error(id, to, from, condition, description)
				return apply(xml)
			}
			
			def apply(xml:Node):Error = new Error(xml)
		}
		
		class Error(xml:Node) extends IQ(xml, IQTypeEnumeration.Error)
		{
		}
	}
}