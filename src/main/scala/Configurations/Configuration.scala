package Configurations

import CloudComponents.CloudletComponent.getCloudletList
import CloudComponents.DatacenterComponent.getDatacenterList
import CloudComponents.VmComponent.getVmList
import HelperUtils.CreateLogger
import com.typesafe.config.{Config, ConfigFactory}
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerBestFit, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.vms.network.NetworkVm
import org.cloudbus.cloudsim.vms.{Vm, VmCost}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import HelperUtils.Parameters

import java.io.File
import scala.jdk.CollectionConverters.*

/**
 * this object represents the core of the simulation, where all the pieces are
 * loaded at runtime using some helper methods, starts the simulation,
 * and returns a detailed description of the results obtained
 */
object Configuration {

  /**
   * this attribute represents the logger for this object
   */
  private val logger = CreateLogger(classOf[Configuration.type])

  /**
   * this attribute represents the root configuration name of the configuration file
   */
  private val rootConfigName = Parameters.configsConfig

  /**
   * this method represents the core of the simulation, where all the pieces are
   * loaded at runtime using some helper methods, starts the simulation,
   * and returns a detailed description of the results obtained
   * @param configName this parameter represents the name in the
   *                   configuration file of the simulation to simulate
   * @param isNetworkConfigured this parameter is used to decide whether the network
   *                            has to be configured or not
   * @return always zero by default
   */
  def getConfig(configName : String, isNetworkConfigured : Boolean = true) : Int = {

    logger.info(s"Starting simulation with config $configName")

    // loads config
    val config = rootConfigName.getConfig(configName)

    logger.info(s"Creating new simulation")
    // creates simulation
    implicit val cloudsim: CloudSim = new CloudSim()

    logger.info(s"Creating new Broker")
    // creates broker
    val broker = new DatacenterBrokerBestFit(cloudsim)

    logger.info(s"Loading components")
    // loads compnents
    val datacenterList = getComponentList[Datacenter]("datacenters", getDatacenterList, config)
    val vmList = getComponentList[Vm]("vms", getVmList, config)
    val cloudletList = getComponentList[Cloudlet]("cloudlets", getCloudletList, config)

    logger.info(s"Submitting Components")
    // submit compnents
    broker.submitCloudletList(cloudletList.asJava)
    broker.submitVmList(vmList.asJava)

    if(isNetworkConfigured){

      logger.info(s"Configuring network")
      // configure network
      val networkTopologyFileName = s"topologies/$configName.brite"
      configureNetwork(broker, datacenterList, cloudsim, networkTopologyFileName)
    }

    logger.info(s"Starting simulation")
    // start simulation
    cloudsim.start()

    // show results
    new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
    computeCost(broker)

    logger.info(s"Simulation terminated")

    0

  }

  /**
   * this method is used to load at runtime a list of components given its type, its function
   * used to be created, and its name in the configuration file
   * @param componentNameConfig this parameter represents the name of the component
   *                            in the configuration file
   * @param getComponentFunction this parameter represents the function to use to load at
   *                             runtime the component
   * @param config this parameter represents the configuration object used to load the component
   * @param cloudsim this parameter is used to create the datacenter in case of the type being a datacenter
   * @tparam T this parameter represents the scala type of the component to create
   * @return a list of the components created of type T
   */
  def getComponentList[T](componentNameConfig : String, getComponentFunction : List[(Int, String)] => List[T], config : Config)(implicit cloudsim: CloudSim): List[T] = {

    val componentsConfig = config.getConfigList(componentNameConfig).asScala.toList

    val list_Count_Component = componentsConfig.map(componentConfig => {
      val componentType = componentConfig.getString(s"type")
      val count = componentConfig.getInt(s"count")

      (count, componentType)

    })

    getComponentFunction(list_Count_Component)
  }

  /**
   * this method is used to configure the simulation network that connects multiple datacenters
   * @param broker this parameter represents the broker of the network
   * @param datacenters this parameter represents the list of datacenters in the network
   * @param simulation this parameter represents the simulation object of the simulation
   * @param networkTopologyFileName this parameter represents the .brite name of the file where
   *                                the network is configured
   */
  def configureNetwork(broker : DatacenterBroker, datacenters : List[Datacenter], simulation : CloudSim, networkTopologyFileName : String): Unit = {

    logger.info(s"Configuring network from file $networkTopologyFileName")

    val networkTopology = BriteNetworkTopology.getInstance(networkTopologyFileName)

    simulation.setNetworkTopology(networkTopology)

    networkTopology.mapNode(broker, 0)

    datacenters.indices.foreach(index => networkTopology.mapNode(datacenters(index), index + 1) )

  }

  /**
   * this method is used to compute the cost of the simulation
   * @param broker this parameter represents the broker of the simulation, that
   *               contains all the cost information
   */
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
    logger.info(f"Processing cost: " + f"$processingCost%15.2f $$")
    logger.info(f"Memory cost:     " + f" $memoryCost%15.2f $$ ")
    logger.info(f"Storage cost:    " + f"$storageCost%15.2f $$" )
    logger.info(f"Bandwidth cost:  " + f" $bandwidthTotalCost%15.2f $$" )
    logger.info(f"Total cost:      " + f" $totalCost%15.2f $$ ")


  }


}
