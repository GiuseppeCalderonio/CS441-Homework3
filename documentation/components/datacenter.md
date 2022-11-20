# Datacenter

## Description

This component represents the datacenter.

The configurable parameters are:

* allocationPolicy : allocation policy, it can be Simple, RoundRobin, BestFit, FirstFit
* costPerSecond : cost in dollars per each second of activity
* costPerMem : cost in dollars per each MB used for ram
* costPerStorage : cost in dollars per each MB used for storage
* costPerBw : cost in dollars per each MB/s of bandwidth used
* schedulingInterval : scheduling interval in seconds [Double]
* migrationPolicy : migration policy type, it can be RandomSelection, MinimumUtilization, MinimumMigrationTime [String]
* maximumUtilization : maximum utilization in percentage between 0 and 1 after which the migration triggers [Double]
* hosts : array of hosts belonging to a datacenter

More info on the types of hosts can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/host.md)

## Types

### Simple

![Datacenter Simple](/documentation/images/DatacenterSimple.png?raw=true "Datacenter Simple")

### Medium

![Datacenter Medium](/documentation/images/DatacenterMedium.png?raw=true "Datacenter Medium")

### Large

![Datacenter Large](/documentation/images/DatacenterLarge.png?raw=true "Datacenter Large")
