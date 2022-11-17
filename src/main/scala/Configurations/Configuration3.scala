package Configurations

object Configuration3 {

  private val configName = "config3"

  @main def runConf3(): Unit = {

    Configuration.getConfig(configName)

  }

}
