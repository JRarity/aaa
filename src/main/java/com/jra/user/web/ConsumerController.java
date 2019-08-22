package com.jra.user.web;

import com.jra.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Create with Itellij IDEA.
 *
 * @Author JRarity
 * @Date 2019/8/22 12:31
 * @Version 1.0
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RibbonLoadBalancerClient client;

    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        // 根据id获取实例
        // List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        // 获取url
        // String url = "http://" + instances.get(0).getHost() + ":" + instances.get(0).getPort() + "/user/" + id;
        ServiceInstance serviceInstance = client.choose("user-service");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/" + id;
         User user = restTemplate.getForObject(url, User.class);
        return user;
    }
}
