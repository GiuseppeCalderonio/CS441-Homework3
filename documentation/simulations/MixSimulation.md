# Mix Simulation

This section will describe the mix simulation where
3 datacenters representing Saas, Paas and Iaas share
a workload composed by cloudlets evenly distributed.

In particular, the mapping between datacenters and 
services is :

* Saas : is represented by a simple datacenter, and the 
workload is represented by light / simple cloudlets
* Paas : is represented by a medium datacenter, and the
workload is represented by medium cloudlets
* Iaas : is represented by a large datacenter, and the
workload is represented by a large cloudlet

For this simulation a bus network topology was selected
that is represented in the following picture :

![Mix](/documentation/images/Mix.png?raw=true "Mix Network")

The distance between the broker and the datacenter is assumed higher compared to the distance
among datacenters, so also the latencies will be different.

The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        6|         6|       0
|       1|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        6|         6|       0
|       2|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        6|         9|       4
|       3|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        6|         9|       4
|       4|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        6|        10|       5
|       7|SUCCESS| 4|   0|      513| 3|       81|      20000|      20000|         20|        6|        12|       6
|       6|SUCCESS| 2|   0|       81| 2|       32|      20000|      20000|         20|        6|        38|      33
|       5|SUCCESS| 2|   1|       81| 1|       32|      20000|      20000|         20|        6|        38|      33
|       9|SUCCESS| 4|   0|      513| 4|       43|     800000|     800000|        100|        6|       234|     228
|       8|SUCCESS| 4|   0|      513| 3|       81|     800000|     800000|        100|        6|       250|     244
|      10|SUCCESS| 6|   0|     2049| 6|      717|   10000000|   10000000|        500|        6|      1116|    1111
17:38:26.134 [main] INFO  Configurations.Configuration$ - Total cost ($) for   7 active VMs from   7 total Vms created : 
17:38:26.134 [main] INFO  Configurations.Configuration$ - Processing cost:          102.26 $
17:38:26.135 [main] INFO  Configurations.Configuration$ - Memory cost:                37.47 $ 
17:38:26.135 [main] INFO  Configurations.Configuration$ - Storage cost:              13.00 $
17:38:26.135 [main] INFO  Configurations.Configuration$ - Bandwidth cost:             12.14 $
17:38:26.136 [main] INFO  Configurations.Configuration$ - Total cost:                164.87 $ 
17:38:26.136 [main] INFO  Configurations.Configuration$ - Simulation terminated
```

In particular, we can see that all the datacenters are involved
in the computation, so the load is well distributed, and also
the cost is actually very low compared to other configurations
already described. As in the other simulations, the system
bottleneck with respect to time is represented by the 
large cloudlet that is scheduled in a large VM executing
in a large Datacenter that simulates the Iaas.
Cloudlets are selected in a realistic way, such that 
high workloads to Iaas are less frequent than tiny
Saas or Faas workloads.

Also in this case the network topology plays a 
big role with respect to efficiency and pricing, since
if we try to remove the network configuration
as already done in [configuration 4](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/simulations/MultipleComponentsSimulation.md)
, the resulting cost is **26501.90 $**

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)
