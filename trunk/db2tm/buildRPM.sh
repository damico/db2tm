#!/bin/bash
sh prepareFS.sh
rm -r /var/tmp/db2tm**
cd dist/linux/rpm/
cp -r db2tm/ /var/tmp
cp db2tm.spec /var/tmp
cd ../../../
cp lib/** /var/tmp/db2tm/usr/lib/db2tm/
cp db2tm /var/tmp/db2tm/usr/bin/
cp /usr/lib/db2tm/db2tm.jar /var/tmp/db2tm/usr/lib/db2tm
cp /etc/db2tm/** /var/tmp/db2tm/etc/db2tm/
mv /var/tmp/db2tm /var/tmp/db2tm-$1
cd /var/tmp/
rpmbuild -ba db2tm.spec