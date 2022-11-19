package CloudComponents

import HelperUtils.{CreateLogger, Parameters}
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.hosts.network.NetworkHost
import org.cloudbus.cloudsim.hosts.{Host, HostSimple, HostSuitability}
import org.cloudbus.cloudsim.network.switches.EdgeSwitch
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}

import scala.jdk.CollectionConverters.*
import scala.math.*

/**
 * This object contains all the necessary functions to load at runtime
 * the Host component from the configuration file
 */
object HostComponent {

  /**
   * this attribute represents the logger for this object
   */
  private val logger = CreateLogger(classOf[HostComponent.type])

  /**
   * this attribute represents the name of the component on the configuration file
   */
  private val configName = "host"

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
  private val hostsTypes = Parameters.hostsTypes

  /**
   * this method is used to load an Host component given its type name
   * in order to have a better understanding, see src/main/resources/components/host.conf
   *
   * @param hostType this parameter represents the type of the Host to create
   * @return the host of the specific type "hostType" if it exists, throws an exception
   *         if the type specified host does not exist
   */
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
    val powerModel = new PowerModelHostSimple(maxPower, staticPower)
    powerModel.setStartupDelay(startupDelay)
      .setShutDownDelay(shutdownDelay)
      .setStartupPower(startupPower)
      .setShutDownPower(shutdownPower)
    host.setPowerModel(powerModel)

    logger.info(s"$configName created")

    host
  }

  /**
   * this method is used to cretae a list of PEs of size "pes" all with mips "mips"
   * @param pes this parameter represents the number of PEs to create
   * @param mips this parameter repreents the Million Instructions Per Second of each PE created
   * @return a list of size "pes" of PEs each with mips "mips"
   */
  private def createPes(pes : Long, mips : Double) : List[Pe] = {
    (0 to pes.toInt).toList.map(_ => new PeSimple(mips))
  }

  /**
   * this method is used to load a list of Hosts given a list of pairs, where the
   * second element represents the type of the host to create, while the first element
   * represents how many hosts of that type to create
   *
   * @param hostListConfig this parameter represents a list containing all the necessary
   *                     info about hosts to create
   * @return a list of Hosts
   */
  def getHostList(hostListConfig: List[(Int, String)]): List[Host] = {

    hostListConfig.flatMap(
      config => {
        (0 until config._1).
          toList.
          map(_ => getHostConfig(config._2))
      }
    )
  }

  /**
   * this method is used to congigure at runtime the hosts network within a datacenter
   * in particular, the heuristic used is to place a switch for every 3 hosts created
   * @param simulation this parameter represents the simulation
   * @param datacenter this parameter represents the datacenter in which hosts 
   *                   to configure are located
   */
  def configureHostsNetwork(simulation : CloudSim, datacenter : NetworkDatacenter) : Unit = {

    val hostList = datacenter.getHostList[NetworkHost].asScala.toList

    val numSwithces = ceil(hostList.length.toDouble / 3.0).toInt

    // create switches
    val switchList = (0 until numSwithces)
      .map(_ => new EdgeSwitch(simulation, datacenter))

    // add switches
    switchList
      .foreach(switch => datacenter.addSwitch(switch))

    // assign switches
    hostList.indices
      .foreach(index => switchList(index % numSwithces).connectHost(hostList(index)))
  }

}
