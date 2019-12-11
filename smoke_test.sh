#!/bin/bash



status=`curl --silent --head https://api.hh.ru/vacancies | head -1 | cut -f 2 -d' '`

if [ "$status" != "200" ]
then
	echo "SMOKE TEST FAILED"
	exit 1
else
	echo "SMOKE TEST PASSED"
	exit 0
fi
