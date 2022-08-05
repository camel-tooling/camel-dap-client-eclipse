# Apache Camel DAP Eclipse Client UI Tests

## Structure

 - RedDeer Test framework - `com.github.camel-tooling.dap.eclipse.client.tests.ui/plugins/com.github.cameltooling.dap.reddeer`
 -  UI tests based on the above RedDeer framework for Camel Tooling - `com.github.camel-tooling.dap.eclipse.client.tests.ui/tests/com.github.cameltooling.dap.ui.tests`

## Local UI tests development

1) Download and install latest Eclipse IDE
2) Import DAP Eclipse Client Maven modules - `File > Import... > Maven > Existing Maven Projects`
    
    _Note: You need to import UI tests and DAP client:_
    
        `com.github.camel-tooling.dap.eclipse.client`
        `com.github.camel-tooling.dap.eclipse.client.tests.ui/plugins/com.github.cameltooling.dap.reddeer`
        `com.github.camel-tooling.dap.eclipse.client.tests.ui/tests/com.github.cameltooling.dap.ui.tests`

## Executing tests from command line

To execute only UI tests use

    mvn clean verify -DskipTests -Dtest=SmokeTests

#### Advanced execution of UI tests from command line (with more parameters)

The UI smoke tests are enabled by default. You can also execute other test suite by specifying

    -Dtest=PluginInfoTest

To disable UI tests you need to set

    -DskipUITests=true

It is also recommended to ignore local artifacts

    -Dtycho.localArtifacts=ignore

You may also get errors from baseline comparison, so disable it

    -Dtycho.baseline=disable
    -DskipBaselineComparison=true

You also need to specify target platform and DAP client plugin

    camel-dap-client-target-platform, com.github.camel-tooling.dap.eclipse.client

Executing only UI tests should look like as follows

    mvn clean verify -pl \
        camel-dap-client-target-platform, \
        com.github.camel-tooling.dap.eclipse.client, \
        com.github.camel-tooling.dap.eclipse.client.tests.ui/plugins/com.github.cameltooling.dap.reddeer, \
        com.github.camel-tooling.dap.eclipse.client.tests.ui/tests/com.github.cameltooling.dap.ui.tests \
        -am -DskipUITests=false \
            -Dtycho.localArtifacts=ignore \
            -Dtycho.baseline=disable \
            -DskipBaselineComparison=true \
            -DfailIfNoTests=false \
            -Dtest=SmokeTests

## Debugging tests when running from command line

Just add the following system property

    -DdebugPort=8001

and in IDE create a configuration for Remote Java Application in Run > Debug Configurations...
