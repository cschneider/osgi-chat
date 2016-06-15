package net.lr.demo.chat.irc;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.BeanComponent;
import org.apache.camel.component.irc.IrcComponent;
import org.apache.camel.core.osgi.OsgiDefaultCamelContext;
import org.apache.camel.core.osgi.OsgiServiceRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import net.lr.demo.chat.service.ChatBroker;
import net.lr.demo.chat.service.ChatListener;
import net.lr.demo.chat.service.ChatMessage;

@Component( //
    name = "connector.irc", //
    immediate = true, //
    property = //
    {
     "service.exported.interfaces=*",
    })
public class IRCConnector implements ChatListener {
    private OsgiDefaultCamelContext context;
    @Reference
    private ChatBroker broker;
    private ProducerTemplate producer;
    private String ircURI;

    @ObjectClassDefinition(name = "IRC config")
    @interface TfConfig {
        String nick() default "tinkerbot";

        String server() default "193.10.255.100";

        int port() default 6667;

        String channel() default "#jbcnconf";
    }

    @Activate
    public void activate(BundleContext bc, TfConfig config) throws Exception {
        context = new OsgiDefaultCamelContext(bc, new OsgiServiceRegistry(bc));

        // FIXME Somehow the components are not picked up
        context.addComponent("irc", new IrcComponent());
        context.addComponent("bean", new BeanComponent());

        ircURI = String.format("irc:%s@%s:%d/%s", config.nick(), config.server(), config.port(),
                               config.channel());
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from(ircURI).bean(new ChatConverter()).bean(broker);
            }
        });
        context.start();
        producer = context.createProducerTemplate();
    }

    @Deactivate
    public void deactivate() throws Exception {
        context.shutdown();
    }

    @Override
    public void onMessage(ChatMessage message) {
        if (!"irc".equals(message.getSenderId())) {
            producer.sendBody(ircURI, message.getMessage());
        }
    }

}
