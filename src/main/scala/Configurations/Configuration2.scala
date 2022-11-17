package Configurations

object Configuration2 {

  private val configName = "config2"

  @main def runConf2(): Unit = {

    Configuration.getConfig(configName)

  }

}
