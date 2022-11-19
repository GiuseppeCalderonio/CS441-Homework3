# CS-441 HOMEWORK 3

* First Name : Giuseppe
* Last name : Calderonio
* UIC email : gcalde22@uic.edu
* UIN: 679346611

## Introduction
This repository contains a set of different cloud simulations 
implemented with CloudSimPlus where many datacenters were
simulated with different workloads.
It was specifically
implemented for the third homework of the CS-441 Cloud Computing
class of University of Illinois at Chicago

## Requirements

In order to correctly download and use the project, it's required
to have the following tools installed :

1. Docker installed

No other requirements are included since everything can be 
deployed and run in a containerized environment

However it can also be run with sbt and scala.

## Problem statement

The problem consists on implementing a set of simulations with
specific requirements like implementing VM migration policies,
configure network topologies between datacenters etc...
In particular, for graduate students only a simulation with a
combination of Saas, Iaas, Paas and Faas have to be implemented and
then containerized in a Docker Image.

More info can be found [here](https://github.com/0x1DOCD00D/CS441_Fall2022/blob/main/Homeworks/Homework3.md)

## Project description

### Code Structure

This section will talk more about the actual code 
implementation that, however, is not the real core of the homework.
Being able to handle many configuration parameters and create the 
simulations dynamically is a design choice that influences all the
project structure, so the code was written in the most general way
as possible in order to allow designers to create simulations
only changing configuration parameters according to a specific
structure described in the next sections.

Indeed, the code is divided in two main packages:

* [CloudComponents](https://github.com/GiuseppeCalderonio/CS441-Homework3/tree/master/src/main/scala/CloudComponents): 
set of objects that given the reference of a 
configuration parameter, create an object (or a list of objects) according to that type
( for example, hosts or VMs given their type) 
* [Configurations](https://github.com/GiuseppeCalderonio/CS441-Homework3/tree/master/src/main/scala/Configurations):
set of main classes, plus an object called [Configuration](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/scala/Configurations/Configuration.scala),
which purpose is to instantiate components, run the simulation,
collect and report results

More info about the code can be found in the comments.

### Configuration parameters

#### Overview

The core of the project is represented by the configuration
parameters, through which the simulations are defined.

In particular, it is enough to describe how many datacenters,
vms and cloudlets to create, configure their network topology and
create a main class under the package [Configurations](https://github.com/GiuseppeCalderonio/CS441-Homework3/tree/master/src/main/scala/Configurations)
to run a complete simulation. 

In the [component example file](https://github.com/GiuseppeCalderonio/CS441-Homework3/tree/master/src/main/scala/Configurations)
is shown a ready to use example of component configuration file with all the
parameters ready to be set; in particular, it's enough to choose
how many instances for each type to create 
(0 for no instance of that type).

In the [network example file](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/example.brite)
is shown a ready to use example of network configuration file
with all the parameters to be set.

#### Structure

The structure of the configuration files can be seen as a 
hierarchical structure, where a set of basic components were designed
and then combined in more complicated and high level components.

In particular, during the design phase, two approaches were possible :

* Declare each parameter in a single file for each simulation: this
approach has the benefit of being more customizable since all the parameters can be changed,
but has the drawback of being complex for big simulations
* Create a set of pre-defined components with chosen parameters: this
approach has the benefit of being scalable with respect to the simulation complexity,
but has the drawback of limited customization

The second approach was chosen, and each component follows more or less the same
principle, it can be of three types:

* simple : this element represents a component
 that has / needs low computational power but is cheap
* medium : this element represents a tradeoff between computational power and cost
* large : this element represents a component
  that has / needs high computational power but is expensive

List of components description can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components)

### Simulations

The total number of simulation analyzed is 8.

They can be divided in 3 main categories :

1. Simulations where one component at a time was tested
with its corresponding workload with respect to complexity.
More on these simulations can be found [here]()
2. Simulations where different kind of components were combined
together in complex structures to evaluate different
parameters selection.
More on these simulations can be found [here]()
3. Simulation where three datacenter were combined to 
represent Saas, Paas, Iaas and Faas and corresponding
workloads were assigned to them.
More on these simulation can be found [here]()

## Testing

In order to test the program, since every simulation
strongly depends on the configuration parameters 
applied to them, was based on the correctness of the
code itself, and it guarantees that each component,
if as input is given a correct configuration parameter
name, it returns a correct instance of the object
created, otherwise it throws an error

To run the test, go to the root directory of the
project and run the following command :

```
sbt clean compile test
```

More info about test can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/tree/master/src/test/scala)

## Deployment

### Jar file

In order to do a local deployment, the
**Assembly** sbt plugin has been used, that allows
to resolve dependencies and create a single jar from
the big project.

In order to create the jar file go to the root
of the project and run the following command :
```
sbt clean compile assembly
```
Then go to the directory target/scala-3.0.2/
and the jar file should have been produced there.

### Local

In order to run the jar locally, run the following command :

```
java -cp <path-to-jar-file> Configurations.<name-of-main-method>
```

Where ```<path-to-jar-file>``` is the path of the jar file in the
file system, while ```<name-of-main-method>``` is the name
of the main method to run (it will be most likely something
like runConfn where n is the number of the configuration)

However, another constraint is required : in the same folder
of the jar file the directory topologies MUST be included
in order to work properly. That's because the CloudSimPlus
file loading process from resources seems to have a 
bug because it adds a default character ```File.separator```
and it i not compatible with the sbt runtime environment.

If it was not the case, the resource folder may have been used.

### Docker

In order to build the docker image, run the following command :

```
docker build -t cs441-homework3 .
```

And to run it use the command :

```
docker run cs441-homework3
```

The docker runs the tests and then executes the mixed simulation
of Saas, Paas and Iaas.

