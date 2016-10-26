# Lean Microservices 

The lean microservices talk shows the limitations of the popular spring boot model to decvelop microservices.
It then shows OSGi with some extensions for packaging and remote communication as an alternative development model
that is simpler for the individual module developers while providing a lot more flexibility at deployment time. 

This example shows how to design a modular application from microservices. The modules communicate only through
OSGi services based on a shared interface (API) bundles.

For packaging an OSGi index is created on the fly from maven dependencies and bndtools bndrun descriptors are used to describe the application. This results in a self contained jar for the packaging. Optionally this can be packaged into a docker image.

The simplest packaging (all) is to package all bundles of the app together. They then use plain OSGi services to talk locally.

Alternatively the application can be split up into individual deployments per microservice comparable to spring boot apps. In this case we use the Aries Remote Service Admin to support transparent remote communication between the microservices.

Aries Remote Service Admin can also be used to export a plain OSGi service with rest annotations as a fully featured REST service that can be used to communicate with non OSGi modules or with external systems.

## OSGi chat example

The example implements a chat notification system with several inputs and outputs.
The inputs will always send to all currently available outputs.

Using Remote Service Admin the inputs and outputs can reside on different processes and machines.

- chat api : Simple interface to send a char message consisting of time, sender, message
- irc connector: Logs into a freenet irc server into channel #jbcnconf and forwards all messages
- shell command send <message> : Forwards the string as a message
- LCD display: Listens to chat messages and displays them on a tinkerforge 20x4 LCD screen
- Motion detector: Tinkerforge motion detector that sends notifications about motions 

## Prerequisites

Install
- docker on ubuntu (see https://docs.docker.com/engine/installation/linux/ubuntulinux/)
- jdk 1.8
- eclipse
- install bndtools 3.3 into eclipse (http://bndtools.org/installation.html)
- maven 3.3.x

## Source 

Checkout the source git clone git@github.com:cschneider/osgi-chat.git
Start eclipse and import the directory osgi-chat and all sub dirs as existing maven projects.

## Build

- mvn clean install

## Run

### Simple deployment chat-all
```
cd packaging/chat-all
java -jar target/osgi-chat.jar
```
