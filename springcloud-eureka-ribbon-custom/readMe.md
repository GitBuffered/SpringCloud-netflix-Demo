Ribbon作为后端负载均衡器，比Nginx更注重的是承担并发而不是请求分发，可以直接感知后台动态变化来指定分发策略。它一共提供了7种负载均衡策略：
策略名 	策略声明 	策略描述 	实现说明
BestAvailableRule 	public class BestAvailableRule extends ClientConfigEnabledRoundRobinRule 	选择一个最小的并发请求的server 	逐个考察Server，如果Server被tripped了，则忽略，在选择其中ActiveRequestsCount最小的server
AvailabilityFilteringRule 	public class AvailabilityFilteringRule extends PredicateBasedRule 	过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端server（active connections 超过配置的阈值） 	使用一个AvailabilityPredicate来包含过滤server的逻辑，其实就就是检查status里记录的各个server的运行状态
WeightedResponseTimeRule 	public class WeightedResponseTimeRule extends RoundRobinRule 	根据响应时间分配一个weight，响应时间越长，weight越小，被选中的可能性越低。 	一个后台线程定期的从status里面读取评价响应时间，为每个server计算一个weight。Weight的计算也比较简单responsetime 减去每个server自己平均的responsetime是server的权重。当刚开始运行，没有形成status时，使用roubine策略选择server。
RetryRule 	public class RetryRule extends AbstractLoadBalancerRule 	对选定的负载均衡策略机上重试机制。 	在一个配置时间段内当选择server不成功，则一直尝试使用subRule的方式选择一个可用的server
RoundRobinRule 	public class RoundRobinRule extends AbstractLoadBalancerRule 	roundRobin方式轮询选择server 	轮询index，选择index对应位置的server
RandomRule 	public class RandomRule extends AbstractLoadBalancerRule 	随机选择一个server 	在index上随机，选择index对应位置的server
ZoneAvoidanceRule 	public class ZoneAvoidanceRule extends PredicateBasedRule 	复合判断server所在区域的性能和server的可用性选择server 	使用ZoneAvoidancePredicate和AvailabilityPredicate来判断是否选择某个server，前一个判断判定一个zone的运行性能是否可用，剔除不可用的zone（的所有server），AvailabilityPredicate用于过滤掉连接数过多的Server。



这里以随机访问策略来举个栗子：

1、ribbon配置文件添加：

service-B.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule

    1

其中service-B是我注册到Eureka的serviceID，一共起了3个示例。

2、main类注册：

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule() {
        return new RandomRule();//这里配置策略，和配置文件对应
    }

   

一定记得加第二个注册，很多文章没有。里面配具体的策略。

3、Controller：

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired  
    private LoadBalancerClient loadBalancerClient;  

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam Integer a,@RequestParam Integer b) {
        this.loadBalancerClient.choose("service-B");//随机访问策略
        return restTemplate.getForEntity("http://service-B/add?a="+a+"&b="+b, String.class).getBody();

    }

}