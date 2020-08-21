import re
import requests, json
import redis


api_url = "http://www.edamall.com.tw/ashx/Brand/GetNewsInfo.ashx"


specialEvent = {"Type" : "SpecialEvent"}
news = {"Type" : "News"}

speicalEvent_data  = json.loads(requests.post(api_url, data=specialEvent).text)['message']
news_data = json.loads(requests.post(api_url, data=news).text)['message']


redis_client = redis.StrictRedis(host='localhost', port=55555, db=3)


for event in speicalEvent_data:
    #print(event['NEWS_ID'])

    # key = NEWS_ID
    # value = NEWS_ID, TITLE, CONTENT, WEIGHT, ACTIVITY_DATE, INDEX_PIC_PATH, INDEX_PIC_PATH2
    
    regex = re.compile(r'[\n\r\t\x0b]')

    event['NEWS_ID'] = regex.sub(" ", event['NEWS_ID'])
    event['TITLE'] = regex.sub(" ", event['TITLE'])
    event['CONTENT'] = regex.sub(" ", event['CONTENT']).replace("\"", "\'")
    event['WEIGHT'] = regex.sub(" ", event['WEIGHT'])
    event['ACTIVITY_DATE'] = regex.sub(" ", event['ACTIVITY_DATE'])
    event['INDEX_PIC_PATH'] = regex.sub(" ", event['INDEX_PIC_PATH'])
    event['INDEX_PIC_PATH2'] = regex.sub(" ", event['INDEX_PIC_PATH2'])

    value = '{\"NewsID\":\"%s\", \"Title\":\"%s\", \"Content\":\"%s\", \"Weight\":\"%s\", \"ACTIVITY_DATE\":\"%s\", \"INDEX_PIC_PATH\":\"%s\", \"INDEX_PIC_PATH2\":\"%s\"}' % ( event['NEWS_ID'], event['TITLE'], event['CONTENT'], event['WEIGHT'], event['ACTIVITY_DATE'], event['INDEX_PIC_PATH'], event['INDEX_PIC_PATH2'])

    redis_client.set(event['NEWS_ID'], value)


for news in news_data:
    #print(news['NEWS_ID'])

    regex = re.compile(r'[\n\r\t\x0b]')

    news['NEWS_ID'] = regex.sub(" ", news['NEWS_ID'])
    news['TITLE'] = regex.sub(" ", news['TITLE'])
    news['CONTENT'] = regex.sub(" ", news['CONTENT']).replace("\"", "\'")
    news['WEIGHT'] = regex.sub(" ", news['WEIGHT'])
    news['ACTIVITY_DATE'] = regex.sub(" ", news['ACTIVITY_DATE'])
    news['INDEX_PIC_PATH'] = regex.sub(" ", news['INDEX_PIC_PATH'])
    news['INDEX_PIC_PATH2'] = regex.sub(" ", news['INDEX_PIC_PATH2'])

    value = '{\"NewsID\":\"%s\", \"Title\":\"%s\", \"Content\":\"%s\", \"Weight\":\"%s\", \"ACTIVITY_DATE\":\"%s\", \"INDEX_PIC_PATH\":\"%s\", \"INDEX_PIC_PATH2\":\"    %s\"}' % (news['NEWS_ID'], news['TITLE'], news['CONTENT'], news['WEIGHT'], news['ACTIVITY_DATE'], news['INDEX_PIC_PATH'], news['INDEX_PIC_PATH2'])
    redis_client.set(news['NEWS_ID'], value)
