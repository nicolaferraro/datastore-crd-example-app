# DataStore CRD Example Application

Use this in conjunction with https://github.com/nicolaferraro/datastore-crd.

## Install

Requires Kubernetes 1.9 (Minikube).
After installing the DataStore CRD:

```
eval $(minikube docker-env)
mvn clean install
kubectl create -f datastore-crd-example-app-datastore.yml
```

## Playing

This app fails to be decommissioned 50% of the times.
Scaling up and then scaling down the controller may make it bounce until all pods are decommissioned successfully. 