#!/bin/sh

export MINIO_SERVER_HOST='minio-service' 
export MINIO_ACCESS_KEY=$MINIO_ROOT_USER
export MINIO_SECRET_KEY=$MINIO_ROOT_PASSWORD


echo "starting"

while : 
do
    echo "=== curl command ===  http://$MINIO_SERVER_HOST"
    curl_output=$(curl --max-time 3 http://$MINIO_SERVER_HOST)
    curl_exit_code=$?

    if [ $curl_exit_code -eq 0 ]; then
        echo "$curl_output"
        break
    else
        echo "Curl command timed out or failed with exit code: $curl_exit_code"
        echo "Retrying after 3 seconds..."
        sleep 3
    fi
done

# Create mc config
echo "=== mc config ==="
mc config host add myminio http://$MINIO_SERVER_HOST $MINIO_ACCESS_KEY $MINIO_SECRET_KEY --api S3v4 

# Create the buckets
echo "=== mc mb post-bucket ==="
mc mb myminio/post-bucket 

echo "=== mc mb profile-bucket ==="
mc mb myminio/profile-bucket 

# Set bucket policies
echo "=== mc policy set post-bucket ==="
mc anonymous  set public myminio/post-bucket #set-json /etc/bucket-settings/post-bucket.json myminio/post-bucket 

echo "=== mc policy set profile-bucket ==="
mc anonymous set public myminio/profile-bucket #set-json /etc/bucket-settings/profile-bucket.json myminio/profile-bucket 


tail -f /dev/null

