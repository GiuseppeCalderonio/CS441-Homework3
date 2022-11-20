package Configurations

object Mix {

  private val configName = "mix"

  @main def runConfMix(): Unit = {

    Configuration.getConfig(configName)

  }

}
