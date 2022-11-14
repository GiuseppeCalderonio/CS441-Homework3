package Components

import HelperUtils.{CreateLogger, ObtainConfigReference}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import com.typesafe.config.Config
import org.cloudbus.cloudsim.cloudlets.network.{CloudletTask, NetworkCloudlet}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import HelperUtils.Parameters

import scala.util.{Failure, Success, Try}
import scala.util.Try

object CloudletComponent {


  private val logger = CreateLogger(classOf[CloudletComponent.type])

  private val configName = "cloudlet"
  private val rootConfigName = Parameters.componentsConfig

  private val config = rootConfigName.getConfig(configName)

  private val cloudletTypes = Parameters.cloudletTypes

  def getCloudletConfig(cloudletType: String): Cloudlet = {

    Parameters.checkType(configName, cloudletType, cloudletTypes)

    logger.info(s"Creating a new $configName of type $cloudletType")

    val partialPath = s"$cloudletType"

    val initialUtilizationCpu = config.getDouble(s"$partialPath.initialUtilizationCpu")
    val initialUtilizationBw = config.getDouble(s"$partialPath.initialUtilizationBw")
    val initialUtilizationRam = config.getDouble(s"$partialPath.initialUtilizationRam")
    val length = config.getLong(s"$partialPath.length")
    val PEs = config.getInt(s"$partialPath.PEs")
    val sizes = config.getLong(s"$partialPath.sizes")

    val cloudlet = new NetworkCloudlet(length, PEs)
    cloudlet.setUtilizationModelBw(new UtilizationModelDynamic(initialUtilizationBw))
    cloudlet.setUtilizationModelRam(new UtilizationModelDynamic(initialUtilizationRam))
    cloudlet.setUtilizationModelCpu(new UtilizationModelDynamic(initialUtilizationCpu))
    cloudlet.setSizes(sizes)

    logger.info(s"$configName created")

    cloudlet
  }

  def getCloudletList(cloudletListConfig : List[(Int, String)]) : List[Cloudlet] = {

    cloudletListConfig.flatMap(
      config => {
        (0 to config._1).
          toList.
          map(_ => getCloudletConfig(config._2))
      }
    )
  }

}
