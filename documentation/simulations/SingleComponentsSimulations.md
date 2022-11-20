# Single Component Simulations

## Introduction

These set of three simulations verify each type of datacenter and assign to it
the expected type of workload for it.

In particular, the simulations test 

* Simple datacenter with simple workload
* Medium datacenter with medium workload
* Large datacenter with large workload

### Simple

[This simulation](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/configs/simple.conf)
can be also represented by a Saas with some Faas / microservices provided;
Indeed, the cloudlets submitted are :

1. Light : representing the typical Faas (for example an http request)
2. Simple : representing the typical Saas 


The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        0|         0|       0
|       1|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        0|         0|       0
|       2|SUCCESS| 2|   0|       81| 0|       32|      20000|      20000|         20|        0|        40|      40
16:10:01.897 [main] INFO  Configurations.Configuration$ - Total cost ($) for   1 active VMs from   1 total Vms created : 
16:10:01.897 [main] INFO  Configurations.Configuration$ - Processing cost:            0.80 $
16:10:01.898 [main] INFO  Configurations.Configuration$ - Memory cost:                 1.02 $ 
16:10:01.898 [main] INFO  Configurations.Configuration$ - Storage cost:               0.50 $
16:10:01.899 [main] INFO  Configurations.Configuration$ - Bandwidth cost:              0.51 $
16:10:01.899 [main] INFO  Configurations.Configuration$ - Total cost:                  2.84 $ 
```

In particular, we can analyze that the cost is very low since usually Saas and Faas are not
workload intensive, plus the total time for the microservices is very low, while the Saas service
takes more time to execute, since it requires the interaction of the user as well.
Network is not discussed since there is only one datacenter in this types of simulations.
As we can see from the Host Id column, only one of the three simple hosts was used, meaning that
more complex workloads can be submitted; that's what we expect by a Saas / Faas since they should
be able to process many data in few time.

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)


### Medium 

[This simulation](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/configs/medium.conf)
can be also represented by a Paas;
Indeed, the cloudlets submitted are :

1. Medium : representing the typical Paas, where higher workload compared to a typical Saas is required

The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS| 2|   0|      513| 0|      128|     800000|     800000|        100|        0|       229|     228
|       1|SUCCESS| 2|   0|      513| 1|      128|     800000|     800000|        100|        0|       229|     228
|       2|SUCCESS| 2|   1|      513| 2|      128|     800000|     800000|        100|        0|       229|     228
16:10:36.500 [main] INFO  Configurations.Configuration$ - Total cost ($) for   3 active VMs from   3 total Vms created : 
16:10:36.501 [main] INFO  Configurations.Configuration$ - Processing cost:            5.49 $
16:10:36.501 [main] INFO  Configurations.Configuration$ - Memory cost:                 2.40 $ 
16:10:36.502 [main] INFO  Configurations.Configuration$ - Storage cost:               1.50 $
16:10:36.502 [main] INFO  Configurations.Configuration$ - Bandwidth cost:              0.60 $
16:10:36.503 [main] INFO  Configurations.Configuration$ - Total cost:                  9.99 $ 
```

In particular, we can analyze that the cost is higher for this type of datacenter because of the
different type of purpose, with a total of 228 seconds of execution time, which is approximately 4 minutes.
However, we can see that only 2 of the three hosts are used in this simulation, since the id 2 is not
present in the Host Id column, so it means that this kind of datacenter can handle more cloudlets in 
other VMs, but on average half of the Datacenter total power was exploited.

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)


### Large

[This simulation](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/configs/large.conf)
can be also represented by an Iaas;
Indeed, the cloudlets submitted are :

1. Large : representing the typical Iaas, where higher workload and ability to customize compared to a typical Paas is required

The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS| 2|   0|     2049| 0|      717|   10000000|   10000000|        500|        0|      1111|    1111
|       2|SUCCESS| 2|   0|     2049| 2|      717|   10000000|   10000000|        500|        0|      1111|    1111
|       1|SUCCESS| 2|   1|     2049| 1|      717|   10000000|   10000000|        500|        0|      1111|    1111
16:11:00.808 [main] INFO  Configurations.Configuration$ - Total cost ($) for   3 active VMs from   3 total Vms created : 
16:11:00.809 [main] INFO  Configurations.Configuration$ - Processing cost:          119.52 $
16:11:00.809 [main] INFO  Configurations.Configuration$ - Memory cost:                96.00 $ 
16:11:00.810 [main] INFO  Configurations.Configuration$ - Storage cost:              30.00 $
16:11:00.810 [main] INFO  Configurations.Configuration$ - Bandwidth cost:             30.00 $
16:11:00.811 [main] INFO  Configurations.Configuration$ - Total cost:                275.52 $ 
```

In particular, we can analyze that the cost, as expected, is higher compared to the Paas and Saas cases,
since this type of datacenter is more suited to high intensive workloads like big Map reduce / ML training.
In total, 20 minutes of execution within a datacenter of supercomputers was simulated.
Being this type of datacenter an high intensive one, only 2 out of three available hosts were used, meaning that
probably other high intensive set of cloudlets can be scheduled.

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)
