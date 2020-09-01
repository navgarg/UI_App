import json
import boto3
from boto3.dynamodb.conditions import Key

def lambda_handler(event, context):
	client = boto3.resource('dynamodb')
	table = client.Table('Rides')
	response = table.get_item(
		Key={
			'user_id': 'E0VJnE0YitPFQhy25F3efPD1PXt2',
			'datetime': '2020-07-18'
		}
	)
	userid = event['user_id']
	date_time = event['datetime']
	response = table.query(
		KeyConditionExpression=Key('user_id').eq(userid) & Key('datetime').gt(date_time) 
	)

	return response
