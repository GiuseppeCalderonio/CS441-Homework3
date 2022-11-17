package CloudComponents

import HelperUtils.{CreateLogger, Parameters}
import HelperUtils.Parameters.throwError
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple
import org.cloudbus.cloudsim.resources.Processor
import org.cloudsimplus.autoscaling.resources.{ResourceScaling, ResourceScalingGradual, ResourceScalingInstantaneous}
import org.cloudsimplus.autoscaling.*

object ScalingComponent {

  private val logger = CreateLogger(classOf[ScalingComponent.type])

  private val configName = "scaling"
  private val rootConfigName = Parameters.componentsConfig

  private val config = rootConfigName.getConfig(configName)

  private val horizontalScalingTypes = Parameters.horizontalScalingTypes
  private val verticalScalingTypes = Parameters.verticalScalingTypes

  def getScalingConfig(scalingType: String, scalingComplexity : String): VmScaling = {

    scalingType match {
      case "horizontal" => getHorizontalScalingConfig(scalingType, scalingComplexity)
      case "vertical" => getVerticalScalingConfig(scalingType, scalingComplexity)
      case _ =>
        Parameters.throwError(scalingType)
        null
    }
  }

  def getHorizontalScalingConfig(horizontalScalingTypeConf: String, horizontalScalingComplexity : String) : HorizontalVmScaling = {

    logger.info(s"Loading")

    Parameters.checkType(configName, horizontalScalingComplexity, horizontalScalingTypes)

    val partialPath = s"$horizontalScalingTypeConf.$horizontalScalingComplexity"

    // creates horizontal scaling for vm

    val cpuUtilization = config.getDouble(s"$partialPath.utilization")
    val vmType = config.getString(s"$partialPath.type")

    val horizontalScaling = new HorizontalVmScalingSimple
    horizontalScaling.setVmSupplier(() => VmComponent.getVmConfig(vmType))
    horizontalScaling.setOverloadPredicate(vm => vm.getCpuPercentUtilization() > cpuUtilization)

    horizontalScaling


  }

  def getVerticalScalingConfig(verticalScalingTypeConf: String, verticalScalingComplexity : String) : VerticalVmScaling = {

    Parameters.checkType(configName, verticalScalingComplexity, verticalScalingTypes)

    val partialPath = s"$verticalScalingTypeConf.$verticalScalingComplexity"

    // creates vertical scaling for vm

    val scalingFactor = config.getDouble(s"$partialPath.scalingFactor")
    val verticalScalingType = getVerticalScalingType(partialPath)
    val lowerTreshold = config.getDouble(s"$partialPath.lowerTreshold")
    val upperTreshod = config.getDouble(s"$partialPath.upperTreshold")

    val verticalScaling = new VerticalVmScalingSimple(classOf[Processor], scalingFactor)

    verticalScaling.setResourceScaling(verticalScalingType)

    verticalScaling.setLowerThresholdFunction(_ => lowerTreshold)
    verticalScaling.setUpperThresholdFunction(_ => upperTreshod)

    verticalScaling
  }

  private def getVerticalScalingType(partialPath: String): ResourceScaling = {
    config.getString(s"$partialPath.type") match
      case "instantaneous" => new ResourceScalingInstantaneous
      case "gradual" => new ResourceScalingGradual
      case _ => throwError(s"$partialPath.type")
                null
  }

}
