package Components

import HelperUtils.{CreateLogger, Parameters}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import Components.HostComponent.{configureHostsNetwork, getHostList}
import com.typesafe.config.Config
import org.cloudbus.cloudsim.hosts.Host
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}
import HelperUtils.Parameters.throwError
import org.cloudbus.cloudsim.allocationpolicies.migration.{VmAllocationPolicyMigration, VmAllocationPolicyMigrationBestFitStaticThreshold}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicy, VmAllocationPolicyBestFit, VmAllocationPolicyFirstFit, VmAllocationPolicyRandom, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.brokers.DatacenterBroker
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.hosts.network.NetworkHost
import org.cloudbus.cloudsim.selectionpolicies.{VmSelectionPolicy, VmSelectionPolicyMinimumMigrationTime, VmSelectionPolicyMinimumUtilization, VmSelectionPolicyRandomSelection}

import scala.jdk.CollectionConverters.*

object DatacenterComponent {

  private val logger = CreateLogger(classOf[DatacenterComponent.type])

  private val configName = "datacenter"
  private val rootConfigName = Parameters.componentsConfig

  private val config = rootConfigName.getConfig(configName)

  private val datacenterTypes = Parameters.datacenterTypes

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

  private def getHosts(hostListConfigName : String) : List[Host] = {

    val hostListConfig = config.getConfigList(hostListConfigName).asScala.toList

    val hostInfo = hostListConfig.map( hostConfig => {
      val hostType = hostConfig.getString(s"type")
      val count = hostConfig.getInt(s"count")
      val vmScheduler = getVmScehduler(s"vmScheduler", hostConfig)
      (hostType, count, vmScheduler)
      }
    )

    val hostTypeList = hostInfo.map( info => info._1 )
    val countList = hostInfo.map( info => info._2 )
    val vmScheduler = hostInfo.map( info => info._3 )

    getHostList(countList.zip(hostTypeList))
      .zip(vmScheduler)
      .map( hosts_vmSchedulers => {
        hosts_vmSchedulers._1.setVmScheduler(hosts_vmSchedulers._2)
        hosts_vmSchedulers._1
      } )

  }

  def getVmScehduler(vmSchedulerType : String, hostConfig : Config) : VmScheduler = {

    hostConfig.getString(vmSchedulerType) match {
      case "SpaceShared" => new VmSchedulerSpaceShared()
      case "TimeShared" => new VmSchedulerTimeShared()
      case _ => throwError(vmSchedulerType)
                null
    }

  }

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
