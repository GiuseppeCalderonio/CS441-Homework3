package Components

import org.cloudbus.cloudsim.cloudlets.Cloudlet
import HelperUtils.CreateLogger
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.hosts.network.NetworkHost
import org.cloudbus.cloudsim.hosts.{Host, HostSimple, HostSuitability}
import org.cloudbus.cloudsim.network.switches.EdgeSwitch
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import HelperUtils.Parameters
import scala.jdk.CollectionConverters.*
import scala.math.*

object HostComponent {

  private val logger = CreateLogger(classOf[HostComponent.type])

  private val configName = "host"
  private val rootConfigName = Parameters.componentsConfig

  private val config = rootConfigName.getConfig(configName)

  private val hostsTypes = Parameters.hostsTypes

  def getHostConfig(hostType: String): Host = {

    Parameters.checkType(configName, hostType, hostsTypes)

    logger.info(s"Creating a new $configName of type $hostType")

    val partialPath = s"$hostType"

    // creates host

    // set hardware basic info

    val pes = config.getLong(s"$partialPath.pes")
    val mips = config.getDouble(s"$partialPath.mips")
    val ram = config.getLong(s"$partialPath.ram")
    val bandwidth = config.getLong(s"$partialPath.bandwidth")
    val storage = config.getLong(s"$partialPath.storage")

    // set power model

    val powerModelPartialPath = s"$partialPath.powerModel"

    val maxPower = config.getDouble(s"$powerModelPartialPath.maxPower")
    val staticPower = config.getDouble(s"$powerModelPartialPath.staticPower")
    val startupDelay = config.getDouble(s"$powerModelPartialPath.startupDelay")
    val shutdownDelay = config.getDouble(s"$powerModelPartialPath.shutdownDelay")
    val startupPower = config.getDouble(s"$powerModelPartialPath.startupPower")
    val shutdownPower = config.getDouble(s"$powerModelPartialPath.shutdownPower")

    val host = new NetworkHost(ram, bandwidth, storage, createPes(pes, mips).asJava)
    val powerModel = new PowerModelHostSimple(maxPower, staticPower);
    powerModel.setStartupDelay(startupDelay)
      .setShutDownDelay(shutdownDelay)
      .setStartupPower(startupPower)
      .setShutDownPower(shutdownPower)
    host.setPowerModel(powerModel)

    logger.info(s"$configName created")

    host
  }

  private def createPes(pes : Long, mips : Double) : List[Pe] = {
    (0 to pes.toInt).toList.map(_ => new PeSimple(mips))
  }

  def getHostList(hostListConfig: List[(Int, String)]): List[Host] = {

    hostListConfig.flatMap(
      config => {
        (0 until config._1).
          toList.
          map(_ => getHostConfig(config._2))
      }
    )
  }

  def configureHostsNetwork(simulation : CloudSim, datacenter : NetworkDatacenter) : Unit = {

    val hostList = datacenter.getHostList[NetworkHost].asScala.toList

    val numSwithces = ceil(hostList.length.toDouble / 3.0).toInt

    val switchList = (0 to numSwithces)
      .map(_ => new EdgeSwitch(simulation, datacenter))

    switchList
      .foreach(switch => datacenter.addSwitch(switch))

    hostList.indices
      .foreach(index => switchList(index % numSwithces).connectHost(hostList(index)))
  }

}
