# Scaling

## Horizontal Scaling

### Description

This component represents the horizontal scaling.

The configurable parameters are:

* type: type of VM that is created when horizontal scaling triggers, it can be simple, medium or large [String]
* utilization : upper bound cpu utilization in percentage (between 0 and 1) that triggers the scaling to happen [Double]
## Vertical Scaling

### Description

This component represents the vertical scaling.

The configurable parameters are:

* scalingFactor: the scaling factor between 0 and 1 that says how much to prvision / deprovision when vertcal scaling is triggered [Double]
* type : type of provisioning / deprovisioning , it can be instantaneous or gradual [String]
* lowerTreshold : the lower utilization threshold between 0 and 1 that triggers underProvisioning [Double]
* upperTreshold : the upper utilization threshold between 0 and 1 that triggers overProvisioning [Double]

### Types

![SCaling types](/documentation/images/Scaling.png?raw=true "Scaling types")

