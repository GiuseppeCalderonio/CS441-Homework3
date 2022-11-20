# Multiple Components Simulations

This section describes a set of simulations characterized by several datacenters subject 
to different types of cloudlets, for this reason the network topology
of various datacenters will be described.

## Conf 1

The [first configuration](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/configs/config1.conf)
has the following network topology :

![Conf1](/documentation/images/Conf1.png?raw=true "Conf1 Network")

It is the implementation of a ring topology with 4 nodes, where the first node
can be seen as a load balancer.

The distance between the broker and the datacenter is assumed higher compared to the distance
among datacenters, so also the latencies will be different.

The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       1|SUCCESS| 4|   0|      513| 1|       43|      20000|      20000|         20|        4|        11|       7
|       2|SUCCESS| 4|   0|      513| 1|       43|      20000|      20000|         20|        4|        21|      17
|       3|SUCCESS| 4|   0|      513| 1|       43|      20000|      20000|         20|        4|        31|      27
|       0|SUCCESS| 2|   0|       81| 0|       32|      20000|      20000|         20|        4|        36|      33
|       4|SUCCESS| 4|   0|      513| 1|       43|      20000|      20000|         20|        4|        41|      37
|       5|SUCCESS| 4|   0|      513| 1|       43|      20000|      20000|         20|        4|        51|      47
|       6|SUCCESS| 8|   0|     2049| 4|      717|   10000000|   10000000|        500|        4|      1115|    1111
|       7|SUCCESS| 8|   1|     2049| 5|      717|   10000000|   10000000|        500|        4|      1115|    1111
16:41:19.338 [main] INFO  Configurations.Configuration$ - Total cost ($) for   7 active VMs from   7 total Vms created : 
16:41:19.338 [main] INFO  Configurations.Configuration$ - Processing cost:          101.23 $
16:41:19.339 [main] INFO  Configurations.Configuration$ - Memory cost:                99.42 $ 
16:41:19.339 [main] INFO  Configurations.Configuration$ - Storage cost:              32.00 $
16:41:19.339 [main] INFO  Configurations.Configuration$ - Bandwidth cost:             31.11 $
16:41:19.339 [main] INFO  Configurations.Configuration$ - Total cost:                263.77 $
```

First of all, we can see that the load is evenly distribute among datacenters, since 3 out of 4 datacenters
are used overall (an higher workload will result in all of them to be activated),
then we can analyze that the cost is slightly lower than a pure Iaas because the workload is
distributed among large and simple cloudlets, so the simple ones reduce the complexity and thus
the cost.

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)

## Conf 2

The [second configuration](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/configs/config2.conf)
has the following network topology :

![Conf2](/documentation/images/Conf2.png?raw=true "Conf2 Network")

It is the implementation of a star topology with 6 nodes, where the first node
can be seen as a load balancer.

The distance between the broker and the datacenter is assumed higher compared to the distance
among datacenters, so also the latencies will be different.

The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|         6|       0
|       1|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|         6|       0
|       2|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|         6|       0
|       3|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|         6|       0
|       4|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|         9|       4
|       5|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|         9|       4
|       6|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        10|       5
|       7|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        10|       5
|       8|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        10|       5
|       9|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        10|       5
|      10|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        11|       6
|      11|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        11|       6
|      12|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        11|       6
|      13|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        11|       6
|      14|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        11|       6
|      15|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        11|       6
|      16|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        12|       6
|      17|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        12|       6
|      18|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        12|       6
|      19|SUCCESS|10|   0|      513| 0|       14|        100|        100|          4|        6|        12|       6
|      22|SUCCESS|10|   0|      513| 1|       56|      20000|      20000|         20|        6|        12|       6
|      20|SUCCESS|10|   0|      513| 0|       14|      20000|      20000|         20|        6|        20|      14
|      23|SUCCESS|10|   0|      513| 1|       56|      20000|      20000|         20|        6|        20|      14
|      21|SUCCESS|10|   0|      513| 0|       14|      20000|      20000|         20|        6|        30|      24
|      24|SUCCESS|10|   0|      513| 1|       56|      20000|      20000|         20|        6|        30|      24
|      25|SUCCESS|10|   0|      513| 1|       56|      20000|      20000|         20|        6|        40|      34
|      26|SUCCESS|10|   0|      513| 1|       56|      20000|      20000|         20|        6|        50|      44
|      27|SUCCESS|10|   0|      513| 1|       56|      20000|      20000|         20|        6|        60|      54
|      28|SUCCESS|12|   0|     2049| 2|      307|     800000|     800000|        100|        6|       119|     114
16:56:38.639 [main] INFO  Configurations.Configuration$ - Total cost ($) for   7 active VMs from   7 total Vms created : 
16:56:38.639 [main] INFO  Configurations.Configuration$ - Processing cost:            2.31 $
16:56:38.640 [main] INFO  Configurations.Configuration$ - Memory cost:               161.60 $ 
16:56:38.640 [main] INFO  Configurations.Configuration$ - Storage cost:              51.00 $
16:56:38.640 [main] INFO  Configurations.Configuration$ - Bandwidth cost:             50.40 $
16:56:38.640 [main] INFO  Configurations.Configuration$ - Total cost:                265.31 $
```

This example represents an over provisioning example, since the cloudlets are mainly of
light and simple types, while they are processed by medium and large datacenters in 
medium and large VMs; for this reason, the cost is very high compared to a real case.
To prove this, it's enough to change the types of VMs from large to simple, and we will see immediately 
a cost reduction (around 50 $ compared to 265).
To further prove this analysis, we can see that the load is not evenly distributed among datacenters, because
only the datacenter with id 10 and 12 are activated, being 2 out of 6.

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)

## Conf 3

The [third configuration](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/configs/config3.conf)
has the following network topology :

![Conf3](/documentation/images/Conf3.png?raw=true "Conf3 Network")

It is the implementation of a bus topology with 3 nodes, where the first node
can be seen as a load balancer.

The distance between the broker and the datacenter is assumed higher compared to the distance
among datacenters, so also the latencies will be different.

The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|      40|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         4|       0
|      41|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         4|       0
|      42|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         4|       0
|      43|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         4|       0
|       0|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|         4|       0
|       1|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|         4|       0
|       8|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|         4|       0
|       9|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|         4|       0
|      16|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|         4|       0
|      17|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|         4|       0
|      24|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|         4|       0
|      25|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|         4|       0
|      32|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|         4|       0
|      33|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|         4|       0
|      44|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         5|       2
|      45|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         5|       2
|      46|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      47|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      48|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      49|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      50|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      51|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      52|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      53|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         6|       3
|      97|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|         6|       3
|      54|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         7|       4
|      55|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|         7|       4
|       2|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|         9|       6
|       3|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|         9|       6
|      10|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|         9|       6
|      11|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|         9|       6
|      18|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|         9|       6
|      19|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|         9|       6
|      26|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|         9|       6
|      27|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|         9|       6
|      34|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|         9|       6
|      35|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|         9|       6
|      56|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        10|       6
|      57|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        10|       6
|      58|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        10|       6
|      59|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        10|       6
|      98|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        10|       6
|      60|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        19|      15
|      61|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        19|      15
|      99|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        19|      15
|       4|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|        19|      16
|       5|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|        19|      16
|      12|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|        19|      16
|      13|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|        19|      16
|      20|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|        19|      16
|      21|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|        19|      16
|      28|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|        19|      16
|      29|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|        19|      16
|      36|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|        19|      16
|      37|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|        19|      16
|      62|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        20|      16
|      63|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        20|      16
|      64|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        29|      25
|      65|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        29|      25
|     100|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        29|      25
|       6|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|        29|      26
|       7|SUCCESS| 2|   0|      513| 0|       32|        100|        100|          4|        4|        29|      26
|      14|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|        29|      26
|      15|SUCCESS| 2|   0|      513| 1|       32|        100|        100|          4|        4|        29|      26
|      22|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|        29|      26
|      23|SUCCESS| 2|   0|      513| 2|       32|        100|        100|          4|        4|        29|      26
|      30|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|        29|      26
|      31|SUCCESS| 2|   0|      513| 3|       32|        100|        100|          4|        4|        29|      26
|      38|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|        29|      26
|      39|SUCCESS| 2|   0|      513| 4|       32|        100|        100|          4|        4|        29|      26
|      66|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        30|      26
|      67|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        30|      26
|      68|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        39|      35
|      69|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        39|      35
|     101|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        39|      35
|      70|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        40|      36
|      71|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        40|      36
|      72|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        49|      45
|      73|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        49|      45
|     102|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        49|      45
|      74|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        50|      46
|      75|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        50|      46
|      76|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        59|      55
|      77|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        59|      55
|     103|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        59|      55
|      78|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        60|      56
|      79|SUCCESS| 4|   0|     2049| 5|       66|        100|        100|          4|        4|        60|      56
|      80|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|        69|      65
|     104|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        69|      65
|      81|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|        79|      75
|     105|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        79|      75
|      82|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|        89|      85
|     106|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        89|      85
|      83|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|        99|      95
|     107|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|        99|      95
|      84|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       109|     105
|     108|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       109|     105
|      85|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       117|     113
|     109|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       117|     113
|     120|SUCCESS| 4|   0|     2049| 7|        1|     800000|     800000|        100|        4|       117|     113
|      86|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       130|     126
|     110|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       130|     126
|      87|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       140|     136
|     111|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       140|     136
|      88|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       150|     146
|     112|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       150|     146
|      89|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       160|     156
|     113|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       160|     156
|      90|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       170|     166
|     114|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       170|     166
|      91|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       180|     176
|     115|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       180|     176
|      92|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       190|     186
|     116|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       190|     186
|      93|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       200|     196
|     117|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       200|     196
|      94|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       210|     206
|     118|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       210|     206
|      95|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       220|     216
|     119|SUCCESS| 4|   1|     2049| 6|       39|      20000|      20000|         20|        4|       220|     216
|      96|SUCCESS| 4|   0|     2049| 5|       66|      20000|      20000|         20|        4|       230|     226
17:08:25.045 [main] INFO  Configurations.Configuration$ - Total cost ($) for   8 active VMs from   8 total Vms created : 
17:08:25.046 [main] INFO  Configurations.Configuration$ - Processing cost:            1.75 $
17:08:25.047 [main] INFO  Configurations.Configuration$ - Memory cost:                96.51 $ 
17:08:25.047 [main] INFO  Configurations.Configuration$ - Storage cost:              30.25 $
17:08:25.047 [main] INFO  Configurations.Configuration$ - Bandwidth cost:             30.26 $
17:08:25.047 [main] INFO  Configurations.Configuration$ - Total cost:                158.77 $
```

This example, is similar to the last one, with the exception that instead of having under provisioning
we have simply an improper combination of datacenters and VMs with respect to the cloudlets 
submitted, which are mostly light and simple, however some medium / large cloudlets are 
submitted as well increasing the overall complexity.
Even if another parameter selection may decrease the cost, this configuration is still
very cheap, using only datacenters of type medium and large, and that's because these 
datacenters are required, otherwise large cloudlets can't be scheduled, and thus with the
remaining CPU, Bandwidth and RAM a big set of light and simple coludlets can be scheduled.

The numbers support the theory, since only one out of the tw large datacenters are activated in the
simulation (DC id column shows only 2 and 4).

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)

## Conf 4

The [fourth configuration](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/src/main/resources/configs/config4.conf)
has the following network topology :

![Conf4](/documentation/images/Conf4.png?raw=true "Conf4 Network")

It is the implementation of a tree topology with 7 nodes, where the first node
can be seen as a load balancer.

The distance between the broker and the datacenter is assumed higher compared to the distance
among datacenters, so also the latencies will be different.

The results of the simulation are shown below :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|         8|       0
|       1|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|         8|       0
|      16|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        7|         8|       0
|      17|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        7|         8|       0
|       8|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|         8|       0
|       9|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|         8|       0
|       2|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|        10|       3
|       3|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|        10|       3
|      18|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        7|        10|       3
|      19|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        7|        10|       3
|      10|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|        10|       3
|      11|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|        10|       3
|       4|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|        10|       3
|       5|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|        10|       3
|      12|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|        10|       3
|      13|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|        10|       3
|      29|SUCCESS| 8|   0|      513|12|       81|      20000|      20000|         20|        7|        14|       7
|       6|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|        20|      13
|       7|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        7|        20|      13
|      14|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|        20|      13
|      15|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        7|        20|      13
|      20|SUCCESS| 2|   1|       81| 3|       32|      20000|      20000|         20|        7|        40|      33
|      21|SUCCESS| 4|   0|       81| 4|       32|      20000|      20000|         20|        7|        40|      33
|      23|SUCCESS| 4|   0|       81| 6|       32|      20000|      20000|         20|        7|        40|      33
|      22|SUCCESS| 4|   1|       81| 5|       32|      20000|      20000|         20|        7|        40|      33
|      24|SUCCESS| 4|   1|       81| 7|       32|      20000|      20000|         20|        7|        40|      33
|      25|SUCCESS| 6|   0|       81| 8|       32|      20000|      20000|         20|        7|        40|      33
|      27|SUCCESS| 6|   0|       81|10|       32|      20000|      20000|         20|        7|        40|      33
|      26|SUCCESS| 6|   1|       81| 9|       32|      20000|      20000|         20|        7|        40|      33
|      28|SUCCESS| 6|   1|       81|11|       32|      20000|      20000|         20|        7|        40|      33
|      34|SUCCESS|10|   0|      513|16|      128|     800000|     800000|        100|        7|       236|     228
|      35|SUCCESS|10|   0|      513|17|      128|     800000|     800000|        100|        7|       236|     228
|      36|SUCCESS|10|   1|      513|18|      128|     800000|     800000|        100|        7|       236|     228
|      37|SUCCESS|10|   1|      513|19|      128|     800000|     800000|        100|        7|       236|     228
|      38|SUCCESS|12|   0|      513|20|      128|     800000|     800000|        100|        7|       236|     228
|      39|SUCCESS|12|   0|      513|21|      128|     800000|     800000|        100|        7|       236|     228
|      31|SUCCESS| 8|   0|      513|13|       43|     800000|     800000|        100|        7|       236|     228
|      32|SUCCESS| 8|   1|      513|14|       43|     800000|     800000|        100|        7|       236|     228
|      33|SUCCESS| 8|   1|      513|15|       43|     800000|     800000|        100|        7|       236|     228
|      30|SUCCESS| 8|   0|      513|12|       81|     800000|     800000|        100|        7|       250|     242
|      40|SUCCESS|14|   0|     2049|24|      717|   10000000|   10000000|        500|        7|      1118|    1111
|      42|SUCCESS|14|   0|     2049|26|      717|   10000000|   10000000|        500|        7|      1118|    1111
|      44|SUCCESS|14|   0|     2049|28|      717|   10000000|   10000000|        500|        7|      1118|    1111
|      41|SUCCESS|14|   1|     2049|25|      717|   10000000|   10000000|        500|        7|      1118|    1111
|      43|SUCCESS|14|   1|     2049|27|      717|   10000000|   10000000|        500|        7|      1118|    1111
17:24:31.941 [main] INFO  Configurations.Configuration$ - Total cost ($) for  29 active VMs from  29 total Vms created : 
17:24:31.941 [main] INFO  Configurations.Configuration$ - Processing cost:          482.44 $
17:24:31.941 [main] INFO  Configurations.Configuration$ - Memory cost:               181.89 $ 
17:24:31.941 [main] INFO  Configurations.Configuration$ - Storage cost:              62.00 $
17:24:31.941 [main] INFO  Configurations.Configuration$ - Bandwidth cost:             58.54 $
17:24:31.941 [main] INFO  Configurations.Configuration$ - Total cost:                784.87 $
```

This example is one of the most interesting ones, because the goal of this simulation
was to create an high intensive, balanced load and try to fit as much as possible, minimizing the
cost and spread the computation among all the datacenters as much as possible.
Indeed this configuration was designed to have as less datacenter and VMs as possible,
and the end result is a cost of almost 800 $ in almost 20 minutes.
As it's possible to see, all the datacenters are activated, and the load is evenly distributed.

Moreover, the relevance of the network tree topology is crucial to reach high performances and
load balancing; in fact, if we set the command to not accept any external network topology calling the
simulation function in this way
```
Configuration.getConfig(configName, false)
```
so introducing the false argument,
the results become :
```
                                               SIMULATION RESULTS

|Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|FinishedLen|CloudletPEs|StartTime|FinishTime|ExecTime
|--------|-------|--|----|---------|--|---------|-----------|-----------|-----------|---------|----------|--------
|      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|         MI|  CPU cores|  Seconds|   Seconds| Seconds
|       0|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|         1|       0
|       1|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|         1|       0
|      16|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        1|         1|       0
|      17|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        1|         1|       0
|       8|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|         1|       0
|       9|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|         1|       0
|      29|SUCCESS| 8|   0|      513|12|        1|      20000|      20000|         20|        1|         9|       8
|       2|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|         9|       9
|       3|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|         9|       9
|      18|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        1|         9|       9
|      19|SUCCESS| 2|   0|       81| 2|       32|        100|        100|          4|        1|         9|       9
|      10|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|         9|       9
|      11|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|         9|       9
|       4|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|        10|       9
|       5|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|        10|       9
|      12|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|        10|       9
|      13|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|        10|       9
|       6|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|        19|      19
|       7|SUCCESS| 2|   0|       81| 0|       32|        100|        100|          4|        1|        19|      19
|      14|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|        19|      19
|      15|SUCCESS| 2|   1|       81| 1|       32|        100|        100|          4|        1|        19|      19
|      21|SUCCESS| 4|   0|       81| 4|       32|      20000|      20000|         20|        1|        33|      33
|      23|SUCCESS| 4|   0|       81| 6|       32|      20000|      20000|         20|        1|        33|      33
|      22|SUCCESS| 4|   1|       81| 5|       32|      20000|      20000|         20|        1|        33|      33
|      24|SUCCESS| 4|   1|       81| 7|       32|      20000|      20000|         20|        1|        33|      33
|      25|SUCCESS| 6|   0|       81| 8|       32|      20000|      20000|         20|        1|        33|      33
|      27|SUCCESS| 6|   0|       81|10|       32|      20000|      20000|         20|        1|        33|      33
|      26|SUCCESS| 6|   1|       81| 9|       32|      20000|      20000|         20|        1|        33|      33
|      28|SUCCESS| 6|   1|       81|11|       32|      20000|      20000|         20|        1|        33|      33
|      20|SUCCESS| 2|   1|       81| 3|       32|      20000|      20000|         20|        1|        33|      33
|      34|SUCCESS|10|   0|      513|16|        1|     800000|     800000|        100|        1|     20157|   20157
|      35|SUCCESS|10|   0|      513|17|        1|     800000|     800000|        100|        1|     20157|   20157
|      36|SUCCESS|10|   1|      513|18|        1|     800000|     800000|        100|        1|     20157|   20157
|      37|SUCCESS|10|   1|      513|19|        1|     800000|     800000|        100|        1|     20157|   20157
|      38|SUCCESS|12|   0|      513|20|        1|     800000|     800000|        100|        1|     20157|   20157
|      39|SUCCESS|12|   0|      513|21|        1|     800000|     800000|        100|        1|     20157|   20157
|      31|SUCCESS| 8|   0|      513|13|        1|     800000|     800000|        100|        1|     21318|   21317
|      32|SUCCESS| 8|   1|      513|14|        1|     800000|     800000|        100|        1|     21318|   21317
|      33|SUCCESS| 8|   1|      513|15|        1|     800000|     800000|        100|        1|     21318|   21317
|      30|SUCCESS| 8|   0|      513|12|        1|     800000|     800000|        100|        1|     22130|   22129
|      40|SUCCESS|14|   0|     2049|24|        1|   10000000|   10000000|        500|        1|    548106|  548105
|      42|SUCCESS|14|   0|     2049|26|        1|   10000000|   10000000|        500|        1|    548106|  548105
|      44|SUCCESS|14|   0|     2049|28|        1|   10000000|   10000000|        500|        1|    548106|  548105
|      41|SUCCESS|14|   1|     2049|25|        1|   10000000|   10000000|        500|        1|    548106|  548105
|      43|SUCCESS|14|   1|     2049|27|        1|   10000000|   10000000|        500|        1|    548106|  548105
17:27:17.542 [main] INFO  Configurations.Configuration$ - Total cost ($) for  29 active VMs from  29 total Vms created : 
17:27:17.542 [main] INFO  Configurations.Configuration$ - Processing cost:       105784.63 $
17:27:17.543 [main] INFO  Configurations.Configuration$ - Memory cost:               181.89 $ 
17:27:17.543 [main] INFO  Configurations.Configuration$ - Storage cost:              62.00 $
17:27:17.543 [main] INFO  Configurations.Configuration$ - Bandwidth cost:             58.54 $
17:27:17.543 [main] INFO  Configurations.Configuration$ - Total cost:             106087.06 $ 
```

Which is incredibly higher.

More info about the types of datacenters can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/datacenter.md)

More info about the types of cloudlets can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/cloudlet.md)

More info about the type of VMs can be found [here](https://github.com/GiuseppeCalderonio/CS441-Homework3/blob/master/documentation/components/vm.md)

