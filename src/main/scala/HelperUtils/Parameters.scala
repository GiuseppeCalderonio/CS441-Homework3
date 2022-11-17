package HelperUtils

import scala.util.{Failure, Success, Try}
import com.typesafe.config.Config

import scala.collection.immutable.ListMap
import scala.jdk.CollectionConverters.*

/**
 * This module obtains configuration parameter values from application.conf and converts them
 * into appropriate scala types.
 *
 * The implementation follows the same structure of the Parameters class of the LogGeneration project
 *
 */
object Parameters {

  /**
   * this value is used for logging purposes
   */
  private val logger = CreateLogger(classOf[Parameters.type])

  /**
   * This value is used to locate the configuration name of the constants at the root of the .config file
   */
  private val constantsConfigName = "constants"

  /**
   * This value is used to locate the configuration name of the various simulations at the root of the .config file
   */
  private val configsConfigName = "configs"

  /**
   * This value is used to locate the configuration name of the various components at the root of the .config file
   */
  private val componentsConfigName = "components"

  /**
   * this value represents the object used to access the .config file
   */
  private val constantsConfig = getConfig(constantsConfigName)

  /**
   * This method is used to get the configuration given its name, it throws an exception
   * if the name is not found in the configuration file
   * @param configName this parameter represents the name of the configuration to load
   * @return the configuration if exists, throws an exception otherwise
   */
  def getConfig(configName : String): Config = {
    ObtainConfigReference(configName) match {
      case Some(value) => value.getConfig(configName)
      case None => throwError(configName)
    }
  }

  /**
   * this method notifies the front end that a configuration "errorConfig" was not
   * found and then throws a runtime exception
   * @param errorConfig this parameter represents the name of the configuration not found
   * @return
   */
  def throwError(errorConfig: String): Config = {
    logger.error(s"Config $errorConfig not found")
    throw new RuntimeException(s"Config $errorConfig not found")
  }


  /**
   * this function returns the parameter value always represented as a string
   * (because the application uses only strings) corresponding to the name "pName"
   * in the .config file if exists, otherwise the default value "defaultVal" is chosen
   *
   * @param pName      the name of the .config parameter string to get
   * @param defaultVal the default value of the parameter if it does not exists in the .config file
   * @return the value of the parameter in the .config file associated with the name "pName" if exists, "defaultVal" otherwise
   */
  private def getParam(pName: String, defaultVal: List[String]): List[String] = {

    Try(constantsConfig.getStringList(s"$pName").asScala.toList) match {
      case Success(value) => value
      case Failure(_) => logger.warn(s"No config parameter $pName is provided. Defaulting to $defaultVal")
        defaultVal
    }
  }

  /**
   * these values represent the public interface of the object Parameters
   * the description of each of them can be found in the .config file (should be located in the src/main/resources folder)
   * these values can't be changed once the jar is created
   */

  val configsConfig: Config = getConfig(configsConfigName)
  val componentsConfig: Config = getConfig(componentsConfigName)
  val cloudletTypes : List[String] = getParam("cloudletTypes", List("light", "simple", "medium", "large"))
  val datacenterTypes: List[String] = getParam("datacenterTypes", List("simple", "medium", "large"))
  val hostsTypes: List[String] = getParam("hostsTypes", List("simple", "medium", "large"))
  val horizontalScalingTypes: List[String] = getParam("horizontalScalingTypes", List("simple", "medium", "large"))
  val verticalScalingTypes: List[String] = getParam("verticalScalingTypes", List("simple", "medium", "large"))
  val vmTypes: List[String] = getParam("vmTypes", List("simple", "medium", "large"))

  /**
   * this function checks if a component (Clouldet, Datacenter, Host, Scaling, Vm) has values between
   * those allowed
   * For example, a component in general can e simple, medium and large, so the possibleConfigTypes
   * will most likely be ["simple", "medium", "large"], and the method checks if the
   * "configType" parameter belongs to one of these values, it throws an exception otherwise
   * @param componentName this parameter represents the component to check
   * @param configType this parameter represents the type of the component to check
   * @param possibleConfigTypes this parameter represents a list of all the possible cmponents
   *                            that can be instantiated
   * @return true if "configType" belongs to "possibleConfigTypes", throws an exception otherwise
   */
  def checkType(componentName : String, configType : String, possibleConfigTypes : List[String]) : Boolean = {

    if !possibleConfigTypes.contains(configType) then throwError(configType)

    logger.info(s"$componentName of type $configType selected")

    true
  }


}