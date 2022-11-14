package Configurations

object Configuration1 {

  private val configName = "config1"

  @main def runConf(): Unit = {

    Configuration.getConfig(configName)

  }


}
