# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

**Cloud mode (microservices):**
```bash
mvn clean install -T 4 -Pcloud
docker compose build && docker compose up
```

**Boot mode (monolith):**
```bash
mvn clean install -T 4 -Pboot
docker compose -f docker-compose-boot.yml build && docker compose -f docker-compose-boot.yml up
```

**Code formatting (run before commits):**
```bash
mvn spring-javaformat:apply
```

**Verify build:**
```bash
mvn verify
```

## Architecture Overview

Pig is a Spring Cloud microservices platform with OAuth2-based authentication. The codebase supports both microservices (`-Pcloud`) and monolithic (`-Pboot`) deployment modes.

### Module Structure

| Module | Purpose | Port |
|--------|---------|------|
| `pig-register` | Nacos server (service discovery + config) | 8848/9848/18080 |
| `pig-gateway` | Spring Cloud Gateway (edge routing) | 9999 |
| `pig-auth` | Spring Authorization Server (OAuth2) | 3000 |
| `pig-upms` | User/permission management (api + biz submodules) | 4000 |
| `pig-boot` | Monolith launcher (only built with `-Pboot`) | 9999 |
| `pig-common` | Shared libraries (see below) | — |
| `pig-visual` | Monitoring/codegen/quartz services | 5001/5002/5007 |

### Common Libraries (`pig-common/`)

- **pig-common-bom**: Centralized dependency version management (import as BOM)
- **pig-common-security**: OAuth2 resource server config, security utils, Feign interceptors
- **pig-common-data**: MyBatis Plus + caching extensions
- **pig-common-feign**: OpenFeign error handling and retry configuration
- **pig-common-log**: Audit logging (integration with `@SysLog` annotation)

All services use `@EnablePigResourceServer` to enable OAuth2 resource server protection with automatic security configuration.

### Service Communication

Services communicate via OpenFeign clients defined in `pig-upms-api/src/main/java/com/pig4cloud/pig/admin/api/feign/`. These are `Remote*Service` interfaces (e.g., `RemoteTokenService`, `RemoteDictService`) that other modules depend on.

## Key Patterns

### Entity Definition

Entities extend `Model<T>` from MyBatis Plus Active Record:
```java
@Data
@Schema(description = "...")  // OpenAPI documentation
@EqualsAndHashCode(callSuper = true)
public class SysXxx extends Model<SysXxx> {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // ...
}
```

### Security Context

Get current user info via `SecurityUtils.getUser()` from `pig-common-security`. The security context is propagated through Feign calls via `PigOAuthRequestInterceptor`.

### Gateway Routing

Routes are defined in `pig-gateway/src/main/resources/application.yml` (static config), not dynamic routing. Routes follow the pattern `/auth/**`, `/admin/**`, `/gen/**`, `/job/**`, `/monitor/**`.

### Configuration Management

- Nacos handles centralized configuration (imported via `spring.config.import`)
- Profile-specific configs: `application-{profile}.yml` and `{service.name}-{profile}.yml`
- Default profile: `dev` (activated in `cloud` Maven profile)
- Database init scripts in `db/` (MySQL: `pig.sql` for business, `pig_config.sql` for Nacos)

### MyBatis Plus

- Uses `mybatis-plus-join` for complex queries (JOIN support)
- Dynamic datasource via `dynamic-datasource-spring-boot4-starter` (multi-tenant/database scenarios)
- Entities use `@TableId(type = IdType.ASSIGN_ID)` for snowflake ID generation

## Development Workflow

1. **Before editing**: Run `mvn spring-javaformat:apply` to ensure code style compliance (Spring Java Format)
2. **Dependencies**: Version changes go in `pig-common-bom/pom.xml`, not individual module POMs
3. **New entity**: Place in `pig-upms-api/src/main/java/com/pig4cloud/pig/admin/api/entity/`
4. **New Feign client**: Place in `pig-upms-api/src/main/java/com/pig4cloud/pig/admin/api/feign/`
5. **New service**: Add to `pig-gateway/src/main/resources/application.yml` routing

## Database

- Default: MySQL with `lower_case_table_names=1` (case-insensitive)
- Also supports: Oracle, SQL Server, DM8, HighGo (drivers in BOM)
- Schema: `pig` database for business tables, `pig_config` for Nacos configuration

## Dependabot

Configured to auto-merge patch updates and minor updates for `dev.langchain4j:*`. Major version updates are ignored and require manual assessment. PRs target `dev` branch.

## Notes from AGENTS.md

- Think before coding: ask questions for ambiguous requirements
- Prefer simplicity: minimal code, no speculative abstractions
- Surgical changes: only touch what the task requires
- Goal-driven: convert vague instructions into verifiable goals (prefer tests)
