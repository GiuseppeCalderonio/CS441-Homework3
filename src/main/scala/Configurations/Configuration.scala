package Configurations

import Components.CloudletComponent.*
import Components.DatacenterComponent.*
import Components.VmComponent.getVmList
import HelperUtils.{CreateLogger, Parameters}
import com.typesafe.config.Config
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.vms.network.NetworkVm
import org.cloudbus.cloudsim.vms.{Vm, VmCost}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

import scala.jdk.CollectionConverters.*

object Configuration {

  private val logger = CreateLogger(classOf[Configuration.type])

  private val rootConfigName = Parameters.configsConfig


  def getConfig(configName : String) : Unit = {

    logger.info(s"Starting simulation with config $configName")

    val config = rootConfigName.getConfig(configName)

    implicit val cloudsim: CloudSim = new CloudSim()

    val broker = new DatacenterBrokerSimple(cloudsim)

    val datacenterList = getComponentList[Datacenter]("datacenters", getDatacenterList, config)
    val vmList = getComponentList[Vm]("vms", getVmList, config)
    val cloudletList = getComponentList[Cloudlet]("cloudlets", getCloudletList, config)

    broker.submitCloudletList(cloudletList.asJava)
    broker.submitVmList(vmList.asJava)

    configureNetwork(broker, datacenterList, cloudsim, s"topologies/$configName.brite")

    //assert(vmList.forall(vm => vm.isInstanceOf[NetworkVm]))

    cloudsim.start()

    new CloudletsTableBuilder(broker.getCloudletFinishedList).build()

    computeCost(broker)

    logger.info(s"Simulation terminated")

  }

  def getComponentList[T](componentNameConfig : String, getComponentFunction : List[(Int, String)] => List[T], config : Config)(implicit cloudsim: CloudSim): List[T] = {

    val componentsConfig = config.getConfigList(componentNameConfig).asScala.toList

    val list_Count_Component = componentsConfig.map(componentConfig => {
      val componentType = componentConfig.getString(s"type")
      val count = componentConfig.getInt(s"count")

      (count, componentType)

    })

    getComponentFunction(list_Count_Component)
  }


  def configureNetwork(broker : DatacenterBroker, datacenters : List[Datacenter], simulation : CloudSim, networkTopologyFileName : String): Unit = {

    val networkTopology = BriteNetworkTopology.getInstance(networkTopologyFileName)

    simulation.setNetworkTopology(networkTopology)

    networkTopology.mapNode(broker, 0)

    datacenters.indices.foreach(index => networkTopology.mapNode(datacenters(index), index + 1) )

  }


  def computeCost(broker : DatacenterBroker): Unit = {

    val vms = broker.getVmCreatedList[Vm].asScala

    val vmCostList = vms.map(vm => new VmCost(vm))
    val processingCost = vmCostList.map( vmCost => vmCost.getProcessingCost ).sum
    val memoryCost = vmCostList.map( vmCost => vmCost.getMemoryCost ).sum
    val storageCost = vmCostList.map( vmCost => vmCost.getStorageCost ).sum
    val bandwidthTotalCost = vmCostList.map( vmCost => vmCost.getBwCost ).sum

    val totalCost = processingCost + memoryCost + storageCost + bandwidthTotalCost

    val totalNonIdleVms = vms.map( vm => if(vm.getTotalExecutionTime > 0) 1 else 0).sum

    logger.info(f"Total cost ($$) for $totalNonIdleVms%3d active VMs from ${broker.getVmsNumber}%3d total Vms created : " )
    logger.info(f"Procssing cost:   " + f"$processingCost%8.2f $$")
    logger.info(f"Memory cost   " + f" $memoryCost%13.2f $$ ")
    logger.info(f"Storage cost:   " + f"$storageCost%11.2f $$" )
    logger.info(f"Bandwidth cost:   " + f" $bandwidthTotalCost%8.2f $$" )
    logger.info(f"Total cost:   " + f" $totalCost%13.2f $$ ")


  }


}
