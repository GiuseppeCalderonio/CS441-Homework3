# Host

## Description

This component represents the host.

The configurable parameters are:

* pes : number of PEs of the host (processors) [Long]
* ram : total ram in MegaBytes of the host [Long]
* bandwidth : total bandwidth in Megabits/s of the host [Long]
* storage : total storage capacity in MegaBytes of the host [Long]
* powerModel : power parameters of the host


## Types

![Host Types](/documentation/images/Hosts.png?raw=true "Host Types")

## PEs

This component represents the host PE.

The configurable parameters are:

* mips : mips of each PE of the host (million instructions per second) [Double]

### Types

![PE Types](/documentation/images/PEs.png?raw=true "PE Types")

## Power Model

This component represents the host Power Model.

The configurable parameters are:

* maxPower : maximum power in watts that the host consumes [Double]
* staticPower : average power in watts that the host consumes [Double]
* startupDelay : delay (in seconds) for starting up the Host [Double]
* shutdownDelay : delay (in seconds) for shutting down the Host [Double]
* startupPower : power consumed (in Watts) for starting up the Host [Double]
* shutdownPower : power consumed (in Watts) for shutting down the Host [Double]

### Types

![PowerModel Types](/documentation/images/PowerModel.png?raw=true "PowerModel Types")
