import redis

r0 = redis.StrictRedis(host="120.126.18.94", db = 0)

key = r0.scan()[1][0].decode()

print(r0.get(key))

