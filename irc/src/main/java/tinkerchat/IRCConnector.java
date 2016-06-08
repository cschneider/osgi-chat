package tinkerchat;

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

import net.lr.demo.chat.service.ChatBroker;

@Component(immediate=true)
public class IRCConnector {
    private OsgiDefaultCamelContext context;
    @Reference
    private ChatBroker listener;

    @Activate
    public void activate(BundleContext bc) throws Exception {
        context = new OsgiDefaultCamelContext(bc, new OsgiServiceRegistry(bc));
        context.addComponent("irc", new IrcComponent());
        context.addComponent("bean", new BeanComponent());
        context.addRoutes(new RouteBuilder() {
            
            @Override
            public void configure() throws Exception {
                from("irc:tinkerbot@193.10.255.100:6667/#jbcnconf").bean(new ChatConverter()).bean(listener);
            }
        });
        context.start();
    }
    
    @Deactivate
    public void deactivate() throws Exception {
        context.shutdown();
    }

}
