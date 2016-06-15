package net.lr.demo.chat.command;

import java.util.HashSet;
import java.util.Set;

import org.apache.karaf.shell.table.ShellTable;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.remoteserviceadmin.EndpointDescription;
import org.osgi.service.remoteserviceadmin.EndpointListener;

@Component(//
    property = {
                "osgi.command.scope=rsa", //
                "osgi.command.function=endpoints",
                "endpoint.listener.scope=(endpoint.framework.uuid=*)"
                
    })
public class EndpointsCommand implements EndpointListener {
    Set<EndpointDescription> endpoints = new HashSet<>();
    private String frameworkId;
    
    @Activate
    public void activate(BundleContext context) {
        this.frameworkId = context.getProperty(Constants.FRAMEWORK_UUID);
    }

    public void endpoints() {
        System.out.println("Endpoints for framework " + frameworkId);
        ShellTable table = new ShellTable();
        table.column("id");
        table.column("interfaces");
        table.column("framework");
        table.column("comp name");
        endpoints.stream().forEach((ep)->print(table, ep));
        table.print(System.out);
    }

    private void print(ShellTable table, EndpointDescription ep) {
        String compName = getProp(ep, "component.name");
        table.addRow().addContent(ep.getId(), ep.getInterfaces(), ep.getFrameworkUUID(), compName);
    }

    private String getProp(EndpointDescription ep, String key) {
        Object value = ep.getProperties().get(key);
        return value == null ? "" : value.toString();
    }

    @Override
    public void endpointAdded(EndpointDescription endpoint, String matchedFilter) {
        endpoints.add(endpoint);
    }

    @Override
    public void endpointRemoved(EndpointDescription endpoint, String matchedFilter) {
        endpoints.remove(endpoint);
    }
    
}
