import re
import requests, json
import redis

brandinfo_url = "http://www.edamall.com.tw/ashx/Brand/GetBrandInfo_Ayuda.ashx"
requests_data = requests.get(brandinfo_url)


print("Fetching data from %s" % (brandinfo_url))

if requests_data.status_code == requests.codes.ok: #200
    infos = json.loads(requests_data.text)['brandlist']
    #print(len(infos['brandlist']))  ##Output : 296
    #print(infos)
    
    redis_client = redis.StrictRedis(host='localhost', port=55555, db=2)
    print("Redis Connected")
    
    print("Data Processing")
    for info in infos:
        regex = re.compile(r'[\n\r\t\x0b]')
        info['store_id'] = regex.sub(" ", info['store_id'])
        info['bd_id'] = regex.sub(" ", info['bd_id'])
        info['bd_name'] = regex.sub(" ", info['bd_name'])
        info['zone_id'] = regex.sub(" ", info['zone_id'])
        info['fl_name'] = regex.sub(" ", info['fl_name'])
        info['link'] = regex.sub(" ", info['link'])
        info['content'] = regex.sub(" ", info['content'])
        info['open_time'] = regex.sub(" ", info['open_time'])
        info['brand_type'][0]['btype_id'] = regex.sub(" ", info['brand_type'][0]['btype_id'])
        info['brand_type'][0]['btype_name'] = regex.sub(" ", info['brand_type'][0]['btype_name'])
        info['brand_pics'][0]['file_path'] = regex.sub(" ", info['brand_pics'][0]['file_path'])
        info['brand_pics'][1]['file_path'] = regex.sub(" ", info['brand_pics'][1]['file_path'])
        value = '{\"StoreID\":\"%s\", \"BrandID\":\"%s\", \"BrandName\":\"%s\", \"ZoneID\":\"%s\", \"Floor\":\"%s\", \"Link\":\"%s\", \"Content\":\"%s\", \"OpenTime\":\"%s\", \"BrandTypeID\":\"%s\", \"BrandType\":\"%s\", \"BrandPicB\":\"%s\", \"BrandPicBIG\":\"%s\"}' % (info['store_id'], info['bd_id'], info['bd_name'], info['zone_id'], info['fl_name'], info['link'], info['content'], info['open_time'], info['brand_type'][0]['btype_id'], info['brand_type'][0]['btype_name'], info['brand_pics'][0]['file_path'], info['brand_pics'][1]['file_path'])
        # https://stackoverflow.com/questions/22394235/invalid-control-character-with-python-json-loads
        #value_json = json.loads(value, strict=False)
        #print(value_json['BrandPicBIG'])

        redis_client.set(info['store_id'], value)
    print("Redis db Updated")
