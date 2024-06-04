package com.azure.test

import com.azure.core.http.policy.HttpLogDetailLevel
import com.azure.core.management.AzureEnvironment
import com.azure.core.management.profile.AzureProfile
import com.azure.identity.DefaultAzureCredentialBuilder
import com.azure.resourcemanager.AzureResourceManager

import scala.collection.JavaConverters.asScalaIteratorConverter

/**
 * @author ${user.name}
 */
object App {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def main(args : Array[String]) {
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

}
