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
    private String motionId;
    protected String lcdId;

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
        // Register enumerate listener and print incoming information
        ipcon.addEnumerateListener(new IPConnection.EnumerateListener() {
            public void enumerate(String uid, String connectedUid, char position,
                                  short[] hardwareVersion, short[] firmwareVersion,
                                  int deviceIdentifier, short enumerationType) {
                System.out.println("UID:               " + uid);
                System.out.println("Enumeration Type:  " + enumerationType);

                if(enumerationType == IPConnection.ENUMERATION_TYPE_DISCONNECTED) {
                    System.out.println("");
                    return;
                }

                System.out.println("Connected UID:     " + connectedUid);
                System.out.println("Position:          " + position);
                System.out.println("Hardware Version:  " + hardwareVersion[0] + "." +
                                                           hardwareVersion[1] + "." +
                                                           hardwareVersion[2]);
                System.out.println("Firmware Version:  " + firmwareVersion[0] + "." +
                                                           firmwareVersion[1] + "." +
                                                           firmwareVersion[2]);
                System.out.println("Device Identifier: " + deviceIdentifier);
                System.out.println("");
                if (deviceIdentifier == 212) {
                    lcdId = uid;
                }
                if (deviceIdentifier == 233) {
                    motionId = uid;
                }
            }
        });

        ipcon.enumerate();
    }

    @Deactivate
    public void deactivate() throws NotConnectedException {
        LOG.info("Stopping Connection");
        ipcon.disconnect();
    }

    IPConnection getConnection() {
        return ipcon;
    }

    public String getMotionId() {
        return motionId;
    }
    public String getLcdId() {
        return lcdId;
    }
    
}
