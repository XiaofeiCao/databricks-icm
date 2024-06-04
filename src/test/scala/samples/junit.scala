package samples

import com.azure.core.http.policy.HttpLogDetailLevel
import com.azure.core.management.AzureEnvironment
import com.azure.core.management.profile.AzureProfile
import com.azure.identity.DefaultAzureCredentialBuilder
import com.azure.resourcemanager.AzureResourceManager
import org.junit.jupiter.api.Test

import scala.collection.JavaConverters.asScalaIteratorConverter

@Test
class AppTest {


    @Test
    def testOK() = {
      val tenantId = System.getenv("AZURE_TENANT_ID")
      val subscriptionId = System.getenv("AZURE_SUBSCRIPTION_ID")

      val azureResourceManager = AzureResourceManager.configure()
        .withLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)
        .authenticate(new DefaultAzureCredentialBuilder().build(), new AzureProfile(tenantId, subscriptionId, AzureEnvironment.AZURE))
        .withDefaultSubscription()

      val privateLinkEndpoints = azureResourceManager
        .networks()
        .manager()
        .serviceClient()
        .getPrivateEndpoints
        .listByResourceGroup("rg-xiaofei")
        .iterator()
        .asScala
        .toList

      println(privateLinkEndpoints.length)
    }

//    @Test
//    def testKO() = assertTrue(false)

}


