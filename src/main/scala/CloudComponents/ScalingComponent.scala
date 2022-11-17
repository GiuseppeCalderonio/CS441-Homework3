package CloudComponents

import HelperUtils.{CreateLogger, Parameters}
import HelperUtils.Parameters.throwError
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.power.models.PowerModelHostSimple
import org.cloudbus.cloudsim.resources.Processor
import org.cloudsimplus.autoscaling.resources.{ResourceScaling, ResourceScalingGradual, ResourceScalingInstantaneous}
import org.cloudsimplus.autoscaling.*

/**
 * This object contains all the necessary functions to load at runtime
 * the Scaling component from the configuration file
 */
object ScalingComponent {

  /**
   * this attribute represents the logger for this object
   */
  private val logger = CreateLogger(classOf[ScalingComponent.type])

  /**
   * this attribute represents the name of the component on the configuration file
   */
  private val configName = "scaling"

  /**
   * this attribute represents the root configuration name of the configuration file
   */
  private val rootConfigName = Parameters.componentsConfig

  /**
   * this attribute represents the configuration object for this component
   */
  private val config = rootConfigName.getConfig(configName)

  /**
   * this attribute represents a list of all the possible value names that the horizontal scaling component can
   * have in the configuration file
   */
  private val horizontalScalingTypes = Parameters.horizontalScalingTypes

  /**
   * this attribute represents a list of all the possible value names that the vertical scaling component can
   * have in the configuration file
   */
  private val verticalScalingTypes = Parameters.verticalScalingTypes

  /**
   * this method is used to load an horizontal scaling component given its type name
   * in order to have a better understanding, see src/main/resources/components/scaling.conf
   * @param horizontalScalingTypeConf this attribute represents the type of horizontal scaling
   * @return the scaling component if the type exists in the configuration file, 
   *         throws an exception otherwise
   */
  def getHorizontalScalingConfig( horizontalScalingTypeConf : String) : HorizontalVmScaling = {

    val horizontalScalingTypeName = "horizontal"

    logger.info(s"Loading")

    Parameters.checkType(configName, horizontalScalingTypeConf, horizontalScalingTypes)

    val partialPath = s"$horizontalScalingTypeName.$horizontalScalingTypeConf"

    // creates horizontal scaling for vm

    val cpuUtilization = config.getDouble(s"$partialPath.utilization")
    val vmType = config.getString(s"$partialPath.type")

    val horizontalScaling = new HorizontalVmScalingSimple
    horizontalScaling.setVmSupplier(() => VmComponent.getVmConfig(vmType))
    horizontalScaling.setOverloadPredicate(vm => vm.getCpuPercentUtilization() > cpuUtilization)

    horizontalScaling


  }

  /**
   * this method is used to load a vertical scaling component given its type name
   * in order to have a better understanding, see src/main/resources/components/scaling.conf
   *
   * @param verticalScalingTypeConf this attribute represents the type of vertical scaling
   * @return the scaling component if the type exists in the configuration file, 
   *         throws an exception otherwise
   */
  def getVerticalScalingConfig( verticalScalingTypeConf : String) : VerticalVmScaling = {

    val verticalScalingTypeName = "vertical"

    Parameters.checkType(configName, verticalScalingTypeConf, verticalScalingTypes)

    val partialPath = s"$verticalScalingTypeName.$verticalScalingTypeConf"

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

  /**
   * this method checks the type of resource scaling to load, if it exists
   * throws an exception otherwise
   * @param partialPath this parameter represents the name of the configuration file
   *                    used to load this component
   * @return the resource scaling component if it exists, throws an exception otherwise
   */
  private def getVerticalScalingType(partialPath: String): ResourceScaling = {
    config.getString(s"$partialPath.type") match
      case "instantaneous" => new ResourceScalingInstantaneous
      case "gradual" => new ResourceScalingGradual
      case _ => throwError(s"$partialPath.type")
                null
  }

}
