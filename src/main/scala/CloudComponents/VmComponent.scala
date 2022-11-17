package CloudComponents

import HelperUtils.{CreateLogger, Parameters}
import CloudComponents.ScalingComponent.*
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.vms.network.NetworkVm
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}

import scala.util.Random

object VmComponent {

  private val logger = CreateLogger(classOf[VmComponent.type])

  private val configName = "vm"
  private val rootConfigName = Parameters.componentsConfig

  private val config = rootConfigName.getConfig(configName)

  private val vmTypes = Parameters.vmTypes

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

  def getVmList(vmListConfig: List[(Int, String)]): List[Vm] = {

    vmListConfig.flatMap(
      config => {
        (0 until config._1).
          toList.
          map(_ => getVmConfig(config._2))
      }
    )
  }

  def setScaling(vm: Vm, partialPath : String) : Unit = {

    val complexity = config.getString(s"$partialPath.scalingComplexity")

    config.getString(s"$partialPath.scalingType") match {
      case "horizontal" => vm.setHorizontalScaling(getHorizontalScalingConfig("horizontal", complexity))
      case "vertical" => setVerticalScaling(vm, complexity)
    }

  }

  def setVerticalScaling(vm: Vm, complexity: String) : Unit = {

    val verticalScaling = getVerticalScalingConfig("vertical", complexity)

    vm.setBwVerticalScaling(verticalScaling)
    vm.setPeVerticalScaling(verticalScaling)
    vm.setRamVerticalScaling(verticalScaling)
  }


}
