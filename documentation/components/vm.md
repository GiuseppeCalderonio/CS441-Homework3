# VM

## Description

This component represents the vm.

The configurable parameters are:

* pes: number of PEs of the VM [Long]
* ram: RAM capacity in Megabytes of the VM [Long]
* bw: Bandwidth capacity (in Megabits/s) [Long]
* size: storage size (capacity) of the VM image in Megabytes [Long]
* scalingType: type of scaling, it can be horizontal or vertical [String]
* scalingComplexity: complexity of scaling, it can be simple, medium or large [String]

## Types

![VM types](/documentation/images/VM.png?raw=true "VM types")

## VM PEs

This component represents the vm pes.

The configurable parameters are:

* mips: the mips capacity of each Vm [Double]

### Types

![PEs types](/documentation/images/PEs.png?raw=true "PEs types")
