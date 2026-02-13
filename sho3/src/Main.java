import java.util.ArrayList;
import java.util.List;

interface Device {
    void operate();
}

interface SmartFactory {
    Device createClimateControl();
    Device createSecuritySensor();
}

class EcoFactory implements SmartFactory {
    public Device createClimateControl() { return new EcoThermostat(); }
    public Device createSecuritySensor() { return new EcoMotionSensor(); }
}

class EcoThermostat implements Device {
    public void operate() { System.out.println("Eco thermostat: maintains +19°C (economy mode)."); }
}

class EcoMotionSensor implements Device {
    public void operate() { System.out.println("Eco sensor: scans once per minute."); }
}

class LuxuryFactory implements SmartFactory {
    public Device createClimateControl() { return new LuxuryClimateControl(); }
    public Device createSecuritySensor() { return new RetinaScanner(); }
}

class LuxuryClimateControl implements Device {
    public void operate() { System.out.println("Luxury Climate: precise control of +22°C and humidity."); }
}

class RetinaScanner implements Device {
    public void operate() { System.out.println("Luxury Security: Retinal Scanning."); }
}

class OldLightSystem {
    public void turnOnOldBulb() { System.out.println("Old lamp: light turned on via adapter."); }
}

class LightAdapter implements Device {
    private OldLightSystem oldLight;

    public LightAdapter(OldLightSystem oldLight) {
        this.oldLight = oldLight;
    }

    @Override
    public void operate() {
        oldLight.turnOnOldBulb();
    }
}

interface HouseState {
    void reportStatus();
}

class NormalState implements HouseState {
    public void reportStatus() { System.out.println(">>> STATUS: All systems are operating normally.."); }
}

class EmergencyState implements HouseState {
    public void reportStatus() { System.out.println(">>> ATTENTION: System in ALARM mode! Call security."); }
}

class SmartHome {
    private HouseState state;
    private List<Device> devices = new ArrayList<>();

    public SmartHome() {
        this.state = new NormalState();
    }

    public void setState(HouseState state) {
        this.state = state;
        System.out.println("\n[The system switched the status...]");
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void runSystem() {
        System.out.println("--- System report ---");
        state.reportStatus();
        for (Device d : devices) {
            d.operate();
        }
        System.out.println("--------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        SmartHome myHome = new SmartHome();

        SmartFactory ecoFactory = new EcoFactory();
        myHome.addDevice(ecoFactory.createClimateControl());
        myHome.addDevice(ecoFactory.createSecuritySensor());

        SmartFactory luxuryFactory = new LuxuryFactory();
        myHome.addDevice(luxuryFactory.createClimateControl());

        OldLightSystem oldLamp = new OldLightSystem();
        myHome.addDevice(new LightAdapter(oldLamp));

        myHome.runSystem();

        myHome.setState(new EmergencyState());
        myHome.runSystem();

        myHome.setState(new NormalState());
        myHome.runSystem();
    }
}