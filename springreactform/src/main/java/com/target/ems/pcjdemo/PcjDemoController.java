package com.target.ems.pcjdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.platform.connector.Connections;
import com.target.platform.connector.config.ConfigSource;
import com.target.platform.connector.config.FileSource;
import com.target.platform.connector.discovery.Node;
import com.target.platform.connector.discovery.ServiceCatalog;
import com.target.platform.connector.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PcjDemoController {

  final ObjectMapper objectMapper;
  final ServiceCatalog serviceCatalog;
  final ApplicationContext applicationContext;

  @Autowired
  public PcjDemoController(ObjectMapper objectMapper, ServiceCatalog serviceCatalog, ApplicationContext applicationContext) {
    this.objectMapper = objectMapper;
    this.serviceCatalog = serviceCatalog;
    this.applicationContext = applicationContext;
  }

  @GetMapping("version")
  String getVersion() {
    return Connections.INSTANCE.getVersion();
  }

  @GetMapping("hostname")
  String getHostname() {
    return System.getenv("HOSTNAME");
  }

  @GetMapping("application")
  String getApplication() throws JsonProcessingException {
    return objectMapper.writeValueAsString(Connections.INSTANCE.getApplication());
  }

  @GetMapping({"service-catalog/instances-for-server-group/", "service-catalog/instances-for-server-group/{name}"})
  Set<ServiceInstance> getInstancesForServerGroup(@PathVariable(required = false) String name) {
    String serverGroup = name != null ? name : Connections.INSTANCE.getApplication().getGroup();
    return serviceCatalog.getInstancesForServerGroup(serverGroup); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/enabled-instances-for-server-group/", "service-catalog/enabled-instances-for-server-group/{name}"})
  Set<ServiceInstance> getEnabledInstancesForServerGroup(@PathVariable(required = false) String name) {
    String serverGroup = name != null ? name : Connections.INSTANCE.getApplication().getGroup();
    return serviceCatalog.getEnabledInstancesForServerGroup(serverGroup); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/healthy-instances-for-server-group/", "service-catalog/healthy-instances-for-server-group/{name}"})
  Set<ServiceInstance> getHealthyInstancesForServerGroup(@PathVariable(required = false) String name) {
    String serverGroup = name != null ? name : Connections.INSTANCE.getApplication().getGroup();
    return serviceCatalog.getHealthyInstancesForServerGroup(serverGroup); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/enabled/", "service-catalog/enabled/{name}"})
  Boolean isEnabled(@PathVariable(required = false) String name) {
    String serverGroup = name != null ? name : Connections.INSTANCE.getApplication().getGroup();
    return serviceCatalog.isEnabled(serverGroup); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/instances-for-cluster/", "service-catalog/instances-for-cluster/{name}"})
  Set<ServiceInstance> getInstancesForCluster(@PathVariable(required = false) String name) {
    String cluster = name != null ? name : Connections.INSTANCE.getApplication().getCluster();
    return serviceCatalog.getInstancesForCluster(cluster); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/enabled-instances-for-cluster/", "service-catalog/enabled-instances-for-cluster/{name}"})
  Set<ServiceInstance> getEnabledInstancesForCluster(@PathVariable(required = false) String name) {
    String cluster = name != null ? name : Connections.INSTANCE.getApplication().getCluster();
    return serviceCatalog.getEnabledInstancesForCluster(cluster); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/healthy-instances-for-cluster/", "service-catalog/healthy-instances-for-cluster/{name}"})
  Set<ServiceInstance> getHealthyInstancesForCluster(@PathVariable(required = false) String name) {
    String cluster = name != null ? name : Connections.INSTANCE.getApplication().getCluster();
    return serviceCatalog.getHealthyInstancesForCluster(cluster); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/instances-for-service/", "service-catalog/instances-for-service/{name}"})
  Set<ServiceInstance> getInstancesForService(@PathVariable(required = false) String name) {
    String defaultServiceName = Connections.INSTANCE.getApplication().getName() + "/" + Connections.INSTANCE.getApplication().getGroup();
    String serviceName = name != null ? name : defaultServiceName;
    return serviceCatalog.getInstancesForService(serviceName); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/enabled-instances-for-service/", "service-catalog/enabled-instances-for-service/{name}"})
  Set<ServiceInstance> getEnabledInstancesForService(@PathVariable(required = false) String name) {
    String defaultServiceName = Connections.INSTANCE.getApplication().getName() + "/" + Connections.INSTANCE.getApplication().getGroup();
    String serviceName = name != null ? name : defaultServiceName;
    return serviceCatalog.getEnabledInstancesForService(serviceName); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/healthy-instances-for-service/", "service-catalog/healthy-instances-for-service/{name}"})
  Set<ServiceInstance> getHealthyInstancesForService(@PathVariable(required = false) String name) {
    String defaultServiceName = Connections.INSTANCE.getApplication().getName() + "/" + Connections.INSTANCE.getApplication().getGroup();
    String serviceName = name != null ? name : defaultServiceName;
    return serviceCatalog.getHealthyInstancesForService(serviceName); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping({"service-catalog/node/", "service-catalog/node/{name}"})
  Node getNode(@PathVariable(required = false) String name) {
    String defaultNodeName = Connections.INSTANCE.getApplication().getName() + "/" + applicationContext.getEnvironment().getProperty("HOSTNAME");
    String nodeName = name != null ? name : defaultNodeName;
    return serviceCatalog.getNode(nodeName); // this is directly calling a blocking method. In a real app, this should be async.
  }

  @GetMapping("key-value/config-source/sources")
  Map<String, Boolean> getSources() {
    Map<String, Boolean> availableSources = new HashMap<>();
    availableSources.put("metatron", Connections.INSTANCE.getMetatron().isAvailable());
    availableSources.put("consul", Connections.INSTANCE.getConsul().isAvailable());
    availableSources.put("vault", Connections.INSTANCE.getVault().isAvailable());
    return availableSources;
  }

  @GetMapping("key-value/spring-boot/application-context-environment/get-property/{key}")
  Object getProperty(@PathVariable(required = true) String key) throws JsonProcessingException {
    String property = applicationContext.getEnvironment().getProperty(key);
    return mask(property); // there are other methods on environment to explore
  }

  @GetMapping("key-value/spring-boot/application-context-environment/contains-property/{key}")
  Boolean containsProperty(@PathVariable(required = true) String key) throws JsonProcessingException {
    return applicationContext.getEnvironment().containsProperty(key);
  }

  @GetMapping({"key-value/config-source/cluster/", "key-value/config-source/cluster/{key}"})
  Object getClusterKV(@PathVariable(required = false) String key) throws IOException {
    String pointer = key != null ? "/" + key : "/";
    Object value = ConfigSource.auto().forCluster().get(pointer, Object.class);
    return mask(value);
  }

  @GetMapping({"key-value/config-source/runtime/", "key-value/config-source/runtime/{fileName}", "key-value/config-source/runtime/{fileName}/{key}"})
  Object getRuntimeKV(@PathVariable(required = false) String fileName, @PathVariable(required = false) String key) throws IOException {
    String pointer = key != null ? "/" + key : "/";
    ConfigSource configSource = ConfigSource.auto();
    ConfigSource runtimeConfigSource = fileName == null ? configSource.forRuntime() : configSource.forRuntime(fileName); // default filename is runtime.yml
    Object value = runtimeConfigSource.get(pointer, Object.class);
    return mask(value);
  }

  @GetMapping({"key-value/config-source/write-file-assets/", "key-value/config-source/write-file-assets/{key}"})
  Object writeFileAssets(@PathVariable(required = false) String key) {
    return key != null ? ConfigSource.auto().writeFileAssets(key) : ConfigSource.writeFileAssets(); //default key is runtime.yml
  }

  @GetMapping({"key-value/config-source/file/", "key-value/config-source/file/{key}", "key-value/config-source/file/{key}/{encodingType}"})
  Object getFileSource(@PathVariable(required = false) String key, @PathVariable(required = false) String encodingType) {
    String pointer = key != null ? "/" + key : "/";
    String encoding = encodingType != null ? encodingType : "base64";
    FileSource.Encoding fileSourceEncoding = FileSource.Encoding.from(encoding);
    return ConfigSource.file(pointer, fileSourceEncoding);
  }

  @GetMapping({"key-value/config-source/merged-for-cluster/", "key-value/config-source/merged-for-cluster/{key}", "key-value/config-source/merged-for-cluster/{key}/{additionalKeys}"})
  Object getMergedKV(@PathVariable(required = false) String key, @PathVariable(required = false) String additionalKeys) throws IOException {
    String resolvedKey = key != null ? key : "";
    String resolvedAdditionalKeys = additionalKeys != null ? additionalKeys : "";
    Object value = ConfigSource.mergedForCluster(ConfigSource.auto(), resolvedKey, resolvedAdditionalKeys).get("/", Object.class);
    return mask(value);
  }

  private Object mask(Object input) {
    try {
      String string = objectMapper.writeValueAsString(input);
      Object object = objectMapper.readValue(string, Object.class);
      return recursiveMask(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Exception occurred during data masking process. Underlying exception squashed for security.");
    }
  }

  private Object recursiveMask(Object object) {
    if (object instanceof String) {
      return "*".repeat(((String) object).length());
    } else if (object instanceof List) {
      return ((List) object).stream().map(this::recursiveMask).collect(Collectors.toList());
    } else if (object instanceof Map) {

      Set<Map.Entry<String, Object>> entries = ((Map<String, Object>) object).entrySet();

      for (Map.Entry entry : entries) {
        entry.setValue(recursiveMask(entry.getValue()));
      }
      return object;
    } else {
      return object;
    }
  }
}