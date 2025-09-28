local uid = KEYS[1]
local prob = ARGV[1]
local ttl = ARGV[2]

if redis.call('EXISTS', uid) == 0 then
    redis.call('HSET', uid,
            'userId', uid,
            'probability', tonumber(prob),
            'clicksCount', 1
    )
    redis.call('EXPIRE', uid, tonumber(ttl))
else
    redis.call('HSET', uid, 'probability', tonumber(prob))
    redis.call('HINCRBY', uid, 'clicksCount', 1)
end