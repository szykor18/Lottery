#!/bin/sh
set -e

url=$1

if [ -z "$SSH_PRIVATE_KEY" ]; then
    >&2 echo "Set SSH_PRIVATE_KEY environment variable"
    exit 1
fi

ssh_host=$(echo $url | sed 's/.*@//' | sed 's/[:/].*//')

mkdir -p ~/.ssh
echo "$SSH_PRIVATE_KEY" | base64 -d > ~/.ssh/id_rsa
chmod 600 ~/.ssh/id_rsa
ssh-keyscan -H "$ssh_host" >> ~/.ssh/known_hosts