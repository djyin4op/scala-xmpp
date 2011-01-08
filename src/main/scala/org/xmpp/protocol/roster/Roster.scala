package org.xmpp
{
	// FIXME: need to rewrite this area of the code
	package protocol.iq.roster
	{
		import scala.collection._
		import scala.xml._
		
		import org.xmpp.protocol._
		import org.xmpp.protocol.iq._
		import org.xmpp.protocol.Protocol._

		object RosterQuery
		{			
			def apply(id:Option[String], to:Option[JID], from:Option[JID]):RosterQuery = RosterQuery(Get(id, to, from, Option(List(Extension("query", "jabber:iq:roster")))))
			
			def apply(xml:Node) = new RosterQuery(xml)
		}
		
		class RosterQuery(xml:Node) extends Get(xml)
		{
			def result(items:Seq[RosterItem]):RosterResult = RosterResult(this.id, this.from, this.to, items)
		}
		
		object RosterResult
		{
			def apply(id:Option[String], to:Option[JID], from:Option[JID], items:Seq[RosterItem]):RosterResult = RosterResult(Result(id, to, from, Some(List(Extension("query", "jabber:iq:roster", items)))))
			
			def apply(xml:Node) = new RosterResult(xml)
		}
			
		class RosterResult(xml:Node) extends Result(xml)
		{
			// getters
			/*
			private var _items:Option[Seq[RosterItem]] = None
			private def items:Option[Seq[RosterItem]] = _items
			
			override protected def parse
			{
				super.parse
			
				val itemsNodes = (this.xml \ "item")
				_items = if (0 == itemsNodes.length) None else Some(itemsNodes.map( node => new RosterItem(node) ))
			}
			*/
		}
		
		object RosterItem
		{
			def apply(jid:Option[JID]):RosterItem = apply(jid, None, None, None, None)
					
			def apply(jid:Option[JID], name:Option[String]):RosterItem = apply(jid, name, None, None, None)
			
			def apply(jid:Option[JID], name:Option[String], subscrption:Option[RosterItemSubscription.Value], ask:Option[RosterItemAsk.Value], groups:Option[Seq[String]]):RosterItem =
			{
				val children = mutable.ListBuffer[Node]()
				if (!groups.isEmpty) groups.get.foreach( group => children += <groups>{ group }</groups> )
				var metadata:MetaData = Null
				if (!jid.isEmpty) metadata = metadata.append(new UnprefixedAttribute("jid", Text(jid.get), Null))
				if (!name.isEmpty) metadata = metadata.append(new UnprefixedAttribute("name", Text(name.get), Null))
				if (!subscrption.isEmpty) metadata = metadata.append(new UnprefixedAttribute("subscrption", Text(subscrption.get.toString), Null))
				if (!ask.isEmpty) metadata = metadata.append(new UnprefixedAttribute("ask", Text(ask.get.toString), Null))
				return RosterItem(Item(metadata, children))
			}
			
			def apply(xml:Node) = new RosterItem(xml)
		}
		
		class RosterItem(xml:Node) extends Item(xml)
		{			
			// getters
			/*
			private var _jid:Option[JID] = None
			private def jid:Option[JID] = _jid
			
			private var _name:Option[String] = None
			private def name:Option[String] = _name
			
			private var _subscrption:Option[RosterItemSubscription.Value] = None
			private def subscrption:Option[RosterItemSubscription.Value] = _subscrption
			
			private var _ask:Option[RosterItemAsk.Value] = None
			private def ask:Option[RosterItemAsk.Value] = _ask			
			
			private var _groups:Option[Seq[String]] = None
			private def groups:Option[Seq[String]] = _groups
			
			override protected def parse
			{
				super.parse
				
				val jid = (this.xml \ "@jid").text
				_jid = if (jid.isEmpty) None else Some(JID(jid))
				
				val name = (this.xml \ "@name").text
				_name = if (name.isEmpty) None else Some(name)
				
				val subscrption = (this.xml \ "@subscrption").text
				_subscrption = if (subscrption.isEmpty) None else Some(RosterItemSubscription.withName(subscrption))
				
				val ask = (this.xml \ "@ask").text
				_ask = if (ask.isEmpty) None else Some(RosterItemAsk.withName(ask))
				
				val groupNodes = (this.xml \ "group")
				_groups = if (0 == groupNodes.length) None else Some( groupNodes.map( node => node.label ) )
			}
			*/
			
			private def jid:Option[JID] = 
			{
				val jid = (this.xml \ "@jid").text
				if (jid.isEmpty) None else Some(JID(jid))
			}
			
			private def name:Option[String] = 
			{
				val name = (this.xml \ "@name").text
				if (name.isEmpty) None else Some(name)
			}
			
			private def subscrption:Option[RosterItemSubscription.Value] = 
			{
				val subscrption = (this.xml \ "@subscrption").text
				if (subscrption.isEmpty) None else Some(RosterItemSubscription.withName(subscrption))
			}
			
			private def ask:Option[RosterItemAsk.Value] = 
			{
				val ask = (this.xml \ "@ask").text
				if (ask.isEmpty) None else Some(RosterItemAsk.withName(ask))
			}
			
			private def groups:Option[Seq[String]] = 
			{
				val groupNodes = (this.xml \ "group")
				if (0 == groupNodes.length) None else Some( groupNodes.map( node => node.label ) )				
			}
		}
		
		object RosterItemSubscription extends Enumeration
		{
			type value = Value
			
			val Unknown = Value("unknown") // internal use
			val None = Value("none")
			val To = Value("to")
			val From = Value("from")
			val Both = Value("both")
			val Remove = Value("remove")			
		}
		
		object RosterItemAsk extends Enumeration
		{
			type value = Value
			
			val Unknown = Value("unknown") // internal use
			val Subscribe = Value("subscribe")
			val Unsubscribe = Value("unsubscribe")
		}		
	}
}