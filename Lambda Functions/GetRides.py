import json
import boto3
from boto3.dynamodb.conditions import Key

def lambda_handler(event, context):
	client = boto3.resource('dynamodb')
	table = client.Table('Rides')
	userid = event['user_id']
	date_time = event['datetime']
	response = table.query(
		KeyConditionExpression=Key('user_id').eq(userid) & Key('datetime').gt(date_time) 
	)

	return response
