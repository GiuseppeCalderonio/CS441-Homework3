# Single Component Simulations

## Introduction

These set of three simulations verify each type of datacenter and assign to it
the expected type of workload for it.

In particular, the simulations test 

* Simple datacenter with simple workload
* Medium datacenter with medium workload
* Large datacenter with large workload

### Simple

This simulation can be also represented by a Saas service with some Faas / microservices provided;
Indeed, the cloudlets submitted are :

1. Light : representing the typical Faas (for example an http request)
2. Simple : representing the typical Saas 


The results of the simulation are showed below :
```
|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        0|         0|       0
|       1|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        0|         0|       0
|       2|SUCCESS| 2|   0|       81| 0|       32|      20000|      20000|         20|        0|        40|      40
15:44:39.778 [main] INFO  Configurations.Configuration$ - Total cost ($) for   1 active VMs from   1 total Vms created : 
15:44:39.779 [main] INFO  Configurations.Configuration$ - Processing cost:            8.02 $
15:44:39.780 [main] INFO  Configurations.Configuration$ - Memory cost:                10.24 $ 
15:44:39.780 [main] INFO  Configurations.Configuration$ - Storage cost:               0.50 $
15:44:39.781 [main] INFO  Configurations.Configuration$ - Bandwidth cost:              5.12 $
15:44:39.782 [main] INFO  Configurations.Configuration$ - Total cost:                 23.88 $ 
```