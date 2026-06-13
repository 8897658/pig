# Pig 项目改进规划

## 一、阶段一：紧急修复 (1-2周)

### 1.1 异常处理体系重构 ✅ 已完成基础框架

**已创建文件**：
- `BizException.java` - 业务异常基类
- `ErrorCode.java` - 错误码接口
- `CommonErrorCode.java` - 通用错误码枚举
- `BizAssert.java` - 业务断言工具类

**待完成**：
```java
// 修改前：PayOrderServiceImpl.java
throw new RuntimeException("支付渠道未配置");

// 修改后：
BizAssert.notNull(channelConfig, CommonErrorCode.PAY_CHANNEL_NOT_CONFIG);

// 或带自定义消息
BizAssert.notNull(channelConfig, CommonErrorCode.PAY_CHANNEL_NOT_CONFIG, 
    "支付渠道[" + channelCode + "]未配置");
```

### 1.2 添加事务注解

**优先级排序**：
| 文件 | 风险等级 | 需要添加的方法 |
|-----|---------|---------------|
| `DynamicRouteServiceImpl` | 高 | update, delete |
| `SysPublicParamServiceImpl` | 中 | updateParam, removeParamByIds |
| `SysApiKeyServiceImpl` | 高 | save, update, delete |
| `GenTableColumnServiceImpl` | 中 | 批量操作 |

**示例修改**：
```java
@Service
@RequiredArgsConstructor
public class DynamicRouteServiceImpl {

    @Transactional(rollbackFor = Exception.class)
    public Boolean update(RouteDefinition routeDefinition) {
        // ...
    }
}
```

---

## 二、阶段二：功能补全 (2-4周)

### 2.1 支付模块实现

**当前状态**: 全部 TODO 占位符

**实现计划**：

```
第1周：微信支付
├── WechatPayChannel.java
│   ├── prepay() - JSAPI下单
│   ├── notify() - 回调验签
│   ├── query() - 订单查询
│   └── refund() - 申请退款
└── 集成 wechatpay-java SDK

第2周：支付宝
├── AlipayChannel.java  
│   ├── prepay() - 手机网站支付
│   ├── notify() - 异步通知
│   ├── query() - 交易查询
│   └── refund() - 统一收单退款

第3周：回调处理
├── PayNotifyController.java
├── 幂等性校验
├── 状态机校验
└── 分布式锁

第4周：测试验证
├── 单元测试
├── 沙箱环境测试
└── 对账功能
```

### 2.2 微信小程序模块实现

**当前状态**: 全部 TODO 占位符

**实现计划**：
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMiniServiceImpl implements WxMiniService {

    private final WxMaService wxMaService;
    private final SysUserService userService;

    @Override
    public UserInfo login(String jsCode) {
        // 1. 通过 jsCode 获取 openid
        WxMaJscode2SessionResult session = wxMaService.getUserService()
            .getSessionInfo(jsCode);
        
        // 2. 查询或创建用户
        SysUser user = userService.getByOpenid(session.getOpenid());
        if (user == null) {
            user = userService.createMiniUser(session.getOpenid());
        }
        
        // 3. 生成 token
        return buildUserInfo(user);
    }

    @Override
    public String getPhoneNumber(String code) {
        return wxMaService.getUserService().getPhoneNoInfo(code);
    }
}
```

---

## 三、阶段三：代码重构 (3-4周)

### 3.1 过长方法拆分

**重构示例 - SysUserServiceImpl.java (805行)**：

```java
// 重构前：单个大类
public class SysUserServiceImpl {
    // 所有方法混在一起
}

// 重构后：拆分为职责清晰的组件
public class SysUserServiceImpl {
    private final UserQueryComponent queryComponent;
    private final UserSaveComponent saveComponent;
    private final UserDeleteComponent deleteComponent;
    private final UserAuthComponent authComponent;
}

@Component
class UserSaveComponent {
    public R<UserVO> saveUser(UserDTO userDTO) {
        // 用户创建逻辑
    }
    
    public R<UserVO> updateUser(UserDTO userDTO) {
        // 用户更新逻辑
    }
}
```

### 3.2 参数校验增强

**创建全局校验组**：
```java
// 校验分组
public interface CreateGroup {}
public interface UpdateGroup {}
public interface QueryGroup {}

// DTO 使用
@Data
public class UserDTO {
    @NotNull(groups = UpdateGroup.class)
    private Long userId;
    
    @NotBlank(groups = CreateGroup.class)
    private String username;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;
    
    @Email(message = "邮箱格式错误")
    private String email;
}

// Controller 使用
@PostMapping
public R save(@Validated(CreateGroup.class) @RequestBody UserDTO userDTO) {
    return userService.saveUser(userDTO);
}
```

### 3.3 消除硬编码

**创建配置类**：
```java
@ConfigurationProperties(prefix = "pig.server")
@Data
public class ServerConfigProperties {
    
    /**
     * 内网请求白名单
     */
    private List<String> innerAllowIps = List.of("127.0.0.1");
    
    /**
     * Feign 本地调试地址
     */
    private String feignLocalUrl = "http://127.0.0.1:${server.port}";
    
    /**
     * 默认分页大小
     */
    private Integer defaultPageSize = 10;
}
```

---

## 四、阶段四：测试补充 (2-3周)

### 4.1 单元测试目标

| 模块 | 目标覆盖率 | 优先级 |
|------|----------|--------|
| pig-auth | 80% | P0 |
| pig-upms-biz | 75% | P0 |
| pig-pay-biz | 70% | P1 |
| pig-wechat-biz | 70% | P1 |
| pig-common-* | 60% | P2 |

### 4.2 集成测试计划

```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles("integration")
abstract class BaseIntegrationTest {
    
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("pig_test");
    
    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7")
        .withExposedPorts(6379);
    
    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.redis.host", redis::getHost);
    }
}

class UserIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testUserCrudFlow() {
        // 1. 创建用户
        var createResponse = restTemplate.postForEntity(
            "/admin/user", userDTO, R.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        // 2. 查询用户
        var userId = createResponse.getBody().getData().get("userId");
        var getResponse = restTemplate.getForEntity(
            "/admin/user/" + userId, R.class);
        assertThat(getResponse.getBody().getCode()).isEqualTo(0);
        
        // 3. 更新用户
        // ...
        
        // 4. 删除用户
        // ...
    }
}
```

---

## 五、阶段五：运维增强 (1-2周)

### 5.1 监控指标

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    tags:
      application: ${spring.application.name}
    export:
      prometheus:
        enabled: true
```

### 5.2 链路追踪

```xml
<!-- pom.xml -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

### 5.3 日志规范

```java
// 推荐的日志格式
log.info("[{}] 用户登录成功, userId={}, username={}", 
    TraceContext.traceId(), user.getUserId(), user.getUsername());

// 脱敏处理
log.info("[{}] 用户信息查询, phone={}", 
    TraceContext.traceId(), DesensitizeUtil.phone(user.getPhone()));
```

---

## 六、实施时间表

```
Week 1-2: 阶段一 - 异常处理 + 事务注解
Week 3-6: 阶段二 - 支付模块 + 微信模块
Week 7-10: 阶段三 - 代码重构
Week 11-13: 阶段四 - 测试补充
Week 14-15: 阶段五 - 运维增强
```

---

## 七、效果预期

| 指标 | 当前 | 目标 |
|------|------|------|
| 异常处理 | 散乱的 RuntimeException | 统一的 BizException |
| 测试覆盖率 | ~35% | ≥70% |
| 技术债务 | 25 个 TODO | <5 个 |
| 代码重复 | BeanUtils 14次 | 封装转换器 |
| 运维可观测性 | 仅 Actuator | Prometheus + Zipkin |
