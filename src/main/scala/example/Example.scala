package example

import org.cloudbus.cloudsim.allocationpolicies.migration.VmAllocationPolicyMigrationBestFitStaticThreshold
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.{CloudSim, SimEntity}
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.{Host, HostSimple, HostSuitability}
import org.cloudbus.cloudsim.network.switches.Switch
import org.cloudbus.cloudsim.network.topologies.{BriteNetworkTopology, NetworkTopology}
import org.cloudbus.cloudsim.power.PowerMeasurement
import org.cloudbus.cloudsim.power.models.{PowerModelDatacenter, PowerModelHostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple, Processor}
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}
import org.cloudbus.cloudsim.schedulers.cloudlet.network.CloudletTaskSchedulerSimple
import org.cloudbus.cloudsim.selectionpolicies.{VmSelectionPolicy, VmSelectionPolicyMinimumMigrationTime, VmSelectionPolicyMinimumUtilization, VmSelectionPolicyRandomSelection}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudsimplus.autoscaling.resources.{ResourceScalingGradual, ResourceScalingInstantaneous}
import org.cloudsimplus.autoscaling.{HorizontalVmScalingSimple, VerticalVmScalingSimple}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.cloudsimplus.listeners.{DatacenterVmMigrationEventInfo, EventInfo, EventListener}
import HelperUtils.{CreateLogger, Parameters}

import scala.jdk.CollectionConverters.*


/*
Comments
I guess that every time a set of hosts is created, we can set the maximum number of virtual machines
that it can have and then the tool tries automatically to associate to each host a set of them
in order (so if there are 4 hosts with 2 space available and 3 VMs, then the first host will be
full while the second wil have just 1 VM space occupied and the other 2 are free)

*/




object Example {
  /*
  val config = ObtainConfigReference("implementation-test") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  */
  private val logger = CreateLogger(classOf[Example.type ])
  private val utilizationModel: UtilizationModelDynamic = new UtilizationModelDynamic(3) // initial utilization percentage

  @main def Start(): Unit =

    println(s"${Parameters.componentsConfig}\n\n\n\n\n\n\n\n\n\n")

    val cloudsim = new CloudSim()
    val datacenter0 = createDataCenter(cloudsim)

    /*

    // various methods that can be useful

    datacenter0.enableMigrations()
    datacenter0.setHostSearchRetryDelay()
    datacenter0.setBandwidthPercentForMigration()
    datacenter0.setSchedulingInterval()
    datacenter0.setDatacenterStorage()
    datacenter0.addOnVmMigrationFinishListener()

    cloudsim.setNetworkTopology()
    datacenter0.setPowerModel()


    // how to do migration policies (I should implement them though)

    val v1 = new VmSelectionPolicyRandomSelection()
    val v2 = new VmSelectionPolicyMinimumUtilization
    val v3 =  new VmSelectionPolicyMinimumMigrationTime
    val maximumUtilization = 80

    val allocationPolicy =
      new VmAllocationPolicyMigrationBestFitStaticThreshold(
        v1, // or v2 or v3
        maximumUtilization);


    val dc = new DatacenterSimple(cloudsim, getHostList(3).asJava, allocationPolicy);

    // how to schedule complex cloudlets executions

            // just creating some of them
    val cs1 = new CloudletSchedulerSpaceShared
    val cs3 = new CloudletSchedulerTimeShared

    val vm = new VmSimple(10, 19)
    vm.setRam(0).setBw(0).setSize(0)
      .setCloudletScheduler(cs1) // or cs3

    // to create routers / switches

    // to create network topologies

    new BriteNetworkTopology(inputFile); // takes as input the path of the Config file for the topology

    // to design autoscaling algorithms

      // horizontally

    def createVm(a : Int) : Vm = {
      new VmSimple(1, 1)
    }
    def predicate(vm : Vm) : Boolean = vm.getCpuPercentUtilization() > 0.7

    val a = 1

    val hs = new HorizontalVmScalingSimple
    hs.setVmSupplier(() => createVm(a)) // it takes a method as a parameter (and example of how to pass parameters to it)
    hs.setOverloadPredicate( vm => predicate(vm) ) // it takes a predicate method as a parameter (and example of how to pass parameters to it)

    vm.setHorizontalScaling(hs)

      // vertically

    val vs = new VerticalVmScalingSimple(classOf[Processor], 0.1) // create vertical scaling

    // how to scale resources
    vs.setResourceScaling(new ResourceScalingGradual)
    vs.setResourceScaling(new ResourceScalingInstantaneous)

    val f : Vm => Double = fun

    def fun(vm: Vm) : Double = 0.8

    vs.setLowerThresholdFunction( vm => fun(vm) )
    vs.setUpperThresholdFunction( vm => fun(vm))

    vm.setBwVerticalScaling(vs)
    vm.setPeVerticalScaling(vs)
    vm.setBwVerticalScaling(vs)


    // to design scheduling algorithms

    val host = new HostSimple(1, 1, 1)
    host.setVmScheduler(new VmSchedulerTimeShared())





    // how to create an host with power consumption

    val HOST_START_UP_DELAY = 5;
    val HOST_SHUT_DOWN_DELAY = 3;
    val HOST_START_UP_POWER = 5;
    val HOST_SHUT_DOWN_POWER = 3;
    val STATIC_POWER = 35;
    val MAX_POWER = 50;

    val powerModel = new PowerModelHostSimple(MAX_POWER, STATIC_POWER);
    powerModel.setStartupDelay(HOST_START_UP_DELAY)
      .setShutDownDelay(HOST_SHUT_DOWN_DELAY)
      .setStartupPower(HOST_START_UP_POWER)
      .setShutDownPower(HOST_SHUT_DOWN_POWER);
    val host1 = new HostSimple(1,1,1, createPe(20).asJava)
    host1.setPowerModel(powerModel)



    */

    val datacenter1 = createDataCenter(cloudsim); //  create a datacenter.conf containing some hosts
    val broker0 = new DatacenterBrokerSimple(cloudsim) // creates a broker (entity that gives datacenters load)

    val vmList = createVms(5) // creates a list of vm
    val cloudletList = createCloudlets(1) // creates a set of workloads to be executed
    broker0.submitVmList(vmList.asJava) // gives to a broker (customer) a set of VMs that then will be
                                        // sent to the datacenter.conf to be allocated
    broker0.submitCloudletList(cloudletList.asJava) // gives to a broker a set of cloudlets that will
    // be executed to the datacenter.conf to execute

    cloudsim.start()
    val finishedCloudlets = broker0.getCloudletFinishedList
    new CloudletsTableBuilder(finishedCloudlets).build() // prints out the result

  def createDataCenter(cloudsim: CloudSim): Datacenter =
    val hostList = getHostList(3) // number of hosts
    new DatacenterSimple(cloudsim, hostList.asJava)

  def getHostList(count: Long): List[Host] =
    if(count < 1) return Nil
    val peList: List[Pe] = createPe(20) // number of PEs

    new HostSimple(32, // ram in MB
      256, // Bandwidth in MB per second
      128, // storage in MB
      peList.asJava) :: getHostList(count - 1)

  def createPe(count: Long): List[Pe] =
    if(count < 1) return Nil
    val pe = new PeSimple(10) // HOST_MIPS = million instructions per second
    pe :: createPe(count - 1)

  def createVms(count: Long): List[Vm] =
    if(count < 1) return Nil
    val vm = new VmSimple(10, // vm MIPS = = million instructions per second
      19) // number of PEs
    vm.setRam(32) // ram in MB
      .setBw(256) // Bandwidth in MB per second
      .setSize(64) // storage in MB
      :: createVms(count - 1)

  def createCloudlets(count: Long): List[Cloudlet] =
    if(count < 1) return Nil
    val cloudlet = new CloudletSimple(
      4, // cloudlet lenght = the length or size (in MI = MillionInstructions)
                // of this cloudlet to be executed in a VM
      17, // pesNumber - number of PEs that Cloudlet will require to be completed (?)
      utilizationModel) // the utilization model
    cloudlet.setSizes(16) // Sets the input and output file sizes of this Cloudlet to the same value (in bytes).
    cloudlet :: createCloudlets(count - 1)





}