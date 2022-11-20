package Configurations

object Configuration4 {

  private val configName = "config4"

  @main def runConf4(): Unit = {

    Configuration.getConfig(configName, false)

  }

}
