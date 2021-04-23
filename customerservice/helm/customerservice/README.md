# Order Service chart

 
## Installing the Chart

To install the chart with the release name `my-release`:

```console
$ helm install my-release order-service [-f values.yaml] [--set key\=value]
```

## Uninstalling the Chart

To uninstall/delete the `my-release` deployment:

```console
$ helm delete my-release
```

The command removes all the Kubernetes components associated with the chart and deletes the release.

## Configuration

The following table lists the configurable parameters of the Catalog Service chart and their default values.

| Parameter                              | Description                                                           | Default                                 |
| -------------------------------------- | --------------------------------------------------------------------- | --------------------------------------- |
| `service.name`                         | The name of the Spectra Service the microservice is deployed under.   | `sp-test`                               |
| `microservice.name`                    | Microservice name. Should correspond to Spectra Environment name      | `hello-spectra`                         |
| `microservice.port`                    | In-cluster port for microservice                                      | `8080`                                  |
| `userApp.enabled`                      | Bool to enable user app. Only responds to '/hello' endpoint if false  | `false`                                 |
| `userApp.initContainerImage`           | Docker url of (public) atpinit container. (Included in base repo dir) | `phx.ocir.io/demo/tools/atpinit:latest` |
| `userApp.secrets.atp.secretName`       | Name of kube secret for ATP info                                      | `hello-spectra-atp-info`                |
| `userApp.secrets.atp.adminUser`        | ATP admin user name                                                   | `ADMIN`                                 |
| `userApp.secrets.atp.adminPassword`    | ATP admin user password                                               | `""` (eventually secret URL)            |
| `userApp.secrets.atp.dbName`           | ATP instance DB name                                                  | `""`                                    |
| `userApp.secrets.atp.dbUrl`            | JDBC URL for ATP instance                                             | `""`                                    |
| `userApp.secrets.atp.serviceUser`      | Non-admin DB username for user service                                | `usersvc`                               |
| `userApp.secrets.atp.servicePassword`  | Password for non-admin DB user for user service                       | `secretPassword123`                     |
| `userApp.secrets.atp.walletSecretName` | Name of Kube secret for Wallet (see Spectra info)                     | `hello-spectra-atp-wallet`              |
| `userApp.secrets.atp.scriptSecretName` | Name of Kube secret for DB init script (see Spectra info)             | `hello-spectra-atp-script`              |
| `image`                                | Docker url of built container                                         | `"`                                     |
| `imagePullPolicy`                      | Kube image pull policy                                                | `Always`                                |
| `imagePullSecret`                      | Kube name of pull secret                                              | `hello-spectra-pull-secret`             |
| `imageCredentials.registry`            | Docker registry for image pull secret                                 | `us-phoenix-1.ocir.io`                  |
| `imageCredemtials.username`            | Docker username for image pull secret                                 | `""`                                    |
| `imageCredentials.password`            | Docker password for image pull secret                                 | `""`                                    |
| `serviceAccount.create`                | Boolean whether to create a service account.                          | `true`                                  |
| `serviceAccount.name`                  | Name of service account. Must exist if .create is false               | `hello-spectra`                         |


