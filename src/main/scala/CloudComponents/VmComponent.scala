package CloudComponents

import HelperUtils.{CreateLogger, Parameters}
import CloudComponents.ScalingComponent.*
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.vms.network.NetworkVm
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}

import scala.util.Random

/**
 * This object contains all the necessary functions to load at runtime
 * the Vm component from the configuration file
 */
object VmComponent {

  /**
   * this attribute represents the logger for this object
   */
  private val logger = CreateLogger(classOf[VmComponent.type])

  /**
   * this attribute represents the name of the component on the configuration file
   */
  private val configName = "vm"

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
  private val vmTypes = Parameters.vmTypes

  /**
   * this method is used to load a Vm component given its type name
   * in order to have a better understanding, see src/main/resources/components/vm.conf
   * @param vmType this parameter represents the type of the Vm to create
   * @return the vm of the specific type "vmType" if it exists, throws an exception
   *         if the type specified vm does not exist
   */
  def getVmConfig(vmType: String): Vm = {

    logger.info(s"Creating a new $configName of type $vmType")

    Parameters.checkType(configName, vmType, vmTypes)

    val partialPath = s"$vmType"

    // creates host

    val pes = config.getInt(s"$partialPath.pes")
    val ram = config.getLong(s"$partialPath.ram")
    val bw = config.getLong(s"$partialPath.bw")
    val size = config.getLong(s"$partialPath.size")
    val mips = config.getLong(s"$partialPath.mips")

    val vm = new NetworkVm(mips, pes)
    vm.setRam(ram)
    vm.setBw(bw)
    vm.setSize(size)

    setScaling(vm, partialPath)

    logger.info(s"$configName created")

    vm
  }

  /**
   * this method is used to load a list of Vms given a list of pairs, where the
   * second element represents the type of the vm to create, while the first element
   * represents how many vms of that type to create
   * @param vmListConfig this parameter represents a list containing all the necessary
   *                     info about vms to create
   * @return a list of Vms
   */
  def getVmList(vmListConfig: List[(Int, String)]): List[Vm] = {

    vmListConfig.flatMap(
      config => {
        (0 until config._1).
          toList.
          map(_ => getVmConfig(config._2))
      }
    )
  }

  /**
   * this method is used to set the scaling type for the vm
   * @param vm this parameter represents the vm to be set
   * @param partialPath this parameter represents the relative name of the
   *                    scaling type in the configuration file
   */
  def setScaling(vm: Vm, partialPath : String) : Unit = {

    val complexity = config.getString(s"$partialPath.scalingComplexity")

    config.getString(s"$partialPath.scalingType") match {
      case "horizontal" => vm.setHorizontalScaling(getHorizontalScalingConfig(complexity))
      case "vertical" => setVerticalScaling(vm, complexity)
    }

  }

  /**
   * this method is used to handle the specific case of setting a vertical caling type
   *
   * @param vm          this parameter represents the vm to be set
   * @param complexity this parameter represents the scaling complexity to set
   *
   */
  def setVerticalScaling(vm: Vm, complexity: String) : Unit = {

    val verticalScaling = getVerticalScalingConfig(complexity)

    vm.setBwVerticalScaling(verticalScaling)
    vm.setPeVerticalScaling(verticalScaling)
    vm.setRamVerticalScaling(verticalScaling)
  }


}
