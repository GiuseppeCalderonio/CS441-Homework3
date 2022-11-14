package Components

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import HelperUtils.Parameters
import HostComponent.*
import org.cloudbus.cloudsim.hosts.Host
import org.scalatest.matchers.must.Matchers.mustBe

class HostComponentTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Host Component Test"

  it should "Initialize correctly host types" in {

    Parameters.hostsTypes.foreach(componentType => getHostConfig(componentType) mustBe a[Host])

  }

}
