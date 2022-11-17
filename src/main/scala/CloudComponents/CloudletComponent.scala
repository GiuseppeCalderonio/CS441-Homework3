package CloudComponents

import HelperUtils.{CreateLogger, Parameters}
import com.typesafe.config.Config
import org.cloudbus.cloudsim.cloudlets.network.{CloudletTask, NetworkCloudlet}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic

import scala.util.{Failure, Success, Try}

/**
 * This object contains all the necessary functions to load at runtime
 * the Cloudlet component from the configuration file
 */
object CloudletComponent {


  /**
   * this attribute represents the logger for this object
   */
  private val logger = CreateLogger(classOf[CloudletComponent.type])

  /**
   * this attribute represents the name of the component on the configuration file
   */
  private val configName = "cloudlet"

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
  private val cloudletTypes = Parameters.cloudletTypes

  /**
   * this method is used to load an Cloudlet component given its type name
   * in order to have a better understanding, see src/main/resources/components/cloudlet.conf
   *
   * @param cloudletType this parameter represents the type of the Cloudlet to create
   * @return the cloudlet of the specific type "cloudletType" if it exists, throws an exception
   *         if the type specified cloudlet does not exist
   */
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

    val cloudlet = new CloudletSimple(length, PEs)
    cloudlet.setUtilizationModelBw(new UtilizationModelDynamic(initialUtilizationBw))
    cloudlet.setUtilizationModelRam(new UtilizationModelDynamic(initialUtilizationRam))
    cloudlet.setUtilizationModelCpu(new UtilizationModelDynamic(initialUtilizationCpu))
    cloudlet.setSizes(sizes)

    logger.info(s"$configName created")

    cloudlet
  }

  /**
   * this method is used to load a list of Cloudlets given a list of pairs, where the
   * second element represents the type of the host to create, while the first element
   * represents how many cloudlets of that type to create
   *
   * @param cloudletListConfig this parameter represents a list containing all the necessary
   *                       info about cloudlets to create
   * @return a list of Cloudlets
   */
  def getCloudletList(cloudletListConfig : List[(Int, String)]) : List[Cloudlet] = {

    cloudletListConfig.flatMap(
      config => {
        (0 until config._1).
          toList.
          map(_ => getCloudletConfig(config._2))
      }
    )
  }

}
