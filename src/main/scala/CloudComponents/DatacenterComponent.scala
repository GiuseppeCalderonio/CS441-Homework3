package CloudComponents

import HostComponent.{configureHostsNetwork, getHostList}
import HelperUtils.{CreateLogger, Parameters}
import HelperUtils.Parameters.throwError
import com.typesafe.config.Config
import org.cloudbus.cloudsim.allocationpolicies.migration.{VmAllocationPolicyMigration, VmAllocationPolicyMigrationBestFitStaticThreshold}
import org.cloudbus.cloudsim.allocationpolicies.*
import org.cloudbus.cloudsim.brokers.DatacenterBroker
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.hosts.network.NetworkHost
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.selectionpolicies.{VmSelectionPolicy, VmSelectionPolicyMinimumMigrationTime, VmSelectionPolicyMinimumUtilization, VmSelectionPolicyRandomSelection}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic

import scala.jdk.CollectionConverters.*

/**
 * This object contains all the necessary functions to load at runtime
 * the Datacenter component from the configuration file
 */
object DatacenterComponent {

  /**
   * this attribute represents the logger for this object
   */
  private val logger = CreateLogger(classOf[DatacenterComponent.type])

  /**
   * this attribute represents the name of the component on the configuration file
   */
  private val configName = "datacenter"

  /**
   * this attribute represents the root configuration name of the configuration file
   */
  private val rootConfigName = Parameters.componentsConfig

  /**
   * this attribute represents the configuration object for this component
   */
  private val config = rootConfigName.getConfig(configName)

  /**
   * this attribute represents a list of all the possible value names that this component can
   * have in the configuration file
   */
  private val datacenterTypes = Parameters.datacenterTypes

  /**
   * this method is used to load a Datacenter component given its type name
   * in order to have a better understanding, see src/main/resources/components/datacenter.conf
   *
   * @param datacenterType this parameter represents the type of the Datacenter to create
   * @return the Datacenter of the specific type "datacenterType" if it exists, throws an exception
   *         if the type specified datacenter does not exist
   */
  def getDatacenterConfig(datacenterType: String)(implicit cloudsim : CloudSim): Datacenter = {

    Parameters.checkType(configName, datacenterType, datacenterTypes)

    logger.info(s"Creating a new $configName of type $datacenterType")

    val partialPath = s"$datacenterType"

    // creates datacenter with hosts and stuff like that

    val hostList = getHosts(s"$partialPath.hosts")
    val allocationPolicy = getAllocationPolicy(s"$partialPath.allocationPolicy")
    val costPerSecond = config.getDouble(s"$partialPath.costPerSecond")
    val costPerMem = config.getDouble(s"$partialPath.costPerMem")
    val costPerStorage = config.getDouble(s"$partialPath.costPerStorage")
    val costPerBw = config.getDouble(s"$partialPath.costPerBw")
    val schedulingInterval = config.getDouble(s"$partialPath.schedulingInterval")
    val migrationPolicy = getMigrationPolicy(s"$partialPath")

    val datacenter = new NetworkDatacenter(cloudsim, hostList.asInstanceOf[List[NetworkHost]].asJava, migrationPolicy)

    datacenter.setVmAllocationPolicy(allocationPolicy)
    datacenter.setSchedulingInterval(schedulingInterval)
    datacenter.getCharacteristics
      .setCostPerBw(costPerBw)
      .setCostPerMem(costPerMem)
      .setCostPerSecond(costPerSecond)
      .setCostPerStorage(costPerStorage)

    configureHostsNetwork(cloudsim, datacenter)

    // notify front end about success of loaded config

    logger.info(s"$configName created")

    datacenter

  }

  /**
   * this method is used to load the migration policy of the datacenter at runtime
   * @param partialPath this parameter represents the partial path of the migration policy
   *                    in the configuration file
   * @return the migration policy associated with the datacenter
   */
  private def getMigrationPolicy(partialPath: String) : VmAllocationPolicyMigration = {
    val selectionPolicy : VmSelectionPolicy = config.getString(s"$partialPath.migrationPolicy") match {
      case "RandomSelection" => new VmSelectionPolicyRandomSelection()
      case "MinimumUtilization" => new VmSelectionPolicyMinimumUtilization()
      case "MinimumMigrationTime" => new VmSelectionPolicyMinimumMigrationTime()
      case _ => throwError(s"$partialPath.migrationPolicy")
        null
    }
    val maximumUtilization = config.getDouble(s"$partialPath.maximumUtilization")

    new VmAllocationPolicyMigrationBestFitStaticThreshold(selectionPolicy, maximumUtilization)

  }

  private def getAllocationPolicy(allocationPolicyType : String) : VmAllocationPolicy = {
    config.getString(allocationPolicyType) match {
      case "Simple" => new VmAllocationPolicySimple()
      case "RoundRobin" => new VmAllocationPolicyRoundRobin()
      case "BestFit" => new VmAllocationPolicyBestFit()
      case "FirstFit" => new VmAllocationPolicyFirstFit()
      case _ => throwError(allocationPolicyType)
                null
    }
  }

  /**
   * this method is used to load at runtime a list of hosts given it name on the
   * configuration file
   * @param hostListConfigName this parameter represents the name of the hosts to load
   *                           in the configuration file
   * @return a list of hosts if the configuration file was set up correctly, throws
   *         an exception otherwise
   */
  private def getHosts(hostListConfigName : String) : List[Host] = {

    // get hosts config
    val hostListConfig = config.getConfigList(hostListConfigName).asScala.toList

    // map into a list all the relevant info of the host
    val hostInfo = hostListConfig.map( hostConfig => {
      val hostType = hostConfig.getString(s"type")
      val count = hostConfig.getInt(s"count")
      val vmScheduler = getVmScehduler(s"vmScheduler", hostConfig)
      (hostType, count, vmScheduler)
      }
    )

    // decoupe all the informaion in different lists
    val hostTypeList = hostInfo.map( info => info._1 )
    val countList = hostInfo.map( info => info._2 )
    val vmScheduler = hostInfo.map( info => info._3 )

    // load the hosts and assign them the vm scheduler, then return
    getHostList(countList.zip(hostTypeList))
      .zip(vmScheduler)
      .map( hosts_vmSchedulers => {
        hosts_vmSchedulers._1.setVmScheduler(hosts_vmSchedulers._2)
        hosts_vmSchedulers._1
      } )

  }

  /**
   * this method is used to load the vm scheduler of the datacenter at runtime
   * @param vmSchedulerType this parameter represents the type of the vm scheduler
   * @param hostConfig this parameter represents the confguration object where the value is stored
   *                   (in this case, an host type)
   * @return
   */
  def getVmScehduler(vmSchedulerType : String, hostConfig : Config) : VmScheduler = {

    hostConfig.getString(vmSchedulerType) match {
      case "SpaceShared" => new VmSchedulerSpaceShared()
      case "TimeShared" => new VmSchedulerTimeShared()
      case _ => throwError(vmSchedulerType)
                null
    }

  }

  /**
   * this method is used to load a list of Datacenters given a list of pairs, where the
   * second element represents the type of the host to create, while the first element
   * represents how many datacenters of that type to create
   *
   * @param datacenterListConfig this parameter represents a list containing all the necessary
   *                           info about datacenters to create
   * @return a list of Datacenters
   */
  def getDatacenterList(datacenterListConfig: List[(Int, String)])(implicit cloudsim : CloudSim): List[Datacenter] = {

    datacenterListConfig.flatMap(
      config => {
        (0 until config._1).
          toList.
          map(_ => getDatacenterConfig(config._2))
      }
    )
  }

}
