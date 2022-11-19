package ComponentsTest

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import CloudComponents.HostComponent.*
import HelperUtils.Parameters
import org.cloudbus.cloudsim.hosts.Host
import org.scalatest.matchers.must.Matchers.mustBe

/**
 * this test class is used to test the host
 */
class HostComponentTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Host Component Test"

  it should "Initialize correctly host types" in {

    Parameters.hostsTypes.foreach(componentType => getHostConfig(componentType) mustBe a[Host])

  }

  it should "Throw a runtime error when the host component type is wrong" in {

    an[RuntimeException] should be thrownBy getHostConfig("wrong value")

  }

}
