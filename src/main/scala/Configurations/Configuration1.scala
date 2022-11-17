package Configurations

object Configuration1 {

  private val configName = "config1"

  @main def runConf1(): Unit = {

    Configuration.getConfig(configName)

  }


}
