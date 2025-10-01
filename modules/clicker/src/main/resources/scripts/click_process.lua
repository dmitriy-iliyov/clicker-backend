local key = KEYS[1]
local id = ARGV[1]
local prob = ARGV[2]
local c_count = ARGV[3]
local ttl = ARGV[4]

if redis.call('EXISTS', key) == 0 then
    redis.call('HSET', key,
            'userId', id,
            'probability', tonumber(prob),
            'clicksCount', 1
    )
    redis.call('EXPIRE', key, tonumber(ttl))
    return 1
else
    redis.call('HSET', key, 'probability', tonumber(prob), 'clicksCount', tonumber(c_count))
    return 1
end
