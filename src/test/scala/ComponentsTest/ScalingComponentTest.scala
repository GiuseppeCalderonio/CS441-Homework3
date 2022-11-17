package ComponentsTest

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import CloudComponents.ScalingComponent.{getHorizontalScalingConfig, getVerticalScalingConfig}
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

  it should "Initialize correctly vertical scaling types" in {

    Parameters.verticalScalingTypes.foreach(componentType => getVerticalScalingConfig(componentType) mustBe a[VerticalVmScaling])

  }

}
