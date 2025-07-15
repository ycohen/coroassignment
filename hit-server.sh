#!/bin/bash

function sendMessage() {
	max=9999
	min=1000
	part1=$(( $RANDOM % ($max - $min + 1) + $min ))
	part2=$(( $RANDOM % ($max - $min + 1) + $min ))
	part3=$(( $RANDOM % ($max - $min + 1) + $min ))
	part4=$(( $RANDOM % ($max - $min + 1) + $min ))

	sentTime=$(( $RANDOM % (5000 - 0 + 1) + 0 ))

	cardCount=$(( $RANDOM % (3 - 0 + 0) + 0 ))

	if [ $cardCount -eq 0 ]; then
		body="No cards here!"
#		echo Chose no card
	elif [ $cardCount -eq 1 ]; then
#		echo Chose one card
		body="${part1}-${part2}-${part3}-${part4}"
	else
#		echo Chose two cards
		body="${part1}-${part2}-${part3}-${part4} and also ${part1}-${part2}-${part3}-${part4}"
	fi

	curl -X POST --location "http://localhost:8080/message"     -H "Content-Type: application/json"     -d @- << EOF
{
		  "id": "",
		  "sender": "a@b.com",
		  "recipients": ["c@d.com"],
		  "subject": "1234",
		  "body": "${body}",
		  "sentTime": "${sentTime}"
}
EOF
}

function getCards() {
	max=5000
	min=0
	lower=$(( $RANDOM % ($max - $min + 1) + $min ))
	upper=$(( $RANDOM % ($max - $lower + 1) + $lower ))

	curl -X GET --location "http://localhost:8080/detections?timeFrom=${lower}&timeTo=${upper}" &>> /dev/null
}

for i in $(seq 1 2500); do
	pick=$(( $RANDOM % (1 - 0 + 0) + 0 ))
	
	if [ $pick -eq 0 ]; then
		sendMessage &
	else
		sendMessage &
#		getCards &
	fi
done

# Wait for all parallel jobs to finish
while [ 1 ]; do 
	fg 2> /dev/null
	[ $? == 1 ] && break; 
done
