#!/usr/bin/env bash

set -eou pipefail

TARGET_DIR="$HOME/target"

cd backend/prepa
if ! [ -f "$HOME/.java/lib/jconn4.jar" ]; then
    echo "*** install jconn4.jar ***"
    curl -L http://192.168.130.6/jconn4/jconn4.jar -o "$HOME/.java/lib/jconn4.jar"
    ./mvnw install:install-file -Dfile="$HOME/.java/lib/jconn4.jar" -DgroupId=com.sybase.jdbc4.jdbc -DartifactId=jconn4 -Dversion=4.0 -Dpackaging=jar
fi

echo "*** mvn package ***"
./mvnw package

echo "*** file list ***"
ls -lh target/

echo "*** deploy ***"
mkdir -p "$TARGET_DIR/backend/"
rsync -av --delete "target/prepa-V1.0.war" "$TARGET_DIR/backend/"
cat << EOF > "$TARGET_DIR/backend/interfaces"
OPHAM -> 192.168.130.2
SIMUL221 -> 192.168.130.221
SIMUL220 -> 192.168.130.220
EOF

echo "*** restart service ***"
sudo systemctl restart prepa

