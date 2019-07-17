import com.pi4j.io.gpio.*;

public class PinSwitch extends Thread {
    private Pin pinNumber;

    public PinSwitch(Pin pinNumber) {
        this.pinNumber = pinNumber;
    }

    @Override
    public void run() {
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(pinNumber, "MyLED", PinState.LOW);

        pin.pulse(1000, true); // set second argument to 'true' use a blocking call
    }
}
