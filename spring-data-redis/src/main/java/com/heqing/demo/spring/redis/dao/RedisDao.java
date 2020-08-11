package com.heqing.demo.spring.redis.dao;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 参考文件 https://blog.csdn.net/weixin_40192296/article/details/101064347
 */
public interface RedisDao {

    /**
     * 将字符串值 value 关联到 key
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。<br/>
     * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     * @param key 主键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 将字符串值 value 关联到 key 。<br/>
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。<br/>
     * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     * @param key 主键
     * @param value 值
     * @param timeout 时间
     * @param timeUnit 时间单位
     * @return 设置成功时返回 true, 失败返回false 。
     */
    void set(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 返回 key 所关联的字符串值。<br/>
     * 如果 key 不存在那么返回特殊值 nil 。<br/>
     * @param key 主键
     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值
     */
    Object get(String key);

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。<br/>
     * @param key 主键
     * @param value 新值
     * @return 返回给定 key 的旧值。当 key 没有旧值时，也即是， key 不存在时，返回 nil 。
     */
    Object getSet(String key, String value);

    /**
     * 删除给定的一个 key 。不存在的 key 会被忽略。
     * @param key 需要删除的主键名
     * @return 是否成功删除，如果key不存在返回false
     */
    Boolean delete(String key);

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除
     * @param key 主键名
     * @param timeout 过期时间（
     * @param timeUnit 时间单位
     * @return 若 key 存在返回 true ，否则返回 false 。
     */
    Boolean expire(String key, long timeout, TimeUnit timeUnit);

    /**
     * 通过日期设置生存时间
     * @param key 主键名
     * @param date 日期
     * @return 设置成功返回 true 。当 key 不存在或者不能为 key 设置过期时间时返回 false 。
     */
    Boolean expireAt(String key, Date date);

    /**
     * 用于移除给定 key 的过期时间，使得 key 永不过期
     * @param key 主键名
     * @return 当过期时间移除成功时，返回 true 。如果 key 不存在或 key 没有设置过期时间，返回 false 。
     */
    Boolean persist(String key);

    /**
     * 序列化给定 key ，并返回被序列化的值
     * @param key 主键名
     * @return 如果 key 不存在，那么返回 null 。否则，返回序列化之后的值。
     */
    byte[] dump(String key);

    /**
     * 判断key是否存在
     * @param key 主键名
     * @return 是否存在
     */
    Boolean countExistingKeys(String key);

    /**
     * 模糊查询功能，符合正则 pattern 的 key
     * KEYS 的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，如果你需要从一个数据集中查找特定的 key ，你最好还是用 Redis 的集合结构(set)来代替。
     * <ul>
     *     <li>KEYS * 匹配数据库中所有 key </li>
     *     <li>KEYS h?llo 匹配 hello ， hallo 和 hxllo 等</li>
     *     <li>KEYS h*llo 匹配 hllo 和 heeeeello 等</li>
     *     <li>KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo </li>
     * </ul>
     * @param pattern 条件
     * @return 设符合给定模式的 key 列表 (Array)。
     */
    Set<String> keys(String pattern);

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中。<br/>
     * 如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果。<br/>
     * 因此，也可以利用这一特性，将 MOVE 当作锁(locking)原语(primitive)。
     * @param key 主键名
     * @param dbIndex 数据库下标
     * @return 当过期时间移除成功时，返回 true 。如果 key 不存在或 key 没有设置过期时间，返回 false 。
     */
    Boolean move(String key, int dbIndex);

    /**
     * 从当前数据库中随机返回(不删除)一个 key 。
     * @return 当数据库不为空时，返回一个 key 。 当数据库为空时，返回 nil 。
     */
    Object randomKey();

    /**
     * 将 key 改名为 newkey 。 当 newkey 已经存在时， RENAME 命令将覆盖旧值。
     * @param oldkey 旧主键名
     * @param newkey 新主键名
     */
    void rename(String oldkey, String newkey);

    /**
     * 返回 key 所储存的值的类型
     * @param key 旧主键名
     * @return 数据类型
     */
    DataType type(String key);

    /**
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。<br/>
     * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
     * @param key 主键名
     * @param value 值
     * @return 追加 value 之后， key 中字符串的长度。
     */
    int append(String key, String value);

    /**
     * 将 key 中储存的数字值减一。<br/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。<br/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。<br/>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。<br/>
     * @param key 主键
     * @return 执行 DECR 命令之后 key 的值。
     */
    Long decrement(String key);

    /**
     * 将 key 所储存的值减去减量 decrement 。<br/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。<br/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。<br/>
     * @param key 主键
     * @param decrement 减量
     * @return 减去 decrement 之后， key 的值。
     */
    Long decrement(String key, long decrement);

    /**
     * 将 key 中储存的数字值增一。<br/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。<br/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。<br/>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。<br/>
     * @param key 主键
     * @return 执行 INCR 命令之后 key 的值。
     */
    Long increment(String key);

    /**
     * 将 key 所储存的值加上增量 increment 。<br/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。<br/>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。<br/>
     * @param key 主键
     * @param increment 增量
     * @return 加上 increment 之后， key 的值。
     */
    Long increment(String key, long increment);

    /**
     * 为 key 中所储存的值加上浮点数增量 increment <br/>
     * 如果 key 不存在，那么 INCRBYFLOAT 会先将 key 的值设为 0 ，再执行加法操作。<br/>
     * 如果命令执行成功，那么 key 的值会被更新为（执行加法之后的）新值，并且新值会以字符串的形式返回给调用者。<br/>
     * 无论是 key 的值，还是增量 increment ，都可以使用像 2.0e7 、 3e5 、 90e-2 那样的指数符号(exponential notation)来表示，
     * 但是，执行 INCRBYFLOAT 命令之后的值总是以同样的形式储存，也即是，它们总是由一个数字，一个（可选的）小数点和一个任意位的小数部分组成
     * （比如 3.14 、 69.768 ，诸如此类)，小数部分尾随的 0 会被移除，如果有需要的话，还会将浮点数改为整数（比如 3.0 会被保存成 3 ）。<br/>
     * 除此之外，无论加法计算所得的浮点数的实际精度有多长， INCRBYFLOAT 的计算结果也最多只能表示小数点的后十七位。
     * @param key 主键
     * @param increment 增量
     * @return 执行命令之后 key 的值。
     */
    Double increment(String key, double increment);

    /**
     * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。<br/>
     * @param key 主键
     * @param startOffset 开始坐标
     * @param endOffset 结束坐标
     * @return 截取得出的子字符串。
     */
    String range(String key, long startOffset, long endOffset);

    /**
     * 将一个 插入到列表 key 的表头。<br/>
     * @param key 主键
     * @param value 一个
     * @return 列表的长度。
     */
    Long lLeftPush(String key, Object value);

    /**
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。<br/>
     * @param key 主键
     * @param values 一个或多个值
     * @return 列表的长度。
     */
    Long lLeftPushAll(String key, Object... values);

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。<br/>
     * @param key 主键
     * @param value 一个
     * @return 列表的长度。
     */
    Long lRightPush(String key, Object value);

    /**
     * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。<br/>
     * @param key 主键
     * @param values 一个或多个值
     * @return 列表的长度。
     */
    Long lRightPushAll(String key, Object... values);

    /**
     * 返回列表 key 中，下标为 index 的元素。<br/>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br/>
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。<br/>
     * @param key 主键
     * @param index 下标（从0开始）
     * @return 列表中下标为 index 的元素。<br/>
     *         如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil 。
     */
    Object lIndex(String key, long index);

    /**
     * 获取存储在绑定键处的列表大小
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 。<br/>
     * 如果 key 不是列表类型，返回一个错误。
     * @param key 主键
     * @return 列表 key 的长度。
     */
    Long lSize(String key);

    /**
     * 删除并返回存储在绑定键处的列表中的第一个元素
     * @param key 主键名
     * @return 列表中的第一个元素
     */
    Object lLeftPop(String key);

    /**
     * BLPOP 是列表的阻塞式(blocking)弹出原语。<br/>
     * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。<br/>
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
     * @param key 多个主键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 如果列表为空，返回一个 bull
     */
    Object lLeftPop(String key, long timeout, TimeUnit unit);

    /**
     * BRPOP 是列表的阻塞式(blocking)弹出原语。<br/>
     * 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。<br/>
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的尾部元素。
     * @param key 主键名
     * @return 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。<br/>
     *         反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
     */
    Object lRightPop(String key);

    /**
     * BRPOP 是列表的阻塞式(blocking)弹出原语。<br/>
     * 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。<br/>
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的尾部元素。
     * @param key 多个主键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。<br/>
     *         反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
     */
    Object lRightPop(String key, long timeout, TimeUnit unit);

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。<br/>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br/>
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * @param key 主键
     * @param start 开始下标
     * @param end 结束下标
     * @return 包含指定区间内的元素的列表。
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。<br/>
     * count 的值可以是以下几种：
     *  <ul>
     *      <li>count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。</li>
     *      <li>count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。</li>
     *      <li>count = 0 : 移除表中所有与 value 相等的值。</li>
     *  </ul>
     * @param key 主键
     * @param count 控制方向
     * @param value 值
     * @return 被移除元素的数量。因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
     */
    Long lRemove(String key, long count, Object value);

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value 。<br/>
     * @param key 主键
     * @param index 下标
     * @param value 值
     * @return 被移除元素的数量。因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
     */
    void lSet(String key, long index, Object value);

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。<br/>
     * 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。<br/>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br/>
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。<br/>
     * @param key 主键
     * @param start 开始下标
     * @param end 结束下标
     * @return 成功返回true，失败返回false;
     */
    void lTrim(String key, long start, long end);

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。<br/>
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。<br/>
     * @param key 主键名
     * @param members 需要添加的元素
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
     */
    Long sAdd(String key, Object... members);

    /**
     * 返回集合 key 中的所有成员。 不存在的 key 被视为空集合。
     * @param key 主键名
     * @return 集合中的所有成员。
     */
    Set<Object> sMembers(String key);

    /**
     * 判断 member 元素是否集合 key 的成员。
     * @param key 主键名
     * @param member 元素名
     * @return 如果 member 元素是集合的成员，返回 true。如果 member 元素不是集合的成员，或 key 不存在，返回 false 。
     */
    Boolean sIsMember(String key, Object member);

    /**
     * 将 member 元素从 source 集合移动到 destination 集合。<br/>
     * 如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0 。
     * 否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。<br/>
     * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。<br/>
     * @param srckey 源集合
     * @param dstkey 目标集合
     * @param member 元素名
     * @return 如果 member 元素被成功移除，返回 true 。<br/>
     *         如果 member 元素不是 source 集合的成员，并且没有任何操作对 destination 集合执行，那么返回false。
     */
    Boolean sMove(String srckey, String dstkey, Object member);

    /**
     * 返回集合 key 的基数(集合中元素的数量)。
     * @param key 主键名
     * @return 集合的基数。当 key 不存在时，返回 0 。
     */
    Long sSize(String key);

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。<br/>
     * 当 key 不是集合类型，返回一个错误。
     * @param key 主键名
     * @param members 元素名
     * @return 被成功移除的元素的数量，不包括被忽略的元素。
     */
    Long sRemove(String key, Object... members);

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。<br/>
     * 不存在的 key 被视为空集。
     * @param compares 多个主键名
     * @return 一个包含差集成员的列表。
     */
    Set<Object> sDiff(List<String> compares);

    /**
     * 这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。<br/>
     * destination 可以是 key 本身。如果 destination 集合已经存在，则将其覆盖。
     * @param compares 多个主键名
     * @param destination 保存的key
     * @return 结果集中的元素数量。
     */
    void sDiffAndStore(List<String> compares, String destination);

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的交集。<br/>
     * 不存在的 key 被视为空集。<br/>
     * 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
     * @param compares 比较的key
     * @return 交集成员的列表。
     */
    Set<Object> sIntersect(List<String> compares);

    /**
     * 这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。<br/>
     * 如果 destination 集合已经存在，则将其覆盖。<br/>
     * destination 可以是 key 本身。
     * @param compares 比较的key
     * @param destination 保存的key
     * @return 结果集中的元素数量。
     */
    void sIntersectAndStore(List<String> compares, String destination);

    /**
     * 返回一个集合的全部成员，该集合是所有给定集合的并集。<br/>
     * 不存在的 key 被视为空集。
     * @param compares 多个主键名
     * @return 并集成员的列表。
     */
    Set<Object> sUnion(List<String> compares);

    /**
     * 这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。<br/>
     * destination 可以是 key 本身。如果 destination 已经存在，则将其覆盖。
     * @param compares 多个主键名
     * @param destination 保存的key
     * @return 结果集中的元素数量。
     */
    void sUnionStore(List<String> compares, String destination);


    /**
     * 移除并返回集合中的一个随机元素。<br/>
     * 如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 SRANDMEMBER 命令。
     * @param key 主键名
     * @return 被移除的随机元素。当 key 不存在或 key 是空集时，返回 nil 。
     */
    Object sPop(String key);

    /**
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。<br/>
     * 该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，而 SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动。
     * @param key 主键名
     * @return 只提供 key 参数时，返回一个元素；如果集合为空，返回 nil 。<br/>
     *         如果提供了 count 参数，那么返回一个数组；如果集合为空，返回空数组。
     */
    Object sRandember(String key);

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。<br/>
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。<br/>
     * score 值可以是整数值或双精度浮点数。<br/>
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。<br/>
     * @param key 主键
     * @param score 分值
     * @param member 成员
     * @return 返回的每个元素都是一个有序集合元素，一个有序集合元素由一个成员（member）和一个分值（score）组成。
     */
    Boolean zAdd(String key, Object member, double score);

    /**
     * 返回有序集 key 的数量。
     * @param key 主键
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。当 key 不存在时，返回 0 。
     */
    Long zCard(String key);

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     * @param key 主键
     * @param min 最小分数
     * @param max 最大分数
     * @return score 值在 min 和 max 之间的成员的数量。
     */
    Long zCount(String key, double min, double max);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 。<br/>
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。<br/>
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。<br/>
     * 当 key 不是有序集类型时，返回一个错误。<br/>
     * score 值可以是整数值或双精度浮点数。
     * @param key 主键
     * @param score 分数
     * @param member 成员
     * @return 增加后的分数值
     */
    Double zIncrby(String key, Object member, double score);

    /**
     * 返回有序集 key 中，指定区间内的成员。其中成员的位置按 score 值递增(从小到大)来排序。<br/>
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。<br/>
     * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 ZREVRANGE 命令。<br/>
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。<br/>
     * 超出范围的下标并不会引起错误。
     * @param key 主键
     * @param start 开始下标
     * @param end 结束下标
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    Set<Object> zRange(String key, long start, long end);

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。<br/>
     * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。<br/>
     * @param key 主键
     * @param min 最小分数
     * @param max 最大分数
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    Set<Object> zRangeByScore(String key, double min, double max);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。<br/>
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。<br/>
     * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名。
     * @param key 主键
     * @param member 成员
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。<br/>
     *         如果 member 不是有序集 key 的成员，返回 nil 。
     */
    Long zRank(String key, Object member);

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。<br/>
     * 当 key 存在但不是有序集类型时，返回一个错误。
     * @param key 主键
     * @param members 多个成员
     * @return 被成功移除的成员的数量，不包括被忽略的成员。
     */
    Long zRemove(String key, Object... members);

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。<br/>
     * 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内。<br/>
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     * @param key 主键
     * @param start 开始下标
     * @param end 结束下标
     * @return 被成功移除的成员的数量。
     */
    Long zRemoveRange(String key, long start, long end);

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。<br/>
     * @param key 主键
     * @param start 开始分数值
     * @param end 结束分数值
     * @return 被成功移除的成员的数量。
     */
    Long zRemoveRangeByScore(String key, double start, double end);

    /**
     * 返回有序集 key 中，指定区间内的成员。<br/>
     * 其中成员的位置按 score 值递减(从大到小)来排列。具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。<br/>
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
     * @param key 主键
     * @param start 开始分数值
     * @param end 结束分数值
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    Set<Object> zReverseRange(String key, long start, long end);

    /**
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。<br/>
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列。<br/>
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGEBYSCORE 命令的其他方面和 ZRANGEBYSCORE 命令一样。
     * @param key 主键
     * @param max 最大分数值
     * @param min 最小分数值
     * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    Set<Object> zRevrangeByScore(String key, double max, double min);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。<br/>
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。<br/>
     * @param key 主键
     * @param member 成员
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。
     *         如果 member 不是有序集 key 的成员，返回 nil 。
     */
    Long zReverseRank(String key, Object member);

    /**
     * 返回有序集 key 中，成员 member 的 score 值。<br/>
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     * @param key 主键
     * @param member 成员
     * @returnmember 成员的 score 值，以字符串形式表示。
     */
    Double zScore(String key, Object member);

    /**
     * 计算给定的一个或多个有序集的并集，并将该并集(结果集)储存到 destination
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。
     * @param compares 一个或多个有序集
     * @param destination 目标集合
     * @returnmember 保存到 dstkey 的结果集的基数。
     */
    Long zUunionAndStore(List<String> compares, String destination);

    /**
     * 计算给定的一个或多个有序集的交集，并将该交集(结果集)储存到 destination 。<br/>
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之和。
     * @param compares 一个或多个有序集
     * @param destination 目标集合
     * @returnmember 保存到 dstkey 的结果集的基数。
     */
    Long zIntersectAndStore(List<String> compares, String destination);

    /**
     * 对于一个所有成员的分值都相同的有序集合键 key 来说， 这个命令会返回该集合中， 成员介于 min 和 max 范围内的元素数量。<br/>
     * 这个命令的 min 参数和 max 参数的意义和 ZRANGEBYLEX 命令的 min 参数和 max 参数的意义一样。
     * @param key 主键
     * @param min 成员名
     * @param max 成员名
     * @return 指定范围内的元素数量。
     */
    Long zLexCount(String key, double min, double max);

    /**
     * 将哈希表 hash 中设置为 value 。<br/>
     * @param key 主键名
     * @param value 值
     * @return 当 HSET 命令在哈希表中新创建 field 域并成功为它设置值时， 命令返回 1 ； 如果域 field 已经存在于哈希表， 并且 HSET 命令成功使用新值覆盖了它的旧值， 那么命令返回 0 。
     */
    void hPutAll(String key, Map<String, Object> value);

    /**
     * 将哈希表 hash 中域 field 的值设置为 value 。<br/>
     * 如果给定的哈希表并不存在， 那么一个新的哈希表将被创建并执行 HSET 操作。<br/>
     * 如果域 field 已经存在于哈希表中， 那么它的旧值将被新值 value 覆盖。<br/>
     * @param key 主键名
     * @param field 域名
     * @param value 值
     * @return 当 HSET 命令在哈希表中新创建 field 域并成功为它设置值时， 命令返回 1 ； 如果域 field 已经存在于哈希表， 并且 HSET 命令成功使用新值覆盖了它的旧值， 那么命令返回 0 。
     */
    void hPut(String key, Object field, Object value);

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     * @param key 主键名
     * @param member 多个域名
     * @return 被成功移除的域的数量，不包括被忽略的域。
     */
    Long hDelete(String key, Object... member);

    /**
     * 查看哈希表 key 中，给定域 field 是否存在。
     * @param key 主键名
     * @param member 域名
     * @return 如果哈希表含有给定域，返回 true, 如果哈希表不含有给定域，或 key 不存在，返回 false 。
     */
    Boolean hasKey(String key, Object member);

    /**
     * 返回哈希表 key 中给定域 field 的值。
     * @param key 主键名
     * @param member 域名
     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
     */
    Object hGet(String key, String member);

    /**
     * 返回哈希表 key 中，所有的域和值。<br/>
     * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
     * @param key 主键名
     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    Map<Object, Object> hGet(String key);

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment 。<br/>
     * 增量也可以为负数，相当于对给定域进行减法操作。<br/>
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。<br/>
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。<br/>
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。<br/>
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
     * @param key 主键名
     * @param field 域名
     * @param increment 增量
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值。
     */
    Long hIncrement(String key, String field, long increment);

    /**
     * 为哈希表 key 中的域 field 加上浮点数增量 increment 。<br/>
     * 如果哈希表中没有域 field ，那么 HINCRBYFLOAT 会先将域 field 的值设为 0 ，然后再执行加法操作。<br/>
     * 如果键 key 不存在，那么 HINCRBYFLOAT 会先创建一个哈希表，再创建域 field ，最后再执行加法操作。<br/>
     * 当以下任意一个条件发生时，返回一个错误：
     *  <ul>
     *      <li>域 field 的值不是字符串类型(因为 redis 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型）</li>
     *      <li>域 field 当前的值或给定的增量 increment 不能解释(parse)为双精度浮点数(double precision floating point number)</li>
     *  </ul>
     * @param key 主键名
     * @param field 域名
     * @param increment 增量
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值。
     */
    Double hIncrement(String key, String field, double increment);

    /**
     * 返回哈希表 key 中的所有域。
     * @param key 主键名
     * @return 一个包含哈希表中所有域的表。当 key 不存在时，返回一个空表。
     */
    Set<Object> hKeys(String key);

    /**
     * 返回哈希表 key 中所有域的值。
     * @param key 主键名
     * @return 一个包含哈希表中所有值的表。当 key 不存在时，返回一个空表。
     */
    List<Object> hVals(String key);

    /**
     * 返回哈希表 key 中域的数量。
     * @param key 主键名
     * @return 哈希表中域的数量。当 key 不存在时，返回 0 。
     */
    Long hLen(String key);

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。<br/>
     * 如果给定的域不存在于哈希表，那么返回一个 nil 值。<br/>
     * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
     * @param key 主键名
     * @param field 多个域名
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
     */
    List<Object> hMget(String key, List<Object> field);

    /**
     * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。<br/>
     * 若域 field 已经存在，该操作无效。<br/>
     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
     * @param key 主键名
     * @param field 域名
     * @param value 值
     * @return 设置成功，返回 true 。如果给定域已经存在且没有操作被执行，返回 false 。
     */
    Boolean hSetNx(String key, Object field, Object value);

    /**
     * 将给定的空间元素（纬度、经度、名字）添加到指定的键里面。 这些数据会以有序集合的形式被储存在键里面，
     * 从而使得像 GEORADIUS 和 GEORADIUSBYMEMBER 这样的命令可以在之后通过位置查询取得这些元素。<br/>
     * GEOADD 命令以标准的 x,y 格式接受参数， 所以用户必须先输入经度， 然后再输入纬度。 GEOADD
     * 能够记录的坐标是有限的： 非常接近两极的区域是无法被索引的。 精确的坐标限制由 EPSG:900913 / EPSG:3785 / OSGEO:41001 等坐标系统定义，
     * 具体如下：<br/>
     * <ul>
     *     <li>有效的经度介于 -180 度至 180 度之间。</li>
     *     <li>有效的纬度介于 -85.05112878 度至 85.05112878 度之间。</li>
     * </ul>
     * 当用户尝试输入一个超出范围的经度或者纬度时， GEOADD 命令将返回一个错误。
     * @param key 主键名
     * @param point 经维度
     * @param member 元素
     * @return 新添加到键里面的空间元素数量， 不包括那些已经存在但是被更新的元素。
     */
    Long geoAdd(String key, Point point, Object member);

    /**
     * 将给定的空间元素（纬度、经度、名字）添加到指定的键里面。 这些数据会以有序集合的形式被储存在键里面，
     * 从而使得像 GEORADIUS 和 GEORADIUSBYMEMBER 这样的命令可以在之后通过位置查询取得这些元素。<br/>
     * GEOADD 命令以标准的 x,y 格式接受参数， 所以用户必须先输入经度， 然后再输入纬度。 GEOADD
     * 能够记录的坐标是有限的： 非常接近两极的区域是无法被索引的。 精确的坐标限制由 EPSG:900913 / EPSG:3785 / OSGEO:41001 等坐标系统定义，
     * 具体如下：<br/>
     * <ul>
     *     <li>有效的经度介于 -180 度至 180 度之间。</li>
     *     <li>有效的纬度介于 -85.05112878 度至 85.05112878 度之间。</li>
     * </ul>
     * 当用户尝试输入一个超出范围的经度或者纬度时， GEOADD 命令将返回一个错误。
     * @param key 主键名
     * @param memberCoordinateMap 成员和经纬度
     * @return 新添加到键里面的空间元素数量， 不包括那些已经存在但是被更新的元素。
     */
    Long geoAdd(String key, Map<Object, Point> memberCoordinateMap);

    /**
     * 从键里面返回所有给定位置元素的位置（经度和纬度）。<br/>
     * 因为 GEOPOS 命令接受可变数量的位置元素作为输入， 所以即使用户只给定了一个位置元素， 命令也会返回数组回复。
     * @param key 主键名
     * @param members 成员
     * @return GEOPOS 命令返回一个数组， 数组中的每个项都由两个元素组成： 第一个元素为给定位置元素的经度， 而第二个元素则为给定位置元素的纬度。<br/>
     *         当给定的位置元素不存在时， 对应的数组项为空值。
     */
    List<Point> geoPosition(String key, Object... members);

    /**
     * 返回两个给定位置之间的距离<br/>
     * 如果两个位置之间的其中一个不存在， 那么命令返回空值。<br/>
     * GEODIST 命令在计算距离时会假设地球为完美的球形， 在极限情况下， 这一假设最大会造成 0.5% 的误差。
     * @param key 主键名
     * @param member1 成员1
     * @param member2 成员2
     * @return 计算出的距离会以双精度浮点数的形式被返回。 如果给定的位置元素不存在， 那么命令返回空值。
     */
    Distance geoDistance(String key, Object member1, Object member2, Metrics metric);

    /**
     * 获取给定地点范围内的 地点信息。时间复杂度为O(N+log(M))，N为指定半径范围内的元素个数，M为要返回的个数
     * @param key 主键名
     * @param member 给定地点
     * @param distance 距离信息
     * @param args 特殊查询条件，如排序规则、指定数量等
     * @return 一个范围之内的位置元素。
     */
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Object member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * 获取给定地点范围内的 地点信息。时间复杂度为O(N+log(M))，N为指定半径范围内的元素个数，M为要返回的个数
     * @param key 主键名
     * @param circle 指定范围
     * @param args 特殊查询条件，如排序规则、指定数量等
     * @return 一个范围之内的位置元素。
     */
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Circle circle, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * 返回一个或多个位置元素的 Geohash 表示
     * @param key 主键名
     * @param members 成员
     * @return 一个数组， 数组的每个项都是一个 geohash 。 命令返回的 geohash 的位置与用户给定的位置元素的位置一一对应。
     */
    List<String> geohash(String key, Object... members);

    /**
     * 从列表中删除位置
     * @param key 主键名
     * @param members 地理位置名
     * @return 被删除的数量
     */
    Long geoRemove(String key, Object... members);

    /**
     * 将任意数量的元素添加到指定的 HyperLogLog 里面。<br/>
     * 作为这个命令的副作用， HyperLogLog 内部可能会被更新， 以便反映一个不同的唯一元素估计数量（也即是集合的基数）。<br/>
     * 如果 HyperLogLog 估计的近似基数（approximated cardinality）在命令执行之后出现了变化， 那么命令返回 1 ， 否则返回 0 。 如果命令执行时给定的键不存在， 那么程序将先创建一个空的 HyperLogLog 结构， 然后再执行命令。<br/>
     * 调用 PFADD 命令时可以只给定键名而不给定元素：
     * <ul>
     *     <li>如果给定键已经是一个 HyperLogLog ， 那么这种调用不会产生任何效果；</li>
     *     <li>但如果给定的键不存在， 那么命令会创建一个空的 HyperLogLog ， 并向客户端返回 1 。</li>
     * </ul>
     * @param key 主键名
     * @param elements 元素
     * @return 如果 HyperLogLog 的内部储存被修改了， 那么返回 true ， 否则返回 false 。
     */
    Long logAdd(String key, Object... elements);

    /**
     * 当 PFCOUNT 命令作用于单个键时， 返回储存在给定键的 HyperLogLog 的近似基数， 如果键不存在， 那么返回 0 。<br/>
     * 当 PFCOUNT 命令作用于多个键时， 返回所有给定 HyperLogLog 的并集的近似基数， 这个近似基数是通过将所有给定 HyperLogLog 合并至一个临时 HyperLogLog 来计算得出的。<br/>
     * 通过 HyperLogLog 数据结构， 用户可以使用少量固定大小的内存， 来储存集合中的唯一元素 （每个 HyperLogLog 只需使用 12k 字节内存，以及几个字节的内存来储存键本身）。<br/>
     * 命令返回的可见集合（observed set）基数并不是精确值， 而是一个带有 0.81% 标准错误（standard error）的近似值。<br/>
     * 举个例子， 为了记录一天会执行多少次各不相同的搜索查询， 一个程序可以在每次执行搜索查询时调用一次 PFADD ， 并通过调用 PFCOUNT 命令来获取这个记录的近似结果。
     * @param keys 主键名
     * @return 给定 HyperLogLog 包含的唯一元素的近似数量。
     */
    Long logSize(String... keys);

    /**
     * 将多个 HyperLogLog 合并（merge）为一个 HyperLogLog ， 合并后的 HyperLogLog 的基数接近于所有输入 HyperLogLog 的可见集合（observed set）的并集。<br/>
     * 合并得出的 HyperLogLog 会被储存在 destkey 键里面， 如果该键并不存在， 那么命令在执行之前， 会先为该键创建一个空的 HyperLogLog 。
     * @param destkey 合并后的key
     * @param sourcekeys 需要合并的key
     * @return 返回 true
     */
    Long logUnion(String destkey, String... sourcekeys);

    /**
     * 删除HyperLogLog
     * @param key key
     */
    void logDelete(String key);
}
