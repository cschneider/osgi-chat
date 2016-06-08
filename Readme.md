# Running the EchoTCP example from Aries rsa on docker containers and using bndtools to resolve against a repository

This example packages Aries RSA and the EchoTCP example first as two runnable jars using bndtools
and in a second step as two docker images.

## Build

The build is currently not fully automated. So you will have to do these steps manually:

- Install docker on ubuntu (see https://docs.docker.com/engine/installation/linux/ubuntulinux/)
- Install bndtools into eclipse (http://bndtools.org/installation.html)
- Checkout the source git clone git@github.com:cschneider/echotcp-docker.git
- Start eclipse and import the directory echotyp-docker as a maven project

These steps are optional as the jars are already checked in:
- Open the service.bndrun in the bndtools editor, resolve and export as service.jar in the same directory
- Open the consumer.bndrun in the bndtools editor, resolve and export as consumer.jar in the same directory

- Package consumer and service as docker images using the `sh build.sh` in the respective directories

## Run

### Terminal 1:
```
cd service
sh start.sh
```

The docker container should start and show that the echo tcp service is ecported to zookeeper.
It will also boot into a gogo shell so you can discover the setup.

### Terminal 2:
```
cd consumer
sh start.sh
```

The docker container should start and the echo tcp service should be imported and called in the consumer.
You should see "Good morning" on the console. Like for the service you can use gogo to discover the setup.

## Further improvements

- Fuly automating the build process using maven
- Run the docker images on mesos and marathon.

