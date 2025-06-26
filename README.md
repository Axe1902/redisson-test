Основные настройки кеширования находятся в классе **CacheUtil**.
В нем два метода: один для настройки и создания **RedissonClient**, второй для конфигурирования **CacheManager**. 
В **CacheManager** мы можем задавать следующие параметры:  
  - ttl;
  - maxIdleTime;
  - maxSize.

Более детально про механизм работы менеджера рассписано в оф документации: https://redisson.pro/docs/cache-api-implementations/#yaml-config-format

При создании RedissonClient необходимо так же задать или загрузить конфиг, который будет не только определять режим работы клиента, но и детально его конфигурировать.
В бесплатной версии существует 5 режимов работы: SingleMode, MasterSlaveMode, SentinalMode, ClusterMode, ReplicatedMode.
Параметры можно задавать как в коде, так и через json или yaml файлы.  
Более подробно тут -> https://redisson.pro/docs/configuration/  

Вот ещё неплохой мини гайд -> https://www.baeldung.com/redis-redisson#bd-overview

**Пример такого файла для SingleNode:**  
```
singleServerConfig:  
  idleConnectionTimeout: 10000  
  connectTimeout: 10000  
  timeout: 3000  
  retryAttempts: 4  
  retryDelay: !<org.redisson.config.EqualJitterDelay> {baseDelay: PT1S, maxDelay: PT2S}  
  reconnectionDelay: !<org.redisson.config.EqualJitterDelay> {baseDelay: PT0.1S, maxDelay: PT10S}  
  password: null  
  subscriptionsPerConnection: 5  
  clientName: null  
  address: "redis://127.0.0.1:6379"  
  subscriptionConnectionMinimumIdleSize: 1  
  subscriptionConnectionPoolSize: 50  
  connectionMinimumIdleSize: 24  
  connectionPoolSize: 64  
  database: 0  
  dnsMonitoringInterval: 5000  
threads: 16  
nettyThreads: 32  
codec: !<org.redisson.codec.Kryo5Codec> {}  
transportMode: "NIO"
```

**Что касательно применения кеширования в приложении.**  
Для это на методах сервиса, в которых выполняеются запросы к репозиториям, ставятся аннтотации **@Cacheable**.
Таких аннтотаций 5, каждая нужна для своей опперации:  
  - **@Cacheable**. Помечает метод, результат которого должен кэшироваться. Если метод вызывается с теми же параметрами, результат берется из кэша, а не вычисляется заново;
  - **@CachePut**. Всегда выполняет метод и обновляет кэш результатом этого выполнения. Используется, когда нужно явно обновить кэш независимо от наличия значения в нем;
  - **@CacheEvict**. Удаляет значения из кэша. Применяется, когда нужно сбросить кэш при изменении данных;
  - **@Caching**. Позволяет группировать несколько аннотаций кэширования (например, несколько @CacheEvict или комбинацию @Cacheable и @CachePut) для одного метода;
  - **@CacheConfig**. Класс-уровневая аннотация для задания общих параметров кэширования (например, имя кэша, генератор ключей и т.д.) для всех методов класса.

В параметрах к аннтотациям необходимо указать имя того кеша, который мы настраивали в менеджере, и ключ, по которому потом redisson его будет искать.   
Подробно тут рассписывать все параметры не буду, просто прикреплю ссылку на оф документацию: https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html  
