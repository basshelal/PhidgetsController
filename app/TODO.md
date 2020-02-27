# TODO

## Software

### Phidgets Spatial API
Clean API that the application can nicely interact with similar to the current Sensors API

### Controller View Fragment
Shows a replica of the controller that mimics what the user is performing on the controller, it's a nice looking demo to show that we can read sensor data and format it nicely, also the same view could be used elsewhere particularly the remapping interface

### Sensor Data View Fragment
Shows the raw data that the sensors are sending, somewhat similar (but better) to the Phidgets Control Panel

### Remapping interface?
Allows the user to remap inputs to send different things, this will be hard and weird to do, leave this for last

### Do something with phone sensors?
If we have time, try and do something involving the phone's sensors because we may allow for a mode where the user has the phone on or on top of the controller, in which case we could benefit from sensor data

## Hardware

### Shorter sensor cables
I've already got shorter USB cables but having shorter sensor cables will help us fit the whole thing into a smaller size and reduces clutter. We need to ask Deepak to get these for us because they're expensive for what they are

### Detect joystick clicks
Find a way to detect when the joystick is clicked, like L3 and R3 on DS4, this is important because the user can actually click it but we have no way of reading this yet from code, meaning it does nothing, that's bad!

### Vibration Motors
Find a way of installing 1 or 2 mini vibration motors (like those used by phones) so that the controller can vibrate, this would be the phone _sending_ data to the Phidgets interface rather than receiving it for once, we can get marks for that and it will give it an authentic feeling

### Controller Shell
Find some kind of controller shell that will be able to fit our controller. The best looking thing I found is either the Xbox One Controller or the Nintendo Switch Pro controller. Both are quite large and so we might be able to squeeze most of our stuff in those. There are shells of these cases being sold on eBay
