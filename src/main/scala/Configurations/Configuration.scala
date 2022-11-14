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
import org.cloudbus.cloudsim.vms.Vm
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

    datacenterList.foreach(datacenter => println(datacenter.getHostList))

    cloudsim.start()
    
    val finishedCloudlets = broker.getCloudletFinishedList
    new CloudletsTableBuilder(finishedCloudlets).build() // prints out the result

    logger.info(s"\n\nSimulation terminated")

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

}
