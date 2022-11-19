package ComponentsTest

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import CloudComponents.ScalingComponent.{getHorizontalScalingConfig, getVerticalScalingConfig}
import CloudComponents.VmComponent.getVmConfig
import HelperUtils.Parameters
import org.cloudsimplus.autoscaling.{HorizontalVmScaling, VerticalVmScaling}
import org.scalatest.matchers.must.Matchers.mustBe

/**
 * this test class is used to test the scaling types
 */
class ScalingComponentTest extends AnyFlatSpec with Matchers with PrivateMethodTester{

  behavior of "Scaling Component Test"

  it should "Initialize correctly horizontal scaling types" in {

    Parameters.horizontalScalingTypes.foreach(componentType => getHorizontalScalingConfig(componentType) mustBe a[HorizontalVmScaling])

  }

  it should "Throw a runtime error when the horizontal scaling component type is wrong" in {

    an[RuntimeException] should be thrownBy getHorizontalScalingConfig("wrong value")

  }

  it should "Initialize correctly vertical scaling types" in {

    Parameters.verticalScalingTypes.foreach(componentType => getVerticalScalingConfig(componentType) mustBe a[VerticalVmScaling])

  }

  it should "Throw a runtime error when the vertical scaling component type is wrong" in {

    an[RuntimeException] should be thrownBy getVerticalScalingConfig("wrong value")

  }

}
