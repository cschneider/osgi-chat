package net.lr.demo.chat.lcd;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;

@Component(name = "tf", configurationPolicy = ConfigurationPolicy.REQUIRE, service = TinkerConnect.class)
@Designate(ocd = TinkerConnect.TfConfig.class)
public class TinkerConnect {
    private static Logger LOG = LoggerFactory.getLogger(TinkerConnect.class);
    private IPConnection ipcon;

    @ObjectClassDefinition(name = "Tinkerforge config")
    @interface TfConfig {
        String host() default "localhost";

        int port() default 4223;
    }

    @Activate
    public void activate(TfConfig config) throws Exception {
        LOG.info("Starting Connection {}:{}", config.host(), config.port());
        ipcon = new IPConnection();
        ipcon.connect(config.host(), config.port());
    }

    @Deactivate
    public void deactivate() throws NotConnectedException {
        LOG.info("Stopping Connection");
        ipcon.disconnect();
    }

    IPConnection getConnection() {
        return ipcon;
    }
}
