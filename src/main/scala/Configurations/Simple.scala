package Configurations

import Configurations.Configuration

object Simple {

  private val configName = "simple"

  @main def runConfSimple(): Unit = {

    Configuration.getConfig(configName, false)

  }

}
