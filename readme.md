### about

scala-xmpp is a an xmpp framework in scala. it provides core xmpp stanza encoding/decoding and a framework for building servers,
external components and clients.

scala-xmpp is similar to ejabbered's exmpp and openfire's tinder/whack libraries but is a native scala implementation.
As such it is optimized for high concurrency utilizing scala's actors and java nio sockets via netty and naggati.
Stanza manipulation is based on pattern matching instead of xml literals.

### a simple external component

    class Echo extends XmppComponent
    {
        override val identities = List(disco.Identity("component", "c2s", "echo server"))
        override val extensionsBuilders = Nil

        override def onMessage(message:Message)
        {
            send(Chat(message.from, message.to, message.subject, message.body))
        }
    }

### a simple server

    class MyServer extends XmppServer
    {
        val config = new DefaultXmppServerConfig()

        def startup()
        {
            super.startup("localhost", "127.0.0.1", keystore, keystorePassword)
        }

        protected def onClientConnected(client:ClientConnection)
        {
            debug("xmpp client %s is connected".format(client.id))

            client.delegate = Some(new ClientConnectionDelegate
            {
                override def authenticate(request:AuthenticationRequest) =
                {
                    debug("xmpp client %s requesting authentication".format(client.id))
                    AuthenticationResult.Unknown
                }

                override def register(request:RegistrationRequest) =
                {
                    debug("xmpp client %s requesting registration".format(client.id))
                    RegistrationResult.Unknown
                }

                override def onStanza(stanza:Stanza)
                {
                    debug("xmpp client %s received '%s'".format(client.id, stanza))
                }

                override def onOnline(jid:JID)
                {
                    debug("xmpp client %s is online".format(client.id))
                }

                override def onOffline()
                {
                    debug("xmpp client %s is offline".format(client.id))
                }

                override def onError(e:Throwable)
                {
                    error("xmpp client %s reported an error".format(client.id), e)
                }
            })
        }

        protected def onClientDisconnected(client:ClientConnection)
        {
            debug("xmpp client %s is disconnected".format(client.id))
        }
    }

### open issue

* client implementation
* unit tests
* implement more common extensions

### license

scala-xmpp, a scala based xmpp framework
Copyright (C) 2010 mishlabs
www.mishlabs.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

***************************************************************************************

This project is work in progress. if you are interested in contributing, have comments
or want to share how you are using it please touch base via github

